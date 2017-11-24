package com.test.xyz.demo.ui;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.domain.repository.api.model.Repo;
import com.test.xyz.demo.ui.repodetails.vp.OnRepoDetailsCompletedListener;
import com.test.xyz.demo.ui.repolist.vp.OnRepoListCompletedListener;
import com.test.xyz.demo.ui.weather.vp.OnWeatherInfoCompletedListener;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;

public abstract class BasePresenterTest {
    protected static final String MOCK_INFO_SUCCESS_MSG = "MOCK INFO SUCCESS MSG";
    protected static final String INVALID_CITY = "INVALID";
    protected static final String VALID_CITY = "New York, USA";
    protected static final String EMPTY_VALUE = "";

    protected void mockInteractor(MainInteractor mainInteractor) {
        mockGetInformationAPI(mainInteractor);
        mockGetRepoListAPI(mainInteractor);
        mockGetRepoItemsAPI(mainInteractor);
    }

    private void mockGetRepoItemsAPI(MainInteractor mainInteractor) {
        doAnswer((invocation) -> {
            ((OnRepoDetailsCompletedListener) invocation.getArguments()[2]).onRepoDetailsRetrievalFailure(any(String.class));
            return null;
        }).when(mainInteractor).getRepoItemDetails(eq(EMPTY_VALUE), any(String.class), any(OnRepoDetailsCompletedListener.class));

        doAnswer((invocation) -> {
            ((OnRepoDetailsCompletedListener) invocation.getArguments()[2]).onRepoDetailsRetrievalSuccess(getFakeRepo());
            return null;
        }).when(mainInteractor).getRepoItemDetails(not(eq(EMPTY_VALUE)), any(String.class), any(OnRepoDetailsCompletedListener.class));
    }

    private void mockGetRepoListAPI(MainInteractor mainInteractor) {
        doAnswer((invocation) -> {
            ((OnRepoListCompletedListener) invocation.getArguments()[1]).onRepoListRetrievalFailure("Error");
            return null;
        }).when(mainInteractor).getRepoList(eq(EMPTY_VALUE), any(OnRepoListCompletedListener.class));

        doAnswer((invocation) -> {
            ((OnRepoListCompletedListener) invocation.getArguments()[1]).onRepoListRetrievalSuccess(getFakeRepoList());
            return null;
        }).when(mainInteractor).getRepoList(not(eq(EMPTY_VALUE)), any(OnRepoListCompletedListener.class));
    }

    private void mockGetInformationAPI(MainInteractor mainInteractor) {
        doAnswer((invocation) -> {
            ((OnWeatherInfoCompletedListener) invocation.getArguments()[2]).onFailure(R.string.weather_error);
            return null;
        }).when(mainInteractor).getWeatherInformation(anyString(), eq(INVALID_CITY), any(OnWeatherInfoCompletedListener.class));

        doAnswer((invocation) -> {
            ((OnWeatherInfoCompletedListener) invocation.getArguments()[2]).onUserNameValidationError(R.string.username_empty_message);
            return null;
        }).when(mainInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY), any(OnWeatherInfoCompletedListener.class));

        doAnswer((invocation) -> {
            ((OnWeatherInfoCompletedListener) invocation.getArguments()[2]).onCityValidationError(R.string.city_empty_message);
            return null;
        }).when(mainInteractor).getWeatherInformation(anyString(), eq(EMPTY_VALUE), any(OnWeatherInfoCompletedListener.class));

        doAnswer((invocation) -> {
            ((OnWeatherInfoCompletedListener) invocation.getArguments()[2]).onSuccess(MOCK_INFO_SUCCESS_MSG);
            return null;
        }).when(mainInteractor).getWeatherInformation(not(eq(EMPTY_VALUE)),
                and(not(eq(INVALID_CITY)), not(eq(EMPTY_VALUE))),
                any(OnWeatherInfoCompletedListener.class));
    }

    private List<Repo> getFakeRepoList() {
        List<Repo> repoList = new ArrayList<>();
        repoList.add(getFakeRepo());
        return repoList;
    }

    private Repo getFakeRepo() {
        Repo repo = new Repo("Fake repo");
        return repo;
    }
}
