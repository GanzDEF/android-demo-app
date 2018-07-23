package com.test.xyz.demo.presentation.projectdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.GitHubRepo;
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenter;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsView;
import com.test.xyz.demo.presentation.common.BaseFragment;
import com.test.xyz.demo.presentation.common.di.DaggerApplication;
import com.test.xyz.demo.presentation.common.util.UIHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectDetailsFragment extends BaseFragment implements ProjectDetailsView {
    private String repoItemTitle;
    private Unbinder unbinder;

    @BindView(R.id.projectDetails) TextView repoDetails;
    @BindView(R.id.projectTitle) TextView repoTitle;

    @Inject ProjectDetailsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projectdetails, container, false);
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
    public void showProjectDetails(final GitHubRepo gitHubRepo) {
        dismissAllDialogs();
        repoTitle.setText(repoItemTitle);
        repoDetails.setText(gitHubRepo.description);
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

    public static ProjectDetailsFragment newInstance(String repoItemTitle) {
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        Bundle args = new Bundle();

        args.putString(UIHelper.Constants.REPO_TITLE, repoItemTitle);
        fragment.setArguments(args);

        return fragment;
    }

    private void loadRepoDetails() {
        showLoadingDialog();
        repoItemTitle = getArguments().getString(UIHelper.Constants.REPO_TITLE);
        presenter.requestProjectDetails(UIHelper.Constants.REPO_OWNER, repoItemTitle);
    }
}
