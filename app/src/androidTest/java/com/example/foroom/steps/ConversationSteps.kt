package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import com.example.foroom.Helper.boundsOnScreen
import com.example.foroom.Helper.isDisplayedWithin
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.Helper.waitUntilEmpty
import com.example.foroom.Helper.waitUntilEnabled
import com.example.foroom.constants.SwipeSettings
import com.example.foroom.constants.Timeouts
import com.example.foroom.pages.ConversationPage
import org.junit.Assert.assertFalse

class ConversationSteps {
    fun checkConversationIsOpen(title: String): ConversationSteps {
        waitUntilDisplayed(ConversationPage.messagesRecyclerView, Timeouts.SCREEN_TIMEOUT_MS)
        waitUntilDisplayed(ConversationPage.chatTitle(title))
        return this
    }

    fun waitUntilChatIsConnected(): ConversationSteps {
        waitUntilEnabled(ConversationPage.sendMessageButton, Timeouts.SCREEN_TIMEOUT_MS)
        return this
    }

    fun enterMessage(text: String): ConversationSteps {
        waitAndPerform(ConversationPage.messageEditText, replaceText(text))
        return this
    }

    fun tapSend(): ConversationSteps {
        waitAndPerform(ConversationPage.sendMessageButton, click())
        return this
    }

    /** The app clears the input only after the message has been saved. */
    fun waitUntilMessageIsSent(): ConversationSteps {
        waitUntilEmpty(ConversationPage.messageEditText, Timeouts.MESSAGE_SENT_TIMEOUT_MS)
        waitUntilEnabled(ConversationPage.sendMessageButton)
        return this
    }

    fun sendMessages(texts: List<String>): ConversationSteps {
        texts.forEach { text ->
            enterMessage(text)
            tapSend()
            waitUntilMessageIsSent()
        }
        return this
    }

    fun hideKeyboard(): ConversationSteps {
        onView(ConversationPage.messageEditText).perform(closeSoftKeyboard())
        return this
    }

    fun checkMessageIsDisplayed(text: String): ConversationSteps {
        waitUntilDisplayed(ConversationPage.message(text))
        return this
    }

    fun checkMessageIsDisplayedWithSender(text: String, senderName: String): ConversationSteps {
        waitUntilDisplayed(ConversationPage.messageFromSender(text, senderName))
        return this
    }

    fun checkMessageIsNotDisplayed(text: String): ConversationSteps {
        assertFalse(
            "Message \"$text\" should be outside the visible message area",
            isDisplayedWithin(ConversationPage.message(text), Timeouts.NOT_DISPLAYED_CHECK_MS)
        )
        return this
    }

    /**
     * Drags the message list downwards, which reveals older messages because the list is
     * reversed. The gesture is placed between the chat header and the message input of the
     * current device, and the number of swipes is bounded so a missing message fails the test.
     */
    fun swipeToOlderMessagesUntilDisplayed(text: String): ConversationSteps {
        val visibleTop = ConversationPage.chatHeader.boundsOnScreen().bottom
        val visibleBottom = ConversationPage.messageInput.boundsOnScreen().top
        val visibleHeight = visibleBottom - visibleTop
        val startY = visibleTop + (visibleHeight * SwipeSettings.START_FRACTION).toInt()
        val endY = visibleTop + (visibleHeight * SwipeSettings.END_FRACTION).toInt()

        repeat(SwipeSettings.MAX_SWIPES) {
            if (isDisplayedWithin(
                    ConversationPage.message(text),
                    SwipeSettings.CHECK_AFTER_SWIPE_TIMEOUT_MS
                )
            ) return this
            swiper(startY, endY, SwipeSettings.SWIPE_DURATION_MS)
        }
        waitUntilDisplayed(ConversationPage.message(text))
        return this
    }

    fun closeChat(): HomeSteps {
        waitAndPerform(ConversationPage.closeButton, click())
        return HomeSteps()
    }
}
