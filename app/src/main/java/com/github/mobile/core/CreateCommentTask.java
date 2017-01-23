package com.github.mobile.core;

import android.accounts.Account;

import org.eclipse.egit.github.core.Comment;

/**
 * Created by matthew on 1/22/17.
 */

public interface CreateCommentTask{

    final String TAG = "CreateCommentTask";


    CreateCommentTask start();


    Comment run(final Account account) throws Exception;


    void onException(final Exception e) throws RuntimeException;
}
