package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil
import org.hamcrest.Matchers.allOf

object ChatsPage {
    private val navBar = withId(R.id.navBar)
    private val homeContainer = withId(R.id.homeContainer)
    private val chatsNavigationButton = withId(R.id.homeNavigationChats)
    private val chatsRecyclerView = withId(R.id.chatsRecyclerView)
    private val searchEditText = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    private val chatHeader = withId(R.id.chatHeaderView)
    private val messagesRecyclerView = withId(R.id.messagesRecyclerView)
    private val closeChatButton = allOf(
        withId(R.id.closeButton),
        hasSibling(messagesRecyclerView)
    )

    fun assertHomeDisplayed() {
        waitUntil {
            onView(homeContainer).check(matches(isDisplayed()))
            onView(navBar).check(matches(isDisplayed()))
        }
    }

    fun open() {
        waitAndPerform(chatsNavigationButton, click())
    }

    fun searchChat(name: String) {
        waitAndPerform(searchEditText, replaceText(name))
        onView(searchEditText).perform(closeSoftKeyboard())
    }

    fun assertChatCardDisplayed(name: String) {
        val chatCardTitle = allOf(
            withId(DesignR.id.chatTitleTextView),
            withText(name),
            isDescendantOfA(chatsRecyclerView)
        )
        waitUntil { onView(chatCardTitle).check(matches(isDisplayed())) }
    }

    fun assertOpenedChatDisplayed(name: String) {
        val chatTitle = allOf(
            withId(DesignR.id.chatNameTextView),
            withText(name),
            isDescendantOfA(chatHeader)
        )
        waitUntil {
            onView(messagesRecyclerView).check(matches(isDisplayed()))
            onView(chatTitle).check(matches(isDisplayed()))
        }
    }

    fun closeOpenedChat() {
        waitAndPerform(closeChatButton, click())
    }
}
