package com.test.xyz.demo.presentation;

import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public abstract class BasePresenterTest {
    protected static final String EMPTY_VALUE = "";

    protected void mockProjectInteractorBehavior(ProjectInteractor projectInteractor) {
        mockGetProjectListAPI(projectInteractor);
        mockGetProjectDetailsAPI(projectInteractor);
    }

    /*
    protected void mockWeatherInteractorBehavior(WeatherInteractor weatherInteractor) {
        mockGetWeatherInformationAPI(weatherInteractor);
    }
    */

    private void mockGetProjectDetailsAPI(ProjectInteractor projectInteractor) {
        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[2]).onFailure(mock(Throwable.class));
            return null;
        }).when(projectInteractor).getProjectDetails(eq(EMPTY_VALUE), any(String.class), any(ProjectActionCallback.class));

        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[2]).onSuccess(getFakeRepo());
            return null;
        }).when(projectInteractor).getProjectDetails(not(eq(EMPTY_VALUE)), any(String.class), any(ProjectActionCallback.class));
    }

    private void mockGetProjectListAPI(ProjectInteractor projectInteractor) {
        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[1]).onFailure(mock(Throwable.class));
            return null;
        }).when(projectInteractor).getProjectList(eq(EMPTY_VALUE), any(ProjectActionCallback.class));

        doAnswer((invocation) -> {
            ((ProjectActionCallback) invocation.getArguments()[1]).onSuccess(getFakeRepoList());
            return null;
        }).when(projectInteractor).getProjectList(not(eq(EMPTY_VALUE)), any(ProjectActionCallback.class));
    }

    private List<GitHubRepo> getFakeRepoList() {
        List<GitHubRepo> gitHubRepoList = new ArrayList<>();
        gitHubRepoList.add(getFakeRepo());
        return gitHubRepoList;
    }

    private GitHubRepo getFakeRepo() {
        GitHubRepo gitHubRepo = new GitHubRepo("Fake gitHubRepo");
        return gitHubRepo;
    }
}
