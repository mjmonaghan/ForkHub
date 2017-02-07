package com.github.mobile;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface CacheWriterInterface {
    public ObjectOutputStream open_write();
    public void close_write();
    public String getName();
}
