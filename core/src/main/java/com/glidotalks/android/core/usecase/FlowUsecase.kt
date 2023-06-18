package com.glidotalks.android.core.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class FlowUsecase<in P, R>(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(param: P): Flow<R> = execute(param)
        .catch { throwable ->
            Timber.e(throwable)
            exception(this)
        }.flowOn(dispatcher)


    protected abstract suspend fun execute(param: P): Flow<R>

    protected abstract suspend fun exception(flowCollector: FlowCollector<R>)
}