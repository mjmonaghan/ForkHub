package com.github.mobile;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface CacheReaderInterface {
    public ObjectInputStream open_read();
    public void close_read(boolean delete);
    public String getName();
}
