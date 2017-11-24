package com.test.xyz.demo.ui;

import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.interactor.MainInteractor;
import com.test.xyz.demo.ui.repodetails.mvp.OnRepoDetailsCompletedListener;
import com.test.xyz.demo.ui.repolist.mvp.OnRepoListCompletedListener;
import com.test.xyz.demo.ui.weather.mvp.OnWeatherInfoCompletedListener;
import com.test.xyz.demo.domain.repository.api.model.Repo;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalFailure(any(String.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(eq(EMPTY_VALUE), any(String.class), any(OnRepoDetailsCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoDetailsCompletedListener) args[2]).onRepoDetailsRetrievalSuccess(any(Repo.class));
                return null;
            }
        }).when(mainInteractor).getRepoItemDetails(not(eq(EMPTY_VALUE)), any(String.class), any(OnRepoDetailsCompletedListener.class));
    }

    private void mockGetRepoListAPI(MainInteractor mainInteractor) {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalFailure("Error");
                return null;
            }
        }).when(mainInteractor).getRepoList(eq(EMPTY_VALUE), any(OnRepoListCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnRepoListCompletedListener) args[1]).onRepoListRetrievalSuccess(any(List.class));
                return null;
            }
        }).when(mainInteractor).getRepoList(not(eq(EMPTY_VALUE)), any(OnRepoListCompletedListener.class));
    }

    private void mockGetInformationAPI(MainInteractor mainInteractor) {
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnWeatherInfoCompletedListener) args[2]).onFailure("Invalid city provided!!!");
                return null;
            }
        }).when(mainInteractor).getWeatherInformation(anyString(), eq(INVALID_CITY), any(OnWeatherInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnWeatherInfoCompletedListener) args[2]).onUserNameValidationError(R.string.username_empty_message);
                return null;
            }
        }).when(mainInteractor).getWeatherInformation(eq(EMPTY_VALUE), eq(VALID_CITY), any(OnWeatherInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnWeatherInfoCompletedListener) args[2]).onCityValidationError(R.string.city_empty_message);
                return null;
            }
        }).when(mainInteractor).getWeatherInformation(anyString(), eq(EMPTY_VALUE), any(OnWeatherInfoCompletedListener.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((OnWeatherInfoCompletedListener) args[2]).onSuccess(MOCK_INFO_SUCCESS_MSG);
                return null;
            }
        }).when(mainInteractor).getWeatherInformation(not(eq(EMPTY_VALUE)),
                and(not(eq(INVALID_CITY)), not(eq(EMPTY_VALUE))),
                any(OnWeatherInfoCompletedListener.class));
    }
}
