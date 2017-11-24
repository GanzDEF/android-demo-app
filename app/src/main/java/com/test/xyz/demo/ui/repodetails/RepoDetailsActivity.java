package com.test.xyz.demo.ui.repodetails;

import android.content.Intent;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.common.BaseActivity;
import com.test.xyz.demo.ui.common.di.DaggerApplication;
import com.test.xyz.demo.ui.common.util.CommonConstants;
import com.test.xyz.demo.ui.common.util.CommonUtils;
import com.test.xyz.demo.ui.repodetails.di.RepoDetailsActivityModule;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsPresenter;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoDetailsActivity extends BaseActivity implements RepoDetailsView {
    private String repoItemTitle;

    @Inject RepoDetailsPresenter presenter;
    @BindView(R.id.repoDetails) TextView repoDetails;
    @BindView(R.id.repoTitle) TextView repoTitle;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_repodetails);

        ButterKnife.bind(this);

        DaggerApplication.get(this).getAppComponent()
                .plus(new RepoDetailsActivityModule(this))
                .inject(this);

        showProgressDialog();

        Intent intent = getIntent();
        repoItemTitle = intent.getStringExtra(CommonConstants.REPO_DESC);

        presenter.requestRepoDetails(CommonConstants.REPO_OWNER, repoItemTitle);
    }

    @Override
    public void showRepoDetails(final Repo repo) {
        dismissAllDialogs();
        repoTitle.setText(repoItemTitle);
        repoDetails.setText(repo.description);
    }

    @Override
    public void showError(final String errorMessage) {
        dismissAllDialogs();
        CommonUtils.showToastMessage(RepoDetailsActivity.this, errorMessage);
        repoDetails.setText(R.string.repo_details_ret_error);
    }

    @Override
    protected void onPause() {
        dismissAllDialogs();
        super.onPause();
    }
}
