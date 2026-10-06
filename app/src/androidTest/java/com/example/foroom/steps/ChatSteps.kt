package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

object ChatSteps {
    private const val CHAT_IMAGE_INDEX = 1

    fun checkHomeScreenIsDisplayed() {
        ChatsPage.assertHomeDisplayed()
    }

    fun createChat(name: String) {
        CreateChatPage.open()
        CreateChatPage.assertDisplayed()
        CreateChatPage.enterChatName(name)
        CreateChatPage.selectImage(CHAT_IMAGE_INDEX)
        CreateChatPage.tapCreateChat()
    }

    fun checkOpenedChatIs(name: String) {
        ChatsPage.assertOpenedChatDisplayed(name)
    }

    fun closeChat() {
        ChatsPage.closeOpenedChat()
        ChatsPage.assertHomeDisplayed()
    }

    fun searchChat(name: String) {
        ChatsPage.searchChat(name)
    }

    fun checkChatIsListed(name: String) {
        ChatsPage.assertChatCardDisplayed(name)
    }
}
