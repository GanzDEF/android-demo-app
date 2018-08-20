package com.test.xyz.demo.presentation.projectdetails.presenter

interface ProjectDetailsPresenter {
    fun requestProjectDetails(userName: String, projectID: String)
    fun onStop()
}
