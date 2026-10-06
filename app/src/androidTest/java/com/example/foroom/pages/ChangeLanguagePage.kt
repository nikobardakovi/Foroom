package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil

object ChangeLanguagePage {
    private val georgianButton = withId(R.id.languageButtonGeo)
    private val englishButton = withId(R.id.languageButtonEng)

    fun assertDisplayed() {
        waitUntil {
            onView(georgianButton).check(matches(isDisplayed()))
            onView(englishButton).check(matches(isDisplayed()))
        }
    }

    fun selectGeorgian() {
        waitAndPerform(georgianButton, click())
    }

    fun selectEnglish() {
        waitAndPerform(englishButton, click())
    }
}
