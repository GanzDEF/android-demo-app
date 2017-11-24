package com.test.xyz.demo.domain.interactor;

import com.test.xyz.demo.ui.repodetails.vp.OnRepoDetailsCompletedListener;
import com.test.xyz.demo.ui.repolist.vp.OnRepoListCompletedListener;
import com.test.xyz.demo.ui.weather.vp.OnWeatherInfoCompletedListener;

public interface MainInteractor {
    void getWeatherInformation(String userName, String cityName, final OnWeatherInfoCompletedListener listener);

    void getRepoList(String userName, final OnRepoListCompletedListener listener);

    void getRepoItemDetails(String userName, String projectID, final OnRepoDetailsCompletedListener listener);
}