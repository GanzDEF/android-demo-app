package com.test.xyz.demo.ui.repodetails;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.ui.common.di.DaggerApplication;
import com.test.xyz.demo.ui.repodetails.mvp.RepoDetailsPresenter;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.common.BaseActivity;
import com.test.xyz.demo.ui.common.util.CommonConstants;
import com.test.xyz.demo.ui.common.util.CommonUtils;
import com.test.xyz.demo.ui.repodetails.di.RepoDetailsActivityModule;
import com.test.xyz.demo.ui.repodetails.mvp.RepoDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoDetailsActivity extends BaseActivity implements RepoDetailsView {
    static String TAG = RepoDetailsActivity.class.getName();

    @Inject
    RepoDetailsPresenter presenter;

    @BindView(R.id.repoDetails)
    TextView repoDetails;

    @BindView(R.id.repoTitle)
    TextView repoTitle;

    String repoItemTitle = "";

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_repodetails);

        ButterKnife.bind(this);

        Log.i(TAG, "Getting custom component inside RepoDetailsActivity");

        DaggerApplication.get(this).getAppComponent()
                .plus(new RepoDetailsActivityModule(this))
                .inject(this);

        startProgress();

        Intent intent = getIntent();
        repoItemTitle = intent.getStringExtra(CommonConstants.REPO_DESC);

        presenter.requestRepoDetails(CommonConstants.DEFAULT_USER_NAME, repoItemTitle);
    }

    @Override
    public void showRepoDetails(final Repo repo) {
        endProgress();
        repoTitle.setText(repoItemTitle);
        repoDetails.setText(repo.description);
    }

    @Override
    public void showError(final String errorMessage) {
        endProgress();
        CommonUtils.showToastMessage(RepoDetailsActivity.this, errorMessage);
        repoDetails.setText(R.string.repo_details_ret_error);
    }

    @Override
    protected void onPause() {
        // All dialogs must be closed when activity is paused to avoid leakage ...
        // Reference: http://stackoverflow.com/questions/2850573/activity-has-leaked-window-that-was-originally-added
        closeAllDialogs();
        super.onPause();
    }

    private void closeAllDialogs() {
        endProgress();
    }
}
