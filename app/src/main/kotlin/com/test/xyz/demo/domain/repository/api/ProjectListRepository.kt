package com.test.xyz.demo.domain.repository.api

import com.test.xyz.demo.domain.model.github.GitHubRepo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectListRepository {

    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String): Observable<List<GitHubRepo>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String, @Path("reponame") repoName: String): Observable<GitHubRepo>

    companion object {
        val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }
}
