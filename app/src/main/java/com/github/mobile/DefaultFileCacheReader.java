package com.github.mobile;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DefaultFileCacheReader implements CacheReaderInterface{

    private static final String TAG = "DefaultFileCacheReader";
    private ObjectInputStream m_input;
    private final File handle;
    private FileLock lock;
    private RandomAccessFile dir;
    public DefaultFileCacheReader(File file)
    {
        handle = file;
    }

    private void createDirectory(final File dir) {
        if (dir != null && !dir.exists())
            dir.mkdirs();
    }

    @Override
    public String getName() { return handle.getName();}


    @Override
    public ObjectInputStream open_read()
    {
        if (!handle.exists() || handle.length() == 0)
            return null;

        dir = null;
        lock = null;
        m_input = null;
        try {
            dir = new RandomAccessFile(handle, "rw");
            lock = dir.getChannel().lock();
            m_input = new ObjectInputStream(new GZIPInputStream(
                    new FileInputStream(dir.getFD()), 8192 * 8));
            return m_input;
        }catch (IOException e) {
            Log.d(TAG, "Exception reading cache " + handle.getName(), e);
            return null;
        }
    }

    @Override
    public void close_read(boolean delete)
    {
        if (m_input != null)
            try {
                m_input.close();
            } catch (IOException e) {
                Log.d(TAG, "Exception closing stream", e);
            }
        if (delete)
            try {
                dir.setLength(0);
            } catch (IOException e) {
                Log.d(TAG, "Exception truncating file", e);
            }
        if (lock != null)
            try {
                lock.release();
            } catch (IOException e) {
                Log.d(TAG, "Exception unlocking file", e);
            }
        if (dir != null)
            try {
                dir.close();
            } catch (IOException e) {
                Log.d(TAG, "Exception closing file", e);
            }
    }
}
