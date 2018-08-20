package com.test.xyz.demo.presentation.common

import android.os.Bundle
import android.support.v4.app.Fragment
import com.test.xyz.demo.domain.model.github.GitHubRepo
import java.util.*

abstract class BaseFragment : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    protected fun mapRepoList(gitHubRepos: List<GitHubRepo>): List<String> {
        val values = ArrayList<String>()
        for (i in gitHubRepos.indices) {
            values.add(gitHubRepos[i].name)
        }
        return values
    }
}
