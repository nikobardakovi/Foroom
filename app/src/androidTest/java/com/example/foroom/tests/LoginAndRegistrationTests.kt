package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {
    // Existing account of the training backend (see TRAINING.md); create it manually when running
    // against the original server.
    private val existingUserName = "student"
    private val incorrectPassword = "WrongPassword123!"
    private val registrationPassword = "Test1234!"

    private val signedOutState = TestRule { base, _ ->
        object : Statement() {
            override fun evaluate() {
                // A previous test (e.g. registration) leaves the user signed in, which would
                // skip the login screen. Start every test from a signed-out application.
                runBlocking { GlobalContext.get().get<ForoomUserDataStore>().clearUserData() }
                base.evaluate()
            }
        }
    }
    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(signedOutState).around(activityRule)

    @Test
    fun validUserNameAndInvalidPassword_showsPasswordError() {
        LoginSteps.checkLoginScreenIsDisplayed()

        LoginSteps.logIn(existingUserName, incorrectPassword)

        LoginSteps.checkPasswordErrorIsDisplayed()
    }

    @Test
    fun invalidUserNameAndInvalidPassword_showsUserNameAndPasswordErrors() {
        LoginSteps.checkLoginScreenIsDisplayed()

        LoginSteps.logIn(uniqueUserName("missing"), incorrectPassword)

        LoginSteps.checkUserNameErrorIsDisplayed()
        LoginSteps.checkPasswordErrorIsDisplayed()
    }

    @Test
    fun successfulRegistration_opensHomeScreen() {
        LoginSteps.checkLoginScreenIsDisplayed()
        LoginSteps.openRegistration()
        RegistrationSteps.checkRegistrationScreenIsDisplayed()

        RegistrationSteps.register(uniqueUserName("user"), registrationPassword)

        RegistrationSteps.checkHomeScreenIsDisplayed()
    }

    private fun uniqueUserName(prefix: String) = "${prefix}_${System.currentTimeMillis()}"
}
