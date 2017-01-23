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
package com.github.mobile.core.commit;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.core.CreateCommentTask;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.CommitService;

/**
 * Task to comment on a commit
 */
public class CreateCommitCommentTask extends ProgressDialogTask<Comment> implements CreateCommentTask{

    private static final String TAG = "CreateCommentTask";

    @Inject
    private CommitService service;

    private final IRepositoryIdProvider repository;

    private final String commit;

    private final CommitComment comment;

    /**
     * Create task to create a comment
     *
     * @param activity
     * @param repository
     * @param commit
     * @param comment
     */
    protected CreateCommitCommentTask(final Activity activity,
            final IRepositoryIdProvider repository, final String commit,
            final CommitComment comment) {
        super(activity);

        this.repository = repository;
        this.commit = commit;
        this.comment = comment;
    }

    /**
     * Execute the task and create the comment
     *
     * @return this task
     */
    public CreateCommitCommentTask start() {
        showIndeterminate(R.string.creating_comment);
        execute();
        return this;
    }

    @Override
    public CommitComment run(final Account account) throws Exception {
        CommitComment created = service.addComment(repository, commit, comment);
        String formatted = HtmlUtils.format(created.getBodyHtml()).toString();
        created.setBodyHtml(formatted);
        return created;

    }

    @Override
    public void onException(final Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception creating comment on commit", e);

        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
