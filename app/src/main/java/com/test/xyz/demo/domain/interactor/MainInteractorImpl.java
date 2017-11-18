package com.test.xyz.demo.domain.interactor;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.Strings;
import com.test.xyz.demo.R;
import com.test.xyz.demo.domain.repository.api.RepoListRepository;
import com.test.xyz.demo.ui.repodetails.mvp.OnRepoDetailsCompletedListener;
import com.test.xyz.demo.ui.repolist.mvp.OnRepoListCompletedListener;
import com.test.xyz.demo.ui.weather.mvp.OnWeatherInfoCompletedListener;
import com.test.xyz.demo.domain.repository.api.HelloRepository;
import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.api.model.Repo;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainInteractorImpl implements MainInteractor {
    private static final String TAG = MainInteractorImpl.class.getName();

    @Inject
    HelloRepository helloRepository;

    @Inject
    WeatherRepository weatherRepository;

    @Inject
    RepoListRepository repoListRepository;

    @Inject
    public MainInteractorImpl() {
    }

    @VisibleForTesting
    MainInteractorImpl(HelloRepository helloRepository, WeatherRepository weatherRepository,
                       RepoListRepository repoListRepository) {

        this.helloRepository = helloRepository;
        this.weatherRepository = weatherRepository;
        this.repoListRepository = repoListRepository;
    }

    @Override
    public void getWeatherInformation(final String userName, final String cityName, final OnWeatherInfoCompletedListener listener) {
        final String greeting = helloRepository.greet(userName) + "\n";

        if (Strings.isNullOrEmpty(userName)) {
            listener.onUserNameValidationError(R.string.username_empty_message);
            return;
        }

        if (Strings.isNullOrEmpty(cityName)) {
            listener.onCityValidationError(R.string.city_empty_message);
            return;
        }

        weatherRepository.getWeatherInfo(cityName).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onNext(Integer temperature) {
                String temp = "Current weather in " + cityName + " is " + temperature + "°F";

                listener.onSuccess(greeting + temp);
            }
        });
    }

    @Override
    public void getRepoList(final String userName, final OnRepoListCompletedListener listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onRepoListRetrievalFailure("Username must be provided!");
            return;
        }

        repoListRepository.getRepoList(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onRepoListRetrievalFailure("Unable to get repo items: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> repoList) {
                        listener.onRepoListRetrievalSuccess(repoList);
                    }
                });
    }

    @Override
    public void getRepoItemDetails(final String userName, final String projectID, final OnRepoDetailsCompletedListener listener) {
        if (Strings.isNullOrEmpty(userName)) {
            listener.onRepoDetailsRetrievalFailure("Username must be provided!");
            return;
        }

        repoListRepository.getRepoItemDetails(userName, projectID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Repo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onRepoDetailsRetrievalFailure("Unable to get repo item details: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Repo repo) {
                        listener.onRepoDetailsRetrievalSuccess(repo);
                    }
                });
    }
}
