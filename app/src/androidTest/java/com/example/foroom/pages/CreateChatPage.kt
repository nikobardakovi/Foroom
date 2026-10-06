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
import com.example.shared.model.Image
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.TypeSafeMatcher

object CreateChatPage {
    private const val IMAGE_COLUMNS = 3

    private val createChatNavigationButton = withId(R.id.homeNavigationCreateChat)
    private val chatNameInput = withId(R.id.chatNameInput)
    private val chatImageChooser = withId(R.id.chatImageChooser)
    private val createChatButton = withId(R.id.createChatButton)

    private val chatNameEditText = editTextOfInput(R.id.chatNameInput)

    fun open() {
        waitAndPerform(createChatNavigationButton, click())
    }

    fun assertDisplayed() {
        waitUntil {
            onView(chatNameInput).check(matches(isDisplayed()))
            onView(chatImageChooser).check(matches(isDisplayed()))
            onView(createChatButton).check(matches(isDisplayed()))
        }
    }

    fun enterChatName(name: String) {
        waitAndPerform(chatNameEditText, replaceText(name))
        onView(chatNameEditText).perform(closeSoftKeyboard())
    }

    fun selectImage(index: Int) {
        waitUntil {
            onView(chatImageChooser).check(matches(hasLoadedImages()))
            onView(imageItemAt(index)).perform(click())
            onView(chatImageChooser).check(matches(hasSelectedIndex(index)))
        }
    }

    fun tapCreateChat() {
        waitAndPerform(createChatButton, click())
    }

    private fun imageItemAt(index: Int): Matcher<View> = allOf(
        instanceOf(ImageChooserItemView::class.java),
        isDescendantOfA(chatImageChooser),
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("chat image item at index $index")
            }

            override fun matchesSafely(view: View): Boolean {
                val row = view.parent as? ViewGroup ?: return false
                val list = row.parent as? ViewGroup ?: return false
                val column = row.indexOfChild(view) / 2
                return list.indexOfChild(row) * IMAGE_COLUMNS + column == index
            }
        }
    )

    private fun hasLoadedImages(): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("image chooser with loaded images")
        }

        override fun matchesSafely(view: View) = view is ImageChooserListView &&
            view.images.isNotEmpty() &&
            view.images.none { image -> image.id == Image.BLANK_IMAGE_ID }
    }

    private fun hasSelectedIndex(index: Int): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("image chooser with selected index $index")
        }

        override fun matchesSafely(view: View) =
            view is ImageChooserListView && view.selectedIndex == index
    }
}
