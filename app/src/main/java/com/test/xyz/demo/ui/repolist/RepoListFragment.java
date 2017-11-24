package com.test.xyz.demo.ui.repolist;

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
import com.test.xyz.demo.ui.common.util.UIHelper;
import com.test.xyz.demo.ui.mainlobby.MainActivity;
import com.test.xyz.demo.ui.repolist.di.RepoListFragmentModule;
import com.test.xyz.demo.ui.repolist.vp.RepoListPresenter;
import com.test.xyz.demo.ui.repolist.vp.RepoListView;

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

        showLoadingDialog();
        presenter.requestRepoList(UIHelper.Constants.REPO_OWNER);
    }

    @Override
    public void showRepoList(final List<Repo> values) {
        dismissAllDialogs();
        displayResults(values);
    }

    @Override
    public void showError(final String errorMessage) {
        dismissAllDialogs();
        UIHelper.showToastMessage(RepoListFragment.this.getActivity(), errorMessage);
         displayResults(new ArrayList<Repo>() {});
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

    /** Creates repo list fragment instance */
    public static RepoListFragment newInstance() {
        RepoListFragment fragment = new RepoListFragment();
        return fragment;
    }

    private void displayResults(List<Repo> repos) {
        final List<String> values = mapRepoList(repos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RepoListFragment.this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        repoListView.setAdapter(adapter);
        repoListView.setEmptyView(noAvlRepos);

        repoListView.setOnItemClickListener((parent,  view, position,  id) -> {
                int itemPosition = position;
            ((MainActivity) getActivity()).loadRepoDetailsFragment(values.get(itemPosition));
        });
    }
}
