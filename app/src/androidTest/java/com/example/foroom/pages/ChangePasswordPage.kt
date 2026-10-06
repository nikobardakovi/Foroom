package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.editTextOfInput
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil

object ChangePasswordPage {
    private val passwordInput = withId(R.id.passwordInput)
    private val repeatPasswordInput = withId(R.id.repeatPasswordInput)
    private val confirmButton = withId(DesignR.id.actionButton)

    private val passwordEditText = editTextOfInput(R.id.passwordInput)
    private val repeatPasswordEditText = editTextOfInput(R.id.repeatPasswordInput)

    fun assertDisplayed() {
        waitUntil {
            onView(passwordInput).check(matches(isDisplayed()))
            onView(repeatPasswordInput).check(matches(isDisplayed()))
            onView(confirmButton).check(matches(isDisplayed()))
        }
    }

    fun enterNewPassword(password: String) {
        waitAndPerform(passwordEditText, replaceText(password))
        onView(passwordEditText).perform(closeSoftKeyboard())
    }

    fun enterRepeatPassword(password: String) {
        waitAndPerform(repeatPasswordEditText, replaceText(password))
        onView(repeatPasswordEditText).perform(closeSoftKeyboard())
    }

    fun tapConfirm() {
        waitAndPerform(confirmButton, click())
    }
}
