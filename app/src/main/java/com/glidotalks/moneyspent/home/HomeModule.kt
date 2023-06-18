package com.glidotalks.moneyspent.home

import com.glidotalks.android.core.datastore.UserPrefRepository
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @Provides
    fun provideHomeEntryUsecase(
        dispatcherProvider: DispatcherProvider,
        userPrefRepository: UserPrefRepository
    ) = HomeScreenEntryUsecase(
        dispatcherProvider,
        userPrefRepository
    )
}