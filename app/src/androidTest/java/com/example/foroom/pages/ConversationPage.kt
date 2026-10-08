package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.editTextOfInput
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ConversationPage {
    val chatHeader = withId(R.id.chatHeaderView)
    val messagesRecyclerView = withId(R.id.messagesRecyclerView)
    val messageInput = withId(R.id.messageInput)
    val messageEditText = editTextOfInput(R.id.messageInput)
    val sendMessageButton: Matcher<View> = allOf(
        withId(R.id.sendMessageButton),
        isDescendantOfA(messageInput)
    )
    val closeButton: Matcher<View> = allOf(
        withId(R.id.closeButton),
        hasSibling(messagesRecyclerView)
    )

    fun chatTitle(title: String): Matcher<View> = allOf(
        withId(DesignR.id.chatNameTextView),
        withText(title),
        isDescendantOfA(chatHeader)
    )

    fun message(text: String): Matcher<View> = allOf(
        withId(DesignR.id.messageTextView),
        withText(text),
        isDescendantOfA(messagesRecyclerView)
    )

    fun messageFromSender(text: String, senderName: String): Matcher<View> = allOf(
        withId(DesignR.id.contentLinearLayout),
        isDescendantOfA(messagesRecyclerView),
        hasDescendant(allOf(withId(DesignR.id.messageTextView), withText(text))),
        hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(senderName)))
    )
}
