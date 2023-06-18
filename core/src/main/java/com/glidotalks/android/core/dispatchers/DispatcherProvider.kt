package com.glidotalks.android.core.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
}