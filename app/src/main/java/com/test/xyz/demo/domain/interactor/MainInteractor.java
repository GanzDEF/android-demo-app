package com.test.xyz.demo.domain.interactor;

import com.test.xyz.demo.ui.repodetails.mvp.OnRepoDetailsCompletedListener;
import com.test.xyz.demo.ui.repolist.mvp.OnRepoListCompletedListener;
import com.test.xyz.demo.ui.weather.mvp.OnWeatherInfoCompletedListener;

public interface MainInteractor {
    void getWeatherInformation(String userName, String cityName, final OnWeatherInfoCompletedListener listener);

    void getRepoList(String userName, final OnRepoListCompletedListener listener);

    void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}