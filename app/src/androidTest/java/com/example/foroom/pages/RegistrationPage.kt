package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.editTextOfInput
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil
import com.example.foroom.Helper.waitUntilDisplayed
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.TypeSafeMatcher

object RegistrationPage {
    private const val AVATAR_COLUMNS = 3

    private val userNameInput = withId(R.id.userNameInput)
    private val passwordInput = withId(R.id.passwordInput)
    private val repeatPasswordInput = withId(R.id.repeatPasswordInput)
    private val avatarList = withId(R.id.listView)
    private val signUpButton = withId(R.id.signUpButton)

    private val userNameEditText = editTextOfInput(R.id.userNameInput)
    private val passwordEditText = editTextOfInput(R.id.passwordInput)
    private val repeatPasswordEditText = editTextOfInput(R.id.repeatPasswordInput)

    fun waitUntilDisplayed() {
        waitUntilDisplayed(repeatPasswordInput)
    }

    fun assertDisplayed() {
        waitUntil {
            onView(userNameInput).check(matches(isDisplayed()))
            onView(passwordInput).check(matches(isDisplayed()))
            onView(repeatPasswordInput).check(matches(isDisplayed()))
            onView(avatarList).check(matches(isDisplayed()))
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

    fun enterRepeatPassword(password: String) {
        waitAndPerform(repeatPasswordEditText, replaceText(password))
        onView(repeatPasswordEditText).perform(closeSoftKeyboard())
    }

    /**
     * Avatars are fetched asynchronously and cannot be chosen until they are loaded, so the click
     * is retried until the list accepts the selection.
     */
    fun selectAvatar(index: Int) {
        waitUntil {
            onView(avatarList).check(matches(hasLoadedAvatars()))
            onView(avatarItemAt(index)).perform(click())
            onView(avatarList).check(matches(hasSelectedIndex(index)))
        }
    }

    fun tapSignUp() {
        waitAndPerform(signUpButton, click())
    }

    private fun avatarItemAt(index: Int): Matcher<View> = allOf(
        instanceOf(ImageChooserItemView::class.java),
        isDescendantOfA(avatarList),
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("avatar item at index $index")
            }

            // Items are laid out in rows; each row also holds filler views between the items.
            override fun matchesSafely(view: View): Boolean {
                val row = view.parent as? ViewGroup ?: return false
                val list = row.parent as? ViewGroup ?: return false
                val column = row.indexOfChild(view) / 2
                return list.indexOfChild(row) * AVATAR_COLUMNS + column == index
            }
        }
    )

    private fun hasLoadedAvatars(): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("avatar list with loaded, selectable avatars")
        }

        override fun matchesSafely(view: View) =
            view is ImageChooserListView && view.images.isNotEmpty() && view.isChoosingEnabled
    }

    private fun hasSelectedIndex(index: Int): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("avatar list with selected index $index")
        }

        override fun matchesSafely(view: View) =
            view is ImageChooserListView && view.selectedIndex == index
    }
}
