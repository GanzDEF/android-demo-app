package com.test.xyz.demo.presentation.projectlist.presenter

import com.test.xyz.demo.domain.model.github.GitHubRepo

interface ProjectListView {
    fun showProjectList(projectList: List<GitHubRepo>)
    fun showError(messageID: Int)
}
