package com.test.xyz.daggersample.domain.interactor;

import com.test.xyz.daggersample.ui.repodetails.mvp.OnRepoDetailsCompletedListener;
import com.test.xyz.daggersample.ui.repolist.mvp.OnRepoListCompletedListener;
import com.test.xyz.daggersample.ui.weather.mvp.OnWeatherInfoCompletedListener;

public interface MainInteractor {
    void getWeatherInformation(String userName, String cityName, final OnWeatherInfoCompletedListener listener);

    void getRepoList(String userName, final OnRepoListCompletedListener listener);

    void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}