/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mobile;

import android.util.Log;

import com.squareup.picasso.Cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.zip.GZIPInputStream;

/**
 * Reader of previously fetched request data
 */
public class RequestReader {

    private static final String TAG = "RequestReader";

    CacheReaderInterface cache;

    private final int version;

    /**
     * Create request reader
     *
     * @param cache
     * @param formatVersion
     */
    public RequestReader(CacheReaderInterface cache, int formatVersion) {
        this.cache = cache;
        version = formatVersion;
    }

    /**
     * Read request data
     *
     * @return read data
     */
    @SuppressWarnings("unchecked")
    public <V> V read() {
        boolean delete = false;
        ObjectInputStream input = cache.open_read();
        try {
            int streamVersion = input.readInt();
            if (streamVersion != version) {
                delete = true;
                return null;
            }
            return (V) input.readObject();
        }catch (IOException e) {
            Log.d(TAG, "Exception reading cache " + cache.getName(), e);
            return null;
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Exception reading cache " + cache.getName(), e);
            return null;
        } finally {
            cache.close_read(delete);
        }
    }
}
