package com.test.xyz.demo.presentation.projectlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.GitHubRepo;
import com.test.xyz.demo.presentation.mainlobby.MainActivity;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenter;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListView;
import com.test.xyz.demo.presentation.common.BaseFragment;
import com.test.xyz.demo.presentation.common.di.DaggerApplication;
import com.test.xyz.demo.presentation.common.util.UIHelper;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectListFragment extends BaseFragment implements ProjectListView {
    private Unbinder unbinder;

    @BindView(R.id.gitHubRepoList) ListView repoListView;
    @BindView(R.id.noAvlRepos) TextView noAvlRepos;

    @Inject ProjectListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repolist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initializeFragment(@Nullable Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new ProjectListFragmentModule(this))
                .inject(this);

        showLoadingDialog();
        presenter.requestProjectList(UIHelper.Constants.REPO_OWNER);
    }

    @Override
    public void showProjectList(final List<GitHubRepo> projectList) {
        dismissAllDialogs();
        displayResults(projectList);
    }

    @Override
    public void showError(final String errorMessage) {
        dismissAllDialogs();
        UIHelper.showToastMessage(ProjectListFragment.this.getActivity(), errorMessage);
         displayResults(new ArrayList<GitHubRepo>() {});
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

    public static ProjectListFragment newInstance() {
        ProjectListFragment fragment = new ProjectListFragment();
        return fragment;
    }

    private void displayResults(List<GitHubRepo> gitHubRepos) {
        final List<String> values = mapRepoList(gitHubRepos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProjectListFragment.this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);
        repoListView.setEmptyView(noAvlRepos);

        repoListView.setOnItemClickListener((parent,  view, position,  id) -> {
                int itemPosition = position;
            ((MainActivity) getActivity()).loadRepoDetailsFragment(values.get(itemPosition));
        });
    }
}
