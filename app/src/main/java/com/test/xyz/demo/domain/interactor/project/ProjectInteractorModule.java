package com.test.xyz.demo.domain.interactor.project;

import android.app.Application;

import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ProjectInteractorModule {
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        int cacheSize = 15 * 1024 * 1024; // 15 MB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    ProjectListRepository provideRepoListService(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProjectListRepository.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ProjectListRepository.class);
    }

    @Provides
    @Singleton
    ProjectInteractor provideProjectInteractor(ProjectListRepository projectListRepository) {
        return new ProjectInteractorImpl(projectListRepository);
    }
}
