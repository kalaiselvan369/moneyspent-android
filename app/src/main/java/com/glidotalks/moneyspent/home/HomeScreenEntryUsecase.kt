package com.glidotalks.moneyspent.home

import com.glidotalks.android.core.datastore.UserPrefRepository
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.android.core.usecase.FlowUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class HomeScreenEntryUsecase(
    dispatcherProvider: DispatcherProvider,
    private val userPrefRepository: UserPrefRepository
) : FlowUsecase<Unit, HomeScreenEntryUsecaseResult>(dispatcherProvider.io) {
    override suspend fun execute(param: Unit): Flow<HomeScreenEntryUsecaseResult> {
        return flow {
            userPrefRepository.isOnboardingCompleted().collect {
                if (it) {
                    emit(HomeScreenEntryUsecaseResult.HomeEntryGranted)
                } else {
                    emit(HomeScreenEntryUsecaseResult.OnboardingNotCompleted)
                }
            }
        }
    }

    override suspend fun exception(flowCollector: FlowCollector<HomeScreenEntryUsecaseResult>) {
        flowCollector.emit(HomeScreenEntryUsecaseResult.Failure)
    }
}

sealed class HomeScreenEntryUsecaseResult {

    object OnboardingNotCompleted : HomeScreenEntryUsecaseResult()
    object HomeEntryGranted : HomeScreenEntryUsecaseResult()
    object Failure : HomeScreenEntryUsecaseResult()
}