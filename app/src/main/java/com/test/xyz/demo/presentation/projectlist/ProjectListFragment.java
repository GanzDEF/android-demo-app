package com.test.xyz.demo.presentation.projectlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.github.GitHubRepo;
import com.test.xyz.demo.presentation.common.BaseFragment;
import com.test.xyz.demo.presentation.common.di.DaggerApplication;
import com.test.xyz.demo.presentation.common.util.UIHelper;
import com.test.xyz.demo.presentation.mainlobby.MainActivity;
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenter;
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectListFragment extends BaseFragment implements ProjectListView {
    private Unbinder unbinder;
    private List<GitHubRepo> projectList;

    @BindView(R.id.projectList) ListView repoListView;
    @BindView(R.id.noAvlProjects) TextView noAvlRepos;

    @Inject ProjectListPresenter presenter;

    public static ProjectListFragment newInstance() {
        ProjectListFragment fragment = new ProjectListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projectlist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadProjectList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            DaggerApplication.get(this.getContext())
                    .getAppComponent()
                    .plus(new ProjectListFragmentModule(this))
                    .inject(this);
        }
    }

    @Override
    public void showProjectList(List<GitHubRepo> projectList) {
        this.projectList = projectList;
        displayResults(projectList);
    }

    @Override
    public void showError(int errorMessage) {
        UIHelper.showToastMessage(ProjectListFragment.this.getActivity(), getString(errorMessage));
        displayResults(new ArrayList<GitHubRepo>() {});
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadProjectList() {
        if (this.projectList == null) {
            presenter.requestProjectList(UIHelper.Constants.PROJECT_OWNER);
            return;
        }

        showProjectList(this.projectList);
    }

    private void displayResults(List<GitHubRepo> gitHubRepos) {
        final List<String> values = mapRepoList(gitHubRepos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProjectListFragment.this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);
        repoListView.setEmptyView(noAvlRepos);

        repoListView.setOnItemClickListener((parent,  view, position,  id) -> {
            int itemPosition = position;
            ((MainActivity) getActivity()).loadProjectDetailsFragment(values.get(itemPosition));
        });
    }
}
