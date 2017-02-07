package com.github.mobile;

import java.io.IOException;

/**
 * Created by matthew on 2/6/17.
 */

public interface HttpClient {


    HttpClient client = null;


    void setRequest(String source);

    void setResponse() throws IOException;

    byte[] getResponseBytes() throws IOException;

}
