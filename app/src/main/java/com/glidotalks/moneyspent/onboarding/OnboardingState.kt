package com.glidotalks.moneyspent.onboarding

sealed class OnboardingState {
    object Welcome : OnboardingState()
    object SetCurrency : OnboardingState()
}