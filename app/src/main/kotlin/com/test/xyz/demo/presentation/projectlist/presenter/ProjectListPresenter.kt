package com.test.xyz.demo.presentation.projectlist.presenter

interface ProjectListPresenter {
    fun requestProjectList(userName: String)
    fun onStop()
}
