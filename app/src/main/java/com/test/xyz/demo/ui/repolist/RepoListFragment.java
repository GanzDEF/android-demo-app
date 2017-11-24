package com.test.xyz.demo.ui.repolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.common.BaseFragment;
import com.test.xyz.demo.ui.common.di.DaggerApplication;
import com.test.xyz.demo.ui.common.util.CommonConstants;
import com.test.xyz.demo.ui.common.util.CommonUtils;
import com.test.xyz.demo.ui.repodetails.RepoDetailsActivity;
import com.test.xyz.demo.ui.repolist.di.RepoListFragmentModule;
import com.test.xyz.demo.ui.repolist.mvp.RepoListPresenter;
import com.test.xyz.demo.ui.repolist.mvp.RepoListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RepoListFragment extends BaseFragment implements RepoListView {
    private Unbinder unbinder;

    @Inject RepoListPresenter presenter;
    @BindView(R.id.repoList) ListView repoListView;
    @BindView(R.id.noAvlRepos) TextView noAvlRepos;

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
                .plus(new RepoListFragmentModule(this))
                .inject(this);

        presenter.requestRepoList(CommonConstants.REPO_OWNER);
    }

    @Override
    public void showRepoList(final List<Repo> values) {
        displayResults(values);
    }

    @Override
    public void showError(final String errorMessage) {
         CommonUtils.showToastMessage(RepoListFragment.this.getActivity(), errorMessage);
         displayResults(new ArrayList<Repo>() {});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void displayResults(List<Repo> repos) {
        final List<String> values = getRepoNameList(repos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RepoListFragment.this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);
        repoListView.setEmptyView(noAvlRepos);

        repoListView.setOnItemClickListener((parent,  view, position,  id) -> {
                int itemPosition = position;
                Intent intent = new Intent(RepoListFragment.this.getActivity(), RepoDetailsActivity.class);
                intent.putExtra(CommonConstants.REPO_DESC, values.get(itemPosition));
                startActivity(intent);
        });
    }

    private List<String> getRepoNameList(List<Repo> repos) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < repos.size(); ++i) {
            values.add(repos.get(i).name);
        }
        return values;
    }
}
