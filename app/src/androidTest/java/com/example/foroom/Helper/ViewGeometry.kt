package com.example.foroom.Helper

import android.graphics.Rect
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher

/** The view's bounds in screen coordinates, the coordinate system used by [swiper]. */
fun Matcher<View>.boundsOnScreen(): Rect {
    val bounds = Rect()
    onView(this).perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> = isDisplayed()

        override fun getDescription(): String = "read the view's bounds on screen"

        override fun perform(uiController: UiController, view: View) {
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            bounds.set(
                location[0],
                location[1],
                location[0] + view.width,
                location[1] + view.height
            )
        }
    })
    return bounds
}

/**
 * Taps the centre of the part of the view that is visible on screen. Unlike Espresso's
 * `click()`, this also works for a view that is partly covered or clipped, as long as a user
 * could still reach it, e.g. a list item cut off by the navigation bar on a small screen.
 */
fun clickOnVisiblePart(): ViewAction {
    val click = GeneralClickAction(
        Tap.SINGLE,
        { view ->
            val visible = Rect()
            view.getGlobalVisibleRect(visible)
            val onScreen = IntArray(2).also(view::getLocationOnScreen)
            val inWindow = IntArray(2).also(view::getLocationInWindow)
            floatArrayOf(
                visible.exactCenterX() + onScreen[0] - inWindow[0],
                visible.exactCenterY() + onScreen[1] - inWindow[1]
            )
        },
        Press.FINGER,
        InputDevice.SOURCE_UNKNOWN,
        MotionEvent.BUTTON_PRIMARY
    )
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isDisplayed()

        override fun getDescription(): String = "click on the visible part of the view"

        override fun perform(uiController: UiController, view: View) {
            click.perform(uiController, view)
        }
    }
}
