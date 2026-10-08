package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

object LoginSteps {
    fun checkLoginScreenIsDisplayed() {
        LoginPage.waitUntilDisplayed()
        LoginPage.assertDisplayed()
    }

    fun logIn(userName: String, password: String) {
        LoginPage.enterUserName(userName)
        LoginPage.enterPassword(password)
        LoginPage.tapLogIn()
    }

    fun openRegistration() {
        LoginPage.tapSignUp()
    }

    fun checkUserNameErrorIsDisplayed() {
        LoginPage.assertUserNameErrorDisplayed()
    }

    fun checkPasswordErrorIsDisplayed() {
        LoginPage.assertPasswordErrorDisplayed()
    }
}
