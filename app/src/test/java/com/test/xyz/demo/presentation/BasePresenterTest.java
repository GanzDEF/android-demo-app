package com.test.xyz.demo.presentation;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.project.ProjectInteractor;
import com.test.xyz.demo.domain.interactor.weather.WeatherInteractor;
import com.test.xyz.demo.domain.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

import static com.test.xyz.demo.domain.interactor.weather.WeatherInteractor.WeatherInfoActionCallback;
import static com.test.xyz.demo.domain.interactor.project.ProjectInteractor.ProjectActionCallback;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public abstract class BasePresenterTest {
    protected static final String INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";
    protected static final String INVALID_CITY = "INVALID";
    protected static final String VALID_CITY = "New York, USA";
    protected static final String EMPTY_VALUE = "";

    protected void mockProjectInteractorBehavior(ProjectInteractor projectInteractor) {
        mockGetProjectListAPI(projectInteractor);
        mockGetProjectDetailsAPI(projectInteractor);
    }

    protected void mockWeatherInteractorBehavior(WeatherInteractor weatherInteractor) {
        mockGetWeatherInformationAPI(weatherInteractor);
    }

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

    private void mockGetWeatherInformationAPI(WeatherInteractor weatherInteractor) {
        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onFailure(R.string.weather_error);
            return null;
        }).when(weatherInteractor).getWeatherInformation(anyString(), eq(INVALID_CITY), any(WeatherInfoActionCallback.class));

        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onUserNameValidationError(R.string.username_empty_message);
            return null;
        }).when(weatherInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY), any(WeatherInfoActionCallback.class));

        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onCityValidationError(R.string.city_empty_message);
            return null;
        }).when(weatherInteractor).getWeatherInformation(anyString(), eq(EMPTY_VALUE), any(WeatherInfoActionCallback.class));

        doAnswer((invocation) -> {
            ((WeatherInfoActionCallback) invocation.getArguments()[2]).onSuccess(INFO_SUCCESS_MSG);
            return null;
        }).when(weatherInteractor).getWeatherInformation(not(eq(EMPTY_VALUE)),
                and(not(eq(INVALID_CITY)), not(eq(EMPTY_VALUE))),
                any(WeatherInfoActionCallback.class));
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
