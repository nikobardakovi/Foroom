package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.constants.Timeouts
import com.example.foroom.pages.LoginPage

class LoginSteps {
    fun checkLoginScreenIsDisplayed(): LoginSteps {
        waitUntilDisplayed(LoginPage.logInButton, Timeouts.SCREEN_TIMEOUT_MS)
        waitUntilDisplayed(LoginPage.userNameInput)
        waitUntilDisplayed(LoginPage.passwordInput)
        return this
    }

    fun enterUserName(userName: String): LoginSteps {
        waitAndPerform(LoginPage.userNameEditText, replaceText(userName))
        onView(LoginPage.userNameEditText).perform(closeSoftKeyboard())
        return this
    }

    fun enterPassword(password: String): LoginSteps {
        waitAndPerform(LoginPage.passwordEditText, replaceText(password))
        onView(LoginPage.passwordEditText).perform(closeSoftKeyboard())
        return this
    }

    fun tapLogIn(): HomeSteps {
        waitAndPerform(LoginPage.logInButton, click())
        return HomeSteps()
    }
}
