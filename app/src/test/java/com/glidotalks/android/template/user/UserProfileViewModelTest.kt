@file:OptIn(ExperimentalCoroutinesApi::class)

package com.glidotalks.android.template.user

import com.glidotalks.android.template.common.CoroutineTestRule
import com.glidotalks.moneyspent.github.feature.user.usecase.UserProfileUsecase
import com.glidotalks.moneyspent.github.feature.user.usecase.UserProfileUsecaseResult
import com.glidotalks.moneyspent.github.feature.user.viewmodel.UserProfileViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class UserProfileViewModelTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var userProfileUsecase: UserProfileUsecase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get user state test`() {
        runTest {
            whenever(userProfileUsecase(Unit)).thenReturn(flowOf(UserProfileUsecaseResult.Failure()))

            val viewModel = UserProfileViewModel(
                coroutineTestRule.testDispatcherProvider,
                userProfileUsecase
            )

            assertThat(viewModel.userState.value).isEqualTo("loading")
        }
    }
}