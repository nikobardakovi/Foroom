package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.descriptionOfInput
import com.example.foroom.Helper.editTextOfInput
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil
import com.example.foroom.Helper.waitUntilDisplayed
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.emptyString
import org.hamcrest.Matchers.not

object LoginPage {
    private val userNameInput = withId(R.id.userNameInput)
    private val passwordInput = withId(R.id.passwordInput)
    private val logInButton = withId(R.id.logInButton)
    private val signUpButton = withId(R.id.signUpButton)

    private val userNameEditText = editTextOfInput(R.id.userNameInput)
    private val passwordEditText = editTextOfInput(R.id.passwordInput)
    private val userNameError = descriptionOfInput(R.id.userNameInput)
    private val passwordError = descriptionOfInput(R.id.passwordInput)

    fun waitUntilDisplayed() {
        waitUntilDisplayed(logInButton)
    }

    fun assertDisplayed() {
        waitUntil {
            onView(userNameInput).check(matches(isDisplayed()))
            onView(passwordInput).check(matches(isDisplayed()))
            onView(logInButton).check(matches(isDisplayed()))
            onView(signUpButton).check(matches(isDisplayed()))
        }
    }

    fun enterUserName(userName: String) {
        waitAndPerform(userNameEditText, replaceText(userName))
        onView(userNameEditText).perform(closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        waitAndPerform(passwordEditText, replaceText(password))
        onView(passwordEditText).perform(closeSoftKeyboard())
    }

    fun tapLogIn() {
        waitAndPerform(logInButton, click())
    }

    fun tapSignUp() {
        waitAndPerform(signUpButton, click())
    }

    fun assertUserNameErrorDisplayed() {
        waitUntil {
            onView(userNameError).check(matches(allOf(isDisplayed(), withText(not(emptyString())))))
        }
    }

    fun assertPasswordErrorDisplayed() {
        waitUntil {
            onView(passwordError).check(matches(allOf(isDisplayed(), withText(not(emptyString())))))
        }
    }
}
