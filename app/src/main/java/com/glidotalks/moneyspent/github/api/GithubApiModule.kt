package com.glidotalks.moneyspent.github.api

import com.glidotalks.android.core.di.ApplicationScope
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class GithubApiModule {

    @Provides
    @ApplicationScope
    @GithubOkhttpClient
    fun provideGithubOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @ApplicationScope
    @GithubRetrofit
    fun provideGithubRetrofit(
        moshi: Moshi,
        @GithubOkhttpClient okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @ApplicationScope
    fun provideGithubApiService(
        @GithubRetrofit retrofit: Retrofit
    ) = retrofit.create(GithubApiService::class.java)
}
