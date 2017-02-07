package com.github.mobile;

import com.github.mobile.accounts.GitHubAccount;
import com.github.mobile.api.RequestConfiguration;
import com.google.inject.Provider;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by matthew on 2/6/17.
 */

public class OkHttpClientWrapper implements HttpClient {

    private OkHttpClient client;

    private Request request;

    private Response response;

    public OkHttpClientWrapper(){

        this.client = new OkHttpClient();
    }

    public OkHttpClientWrapper(Provider<GitHubAccount> accountProvider) {

        this.client = new OkHttpClient.Builder()
                .addInterceptor(new RequestConfiguration(accountProvider))
                .build();
    }

    public OkHttpClient getClient(){
        return this.client;
    }

    public void setRequest(String source){
        request = new Request.Builder().url(source).build();
    }

    public void setResponse() throws IOException{
        response = this.client.newCall(request).execute();
    }

    public byte[] getResponseBytes() throws IOException {

        return response.body().bytes();
    }
}


