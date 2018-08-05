package com.test.xyz.demo.domain.interactor.project;

import com.test.xyz.demo.domain.common.di.CommonModule;
import com.test.xyz.demo.domain.repository.api.ProjectListRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = CommonModule.class)
public class ProjectInteractorModule {

    @Provides
    @Singleton
    ProjectListRepository provideProjectListRepository(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProjectListRepository.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
