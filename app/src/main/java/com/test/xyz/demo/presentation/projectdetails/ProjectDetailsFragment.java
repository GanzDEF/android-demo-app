package com.test.xyz.demo.presentation.projectdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.BaseFragment;
import com.test.xyz.demo.presentation.common.di.DaggerApplication;
import com.test.xyz.demo.presentation.common.util.UIHelper;
import com.test.xyz.demo.presentation.mainlobby.MainActivity;
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenter;
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectDetailsFragment extends BaseFragment implements ProjectDetailsView {
    private String projectTitle;
    private Unbinder unbinder;

    @BindView(R.id.projectDetails) TextView repoDetails;
    @BindView(R.id.projectTitle) TextView repoTitle;

    @Inject ProjectDetailsPresenter presenter;

    public static ProjectDetailsFragment newInstance(String projectTitle) {
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        Bundle args = new Bundle();

        args.putString(UIHelper.Constants.PROJECT_TITLE, projectTitle);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projectdetails, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadProjectDetails();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            DaggerApplication.get(this.getContext())
                    .getAppComponent()
                    .plus(new RepoDetailsFragmentModule(this))
                    .inject(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showProjectDetails(GitHubRepo gitHubRepo) {
        repoTitle.setText(projectTitle);
        repoDetails.setText(gitHubRepo.description);
    }

    @Override
    public void showError(int errorMessage) {
        UIHelper.showToastMessage(getActivity(), getString(errorMessage));
        repoDetails.setText(R.string.project_details_ret_error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadProjectDetails() {
        projectTitle = getArguments().getString(UIHelper.Constants.PROJECT_TITLE);
        presenter.requestProjectDetails(UIHelper.Constants.PROJECT_OWNER, projectTitle);
    }
}
