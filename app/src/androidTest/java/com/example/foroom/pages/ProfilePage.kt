package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ProfilePage {
    private val profileNavigationButton = withId(R.id.homeNavigationProfile)
    private val changePasswordItem = withId(R.id.changePasswordItem)
    private val changeLanguageItem = withId(R.id.changeLanguageItem)
    private val signOutItem = withId(R.id.signOutItem)

    fun open() {
        waitAndPerform(profileNavigationButton, click())
    }

    fun assertDisplayed() {
        waitUntil {
            onView(changePasswordItem).check(matches(isDisplayed()))
            onView(changeLanguageItem).check(matches(isDisplayed()))
            onView(signOutItem).check(matches(isDisplayed()))
        }
    }

    fun tapChangePassword() {
        waitAndPerform(changePasswordItem, click())
    }

    fun tapChangeLanguage() {
        waitAndPerform(changeLanguageItem, click())
    }

    fun assertChangeLanguageTitle(expectedTitle: String) {
        waitUntil { onView(titleOf(changeLanguageItem, expectedTitle)).check(matches(isDisplayed())) }
    }

    fun assertSignOutTitle(expectedTitle: String) {
        waitUntil { onView(titleOf(signOutItem, expectedTitle)).check(matches(isDisplayed())) }
    }

    private fun titleOf(item: Matcher<View>, text: String): Matcher<View> = allOf(
        withId(DesignR.id.listItemTextView),
        isDescendantOfA(item),
        withText(text)
    )
}
