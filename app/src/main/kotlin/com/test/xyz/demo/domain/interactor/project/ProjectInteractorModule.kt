package com.test.xyz.demo.domain.interactor.project

import com.test.xyz.demo.domain.common.di.CommonModule
import com.test.xyz.demo.domain.repository.api.ProjectListRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(CommonModule::class))
class ProjectInteractorModule {

    @Provides
    @Singleton
    internal fun provideProjectListRepository(client: OkHttpClient): ProjectListRepository {
        val retrofit = Retrofit.Builder()
                .baseUrl(ProjectListRepository.HTTPS_API_GITHUB_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        return retrofit.create(ProjectListRepository::class.java)
    }

    @Provides
    @Singleton
    internal fun provideProjectInteractor(projectListRepository: ProjectListRepository): ProjectInteractor {
        return ProjectInteractorImpl(projectListRepository)
    }
}
