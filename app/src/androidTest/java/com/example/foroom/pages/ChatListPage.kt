package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ChatListPage {
    val chatsRecyclerView = withId(R.id.chatsRecyclerView)
    val searchEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun chatCardTitle(title: String): Matcher<View> = allOf(
        withId(DesignR.id.chatTitleTextView),
        withText(title),
        isDescendantOfA(chatsRecyclerView)
    )

    fun chatCardOpenButton(title: String): Matcher<View> = allOf(
        withId(DesignR.id.sendMessageButton),
        hasSibling(chatCardTitle(title))
    )
}
