package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.usecase.RegisterUserUseCase
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {
    private val chatOwnerFullName = "nikoloz bardakovi"

    private val userName = "profile_${System.currentTimeMillis()}"
    private val currentPassword = "Current123!"
    private val newPassword = "Changed123!"

    private val testUserState = TestRule { base, _ ->
        object : Statement() {
            override fun evaluate() {
                val koin = GlobalContext.get()
                runBlocking {
                    koin.get<RegisterUserUseCase>()(
                        RegistrationRequest(userName, currentPassword, AVATAR_ID)
                    )
                    koin.get<ForoomUserDataStore>().clearUserData()
                }
                base.evaluate()
            }
        }
    }
    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(testUserState).around(activityRule)

    @Test
    fun changePassword_logsOut_andNewPasswordWorks() {
        logInAndCheckHome(currentPassword)
        ProfileSteps.openProfile()

        ProfileSteps.changePassword(newPassword)

        LoginSteps.checkLoginScreenIsDisplayed()
        logInAndCheckHome(newPassword)
    }

    @Test
    fun changeLanguage_fromGeorgianToEnglishAndBack() {
        logInAndCheckHome(currentPassword)
        ProfileSteps.openProfile()

        ProfileSteps.changeLanguageToGeorgian()
        ProfileSteps.checkProfileIsInGeorgian()

        ProfileSteps.changeLanguageToEnglish()
        ProfileSteps.checkProfileIsInEnglish()

        ProfileSteps.changeLanguageToGeorgian()
        ProfileSteps.checkProfileIsInGeorgian()
    }

    @Test
    fun createChat_andFindItInChatList() {
        val chatName = "$chatOwnerFullName ${System.currentTimeMillis()}"
        logInAndCheckHome(currentPassword)

        ChatSteps.createChat(chatName)
        ChatSteps.checkOpenedChatIs(chatName)
        ChatSteps.closeChat()

        ChatSteps.searchChat(chatName)
        ChatSteps.checkChatIsListed(chatName)
    }

    private fun logInAndCheckHome(password: String) {
        LoginSteps.checkLoginScreenIsDisplayed()
        LoginSteps.logIn(userName, password)
        ChatSteps.checkHomeScreenIsDisplayed()
    }

    private companion object {
        const val AVATAR_ID = 1
    }
}
