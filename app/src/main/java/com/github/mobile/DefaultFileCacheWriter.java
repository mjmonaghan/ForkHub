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

public class DefaultFileCacheWriter implements CacheWriterInterface{

    private static final String TAG = "DefaultFileCacheWriter";
    private ObjectOutputStream m_output;
    private final File handle;
    private FileLock lock;
    private RandomAccessFile dir;
    public DefaultFileCacheWriter(File file)
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
    public ObjectOutputStream open_write()
    {
        createDirectory(handle.getParentFile());
        dir = null;
        lock = null;
        m_output = null;
        try {
            dir = new RandomAccessFile(handle, "rw");
            lock = dir.getChannel().lock();
            m_output = new ObjectOutputStream(new GZIPOutputStream(
                    new FileOutputStream(dir.getFD()), 8192));
            return m_output;
        } catch (IOException e)
        {
            Log.d(TAG, "Exception opening cache " + handle.getName(), e);
            return null;
        }
    }

    @Override
    public void close_write()
    {
        if (m_output != null)
            try {
                m_output.close();
            } catch (IOException e) {
                Log.d(TAG, "Exception closing stream", e);
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
