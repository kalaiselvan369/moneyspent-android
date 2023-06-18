package com.glidotalks.moneyspent.onboarding

import com.glidotalks.android.core.datastore.UserPrefRepository
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class OnboardingModule {

    @Provides
    fun provideSetCurrencyUsecase(
        dispatcherProvider: DispatcherProvider,
        userPrefRepository: UserPrefRepository
    ) =
        SetCurrencyUsecase(
            dispatcherProvider,
            userPrefRepository
        )
}