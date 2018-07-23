package com.test.xyz.demo.domain.repository.api;

import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ProjectListRepository {
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Observable<List<GitHubRepo>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Observable<GitHubRepo> getProjectDetails(@Path("user") String user, @Path("reponame") String repoName);
}
