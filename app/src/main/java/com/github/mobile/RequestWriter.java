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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.zip.GZIPOutputStream;

/**
 * Request writer
 */
public class RequestWriter {

    private static final String TAG = "RequestWriter";

    CacheWriterInterface cache;

    private final int version;

    /**
     * Create a request writer that writes to the given file
     *
     * @param cache
     * @param formatVersion
     */
    public RequestWriter(CacheWriterInterface cache, int formatVersion) {
        this.cache = cache;
        version = formatVersion;
    }



    /**
     * Write request to file
     *
     * @param request
     * @return request
     */
    public <V> V write(V request) {
        ObjectOutputStream output = cache.open_write();
        try {
            output.writeInt(version);
            output.writeObject(request);
        } catch (IOException e) {
            Log.d(TAG, "Exception writing cache " + cache.getName(), e);
            return null;
        }
        finally {
            cache.close_write();
        }
        return request;
    }
}
