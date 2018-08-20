package com.test.xyz.demo.presentation.projectdetails.presenter

import com.test.xyz.demo.domain.model.github.GitHubRepo

interface ProjectDetailsView {
    fun showProjectDetails(gitHubRepo: GitHubRepo)
    fun showError(errorMessage: Int)
}
