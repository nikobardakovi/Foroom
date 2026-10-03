package com.example.foroom.Helper

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

private const val DEFAULT_TIMEOUT_MS = 10_000L
private const val POLL_INTERVAL_MS = 100L

/**
 * Retries [block] until it stops throwing or [timeoutMs] elapses, then rethrows the last failure.
 * Network calls and avatar loading are not tracked by Espresso's idling mechanism, so screens
 * that depend on them have to be polled.
 */
fun waitUntil(timeoutMs: Long = DEFAULT_TIMEOUT_MS, block: () -> Unit) {
    val deadline = System.currentTimeMillis() + timeoutMs
    while (true) {
        try {
            block()
            return
        } catch (e: Exception) {
            if (System.currentTimeMillis() >= deadline) throw e
        } catch (e: AssertionError) {
            if (System.currentTimeMillis() >= deadline) throw e
        }
        Thread.sleep(POLL_INTERVAL_MS)
    }
}

fun waitUntilDisplayed(matcher: Matcher<View>, timeoutMs: Long = DEFAULT_TIMEOUT_MS) {
    waitUntil(timeoutMs) { onView(matcher).check(matches(isDisplayed())) }
}

fun waitAndPerform(
    matcher: Matcher<View>,
    action: ViewAction,
    timeoutMs: Long = DEFAULT_TIMEOUT_MS
) {
    waitUntil(timeoutMs) { onView(matcher).check(matches(isDisplayed())).perform(action) }
}

/** The `inputEditText` of the custom `Input` view with the given id. */
fun editTextOfInput(inputId: Int): Matcher<View> =
    allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(inputId)))

/** The `descriptionTextView` (info/error message) of the custom `Input` view with the given id. */
fun descriptionOfInput(inputId: Int): Matcher<View> =
    allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(withId(inputId)))
