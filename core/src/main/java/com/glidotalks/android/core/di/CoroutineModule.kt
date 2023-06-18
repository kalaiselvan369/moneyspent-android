package com.glidotalks.android.core.di

import com.glidotalks.android.core.dispatchers.CoroutineDispatcherProvider
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineModule {

    @Binds
    abstract fun bindDispatcherProvider(
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): DispatcherProvider
}