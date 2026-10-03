package com.example.foroom.steps

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.pages.RegistrationPage

object RegistrationSteps {
    private const val AVATAR_INDEX = 2

    fun checkRegistrationScreenIsDisplayed() {
        RegistrationPage.waitUntilDisplayed()
        RegistrationPage.assertDisplayed()
    }

    fun register(userName: String, password: String) {
        RegistrationPage.enterUserName(userName)
        RegistrationPage.enterPassword(password)
        RegistrationPage.enterRepeatPassword(password)
        RegistrationPage.selectAvatar(AVATAR_INDEX)
        RegistrationPage.tapSignUp()
    }

    fun checkHomeScreenIsDisplayed() {
        waitUntilDisplayed(withId(R.id.homeContainer))
        waitUntilDisplayed(withId(R.id.navBar))
    }
}
