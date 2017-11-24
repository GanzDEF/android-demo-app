package com.test.xyz.demo.ui.repodetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.common.BaseFragment;
import com.test.xyz.demo.ui.common.di.DaggerApplication;
import com.test.xyz.demo.ui.common.util.UIHelper;
import com.test.xyz.demo.ui.repodetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsPresenter;
import com.test.xyz.demo.ui.repodetails.vp.RepoDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RepoDetailsFragment extends BaseFragment implements RepoDetailsView {
    private String repoItemTitle;
    private Unbinder unbinder;

    @Inject RepoDetailsPresenter presenter;
    @BindView(R.id.repoDetails) TextView repoDetails;
    @BindView(R.id.repoTitle) TextView repoTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repodetails, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initializeFragment(@Nullable Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new RepoDetailsFragmentModule(this))
                .inject(this);

        loadRepoDetails();
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
        UIHelper.showToastMessage(getActivity(), errorMessage);
        repoDetails.setText(R.string.repo_details_ret_error);
    }

    @Override
    public void onPause() {
        dismissAllDialogs();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /** Creates repo details fragment instance */
    public static RepoDetailsFragment newInstance(String repoItemTitle) {
        RepoDetailsFragment fragment = new RepoDetailsFragment();
        Bundle args = new Bundle();

        args.putString(UIHelper.Constants.REPO_TITLE, repoItemTitle);
        fragment.setArguments(args);

        return fragment;
    }

    private void loadRepoDetails() {
        showLoadingDialog();
        repoItemTitle = getArguments().getString(UIHelper.Constants.REPO_TITLE);
        presenter.requestRepoDetails(UIHelper.Constants.REPO_OWNER, repoItemTitle);
    }
}
