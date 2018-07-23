package com.test.xyz.demo.presentation.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment {
    private ProgressDialog mDialog;

    public void showLoadingDialog() {
        mDialog = new ProgressDialog(this.getActivity());

        mDialog.setMessage(getString(R.string.please_wait));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void dismissAllDialogs() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        initializeFragment(savedInstanceState);
    }

    protected List<String> mapRepoList(List<GitHubRepo> gitHubRepos) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < gitHubRepos.size(); ++i) {
            values.add(gitHubRepos.get(i).name);
        }
        return values;
    }

    abstract protected void initializeFragment(Bundle savedInstanceState);
}
