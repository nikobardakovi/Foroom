package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.constants.ChatTitles
import com.example.foroom.constants.MessageTexts
import com.example.foroom.constants.TestUser
import com.example.foroom.constants.TestUsers
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.HomeSteps
import com.example.foroom.steps.LoginSteps
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationTests {
    private val suffix = System.currentTimeMillis().toString()

    @get:Rule
    val rules: RuleChain = RuleChain
        .outerRule(PreparedConversationData())
        .around(ActivityScenarioRule(ForoomActivity::class.java))

    @Test
    fun userA_sendsDrinkInvitationInJohnWeek_andMessageRemainsAfterReopeningChat() {
        val invitation = "${MessageTexts.DRINK_INVITATION} $suffix"

        logInAs(TestUsers.USER_A)
            .openChatsTab()
            .checkChatListIsDisplayed()
            .searchChat(ChatTitles.JOHN_WEEK)
            .checkChatIsListed(ChatTitles.JOHN_WEEK)
            .openChat(ChatTitles.JOHN_WEEK)
            .checkConversationIsOpen(ChatTitles.JOHN_WEEK)
            .waitUntilChatIsConnected()
            .enterMessage(invitation)
            .tapSend()
            .waitUntilMessageIsSent()
            .checkMessageIsDisplayed(invitation)
            .closeChat()
            .checkHomeScreenIsDisplayed()
            .openChatsTab()
            .checkChatIsListed(ChatTitles.JOHN_WEEK)
            .openChat(ChatTitles.JOHN_WEEK)
            .checkConversationIsOpen(ChatTitles.JOHN_WEEK)
            .checkMessageIsDisplayedWithSender(invitation, TestUsers.USER_A.userName)
    }

    @Test
    fun userA_asksAboutFavouriteAcademyModuleInFullNameChat() {
        val question = "${MessageTexts.ACADEMY_QUESTION} $suffix"

        logInAs(TestUsers.USER_A)
            .openChatsTab()
            .checkChatListIsDisplayed()
            .searchChat(ChatTitles.FULL_NAME_CHAT)
            .checkChatIsListed(ChatTitles.FULL_NAME_CHAT)
            .openChat(ChatTitles.FULL_NAME_CHAT)
            .checkConversationIsOpen(ChatTitles.FULL_NAME_CHAT)
            .waitUntilChatIsConnected()
            .enterMessage(question)
            .tapSend()
            .waitUntilMessageIsSent()
            .checkMessageIsDisplayedWithSender(question, TestUsers.USER_A.userName)
    }

    @Test
    fun userB_readsOlderGreetingAndReplies_andUserA_seesReplyInSharedChat() {
        val greeting = "${MessageTexts.GREETING} $suffix"
        val reply = "${MessageTexts.REPLY} $suffix"
        val fillerMessages = (1..MessageTexts.FILLER_MESSAGE_COUNT).map { number ->
            "${MessageTexts.FILLER} $number $suffix"
        }
        val newestFillerMessage = fillerMessages.last()

        logInAs(TestUsers.USER_A)
            .openChatsTab()
            .checkChatListIsDisplayed()
            .searchChat(ChatTitles.SHARED_CHAT)
            .checkChatIsListed(ChatTitles.SHARED_CHAT)
            .openChat(ChatTitles.SHARED_CHAT)
            .checkConversationIsOpen(ChatTitles.SHARED_CHAT)
            .waitUntilChatIsConnected()
            .enterMessage(greeting)
            .tapSend()
            .waitUntilMessageIsSent()
            .checkMessageIsDisplayed(greeting)
            .sendMessages(fillerMessages)
            .checkMessageIsDisplayed(newestFillerMessage)
            .hideKeyboard()
            .closeChat()
            .openProfileTab()
            .checkProfileIsDisplayed()
            .tapSignOut()

        logInAs(TestUsers.USER_B)
            .openChatsTab()
            .checkChatListIsDisplayed()
            .searchChat(ChatTitles.SHARED_CHAT)
            .checkChatIsListed(ChatTitles.SHARED_CHAT)
            .openChat(ChatTitles.SHARED_CHAT)
            .checkConversationIsOpen(ChatTitles.SHARED_CHAT)
            .waitUntilChatIsConnected()
            .checkMessageIsDisplayed(newestFillerMessage)
            .checkMessageIsNotDisplayed(greeting)
            .swipeToOlderMessagesUntilDisplayed(greeting)
            .checkMessageIsDisplayedWithSender(greeting, TestUsers.USER_A.userName)
            .enterMessage(reply)
            .tapSend()
            .waitUntilMessageIsSent()
            .checkMessageIsDisplayedWithSender(reply, TestUsers.USER_B.userName)
            .hideKeyboard()
            .closeChat()
            .openProfileTab()
            .checkProfileIsDisplayed()
            .tapSignOut()

        logInAs(TestUsers.USER_A)
            .openChatsTab()
            .checkChatListIsDisplayed()
            .searchChat(ChatTitles.SHARED_CHAT)
            .checkChatIsListed(ChatTitles.SHARED_CHAT)
            .openChat(ChatTitles.SHARED_CHAT)
            .checkConversationIsOpen(ChatTitles.SHARED_CHAT)
            .checkMessageIsDisplayedWithSender(reply, TestUsers.USER_B.userName)
    }

    private fun logInAs(user: TestUser): HomeSteps = LoginSteps()
        .checkLoginScreenIsDisplayed()
        .enterUserName(user.userName)
        .enterPassword(user.password)
        .tapLogIn()
        .checkHomeScreenIsDisplayed()
}
