package com.glidotalks.moneyspent.github.feature.user.di

import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.moneyspent.github.api.GithubApiService
import com.glidotalks.moneyspent.github.feature.user.datasource.UserLocalDatasource
import com.glidotalks.moneyspent.github.feature.user.datasource.UserRemoteDatasource
import com.glidotalks.moneyspent.github.feature.user.repository.UserRepository
import com.glidotalks.moneyspent.github.feature.user.repository.UserRepositoryImpl
import com.glidotalks.moneyspent.github.feature.user.usecase.UserProfileUsecase
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    companion object {

        @Provides
        fun provideUserProfileUsecase(
            dispatcherProvider: DispatcherProvider,
            userRepository: UserRepository
        ) = UserProfileUsecase(dispatcherProvider, userRepository)

        @Provides
        fun provideUserRemoteDataSource(
            moshi: Moshi,
            apiService: GithubApiService
        ) = UserRemoteDatasource(moshi, apiService)

        @Provides
        fun provideUserLocalDataSource() = UserLocalDatasource()
    }

}