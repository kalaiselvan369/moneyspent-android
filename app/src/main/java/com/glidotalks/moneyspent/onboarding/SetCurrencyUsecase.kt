package com.glidotalks.moneyspent.onboarding

import com.glidotalks.android.core.datastore.UserPrefRepository
import com.glidotalks.android.core.dispatchers.DispatcherProvider
import com.glidotalks.android.core.usecase.FlowUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class SetCurrencyUsecase(
    dispatcherProvider: DispatcherProvider,
    private val userPrefRepository: UserPrefRepository
) : FlowUsecase<String, Unit>(dispatcherProvider.io) {
    override suspend fun execute(param: String): Flow<Unit> {
        userPrefRepository.saveUserCurrency(param)
        userPrefRepository.isOnboardingCompleted()
        return flow {
            emit(Unit)
        }
    }

    override suspend fun exception(flowCollector: FlowCollector<Unit>) {
        TODO("Not yet implemented")
    }
}