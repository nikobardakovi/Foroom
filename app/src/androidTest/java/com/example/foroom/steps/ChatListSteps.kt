package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import com.example.foroom.Helper.isDisplayedWithin
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntil
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.constants.Timeouts
import com.example.foroom.pages.ChatListPage
import com.example.foroom.pages.ConversationPage

class ChatListSteps {
    fun checkChatListIsDisplayed(): ChatListSteps {
        waitUntilDisplayed(ChatListPage.searchEditText, Timeouts.SCREEN_TIMEOUT_MS)
        return this
    }

    fun searchChat(title: String): ChatListSteps {
        waitAndPerform(ChatListPage.searchEditText, replaceText(title))
        onView(ChatListPage.searchEditText).perform(closeSoftKeyboard())
        return this
    }

    fun checkChatIsListed(title: String): ChatListSteps {
        waitUntilDisplayed(ChatListPage.chatCardTitle(title))
        return this
    }

    /**
     * The search reloads the list asynchronously, so a card that is already visible can be
     * replaced while it is tapped. The tap is repeated until the conversation screen opens.
     */
    fun openChat(title: String): ConversationSteps {
        waitUntil(Timeouts.SCREEN_TIMEOUT_MS) {
            if (!isDisplayedWithin(ConversationPage.messagesRecyclerView, NO_WAIT)) {
                onView(ChatListPage.chatCardOpenButton(title)).perform(click())
            }
            waitUntilDisplayed(ConversationPage.messagesRecyclerView, Timeouts.SCREEN_OPEN_TIMEOUT_MS)
        }
        return ConversationSteps()
    }

    private companion object {
        const val NO_WAIT = 0L
    }
}
