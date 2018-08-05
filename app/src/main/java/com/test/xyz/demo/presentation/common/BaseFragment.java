package com.test.xyz.demo.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.test.xyz.demo.domain.model.github.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    protected List<String> mapRepoList(List<GitHubRepo> gitHubRepos) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < gitHubRepos.size(); ++i) {
            values.add(gitHubRepos.get(i).name);
        }
        return values;
    }
}
