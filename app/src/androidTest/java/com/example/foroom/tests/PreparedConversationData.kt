package com.example.foroom.tests

import com.example.foroom.constants.ChatTitles
import com.example.foroom.constants.TestUser
import com.example.foroom.constants.TestUsers
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.usecase.CreateChatUseCase
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.domain.usecase.GetEmojisUseCase
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.foroom.domain.usecase.RegisterUserUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.shared.util.runtime.user_token.UserTokenRuntimeHolder
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext

/**
 * Prepares the local data every scenario needs on the current device: User A and User B, and
 * the johnWeek, full-name and shared chats created by User A. Existing accounts and chats are
 * reused, so the preparation can run before every test. Afterwards the app is signed out, so
 * each scenario starts on the login screen regardless of earlier runs.
 */
class PreparedConversationData : TestRule {
    private val koin get() = GlobalContext.get()

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                runBlocking {
                    ensureUserExists(TestUsers.USER_B)
                    ensureUserExists(TestUsers.USER_A)
                    signInInBackground(TestUsers.USER_A)
                    ChatTitles.ALL.forEach { title -> ensureChatExists(title) }
                    koin.get<ForoomUserDataStore>().clearUserData()
                }
                base.evaluate()
            }
        }

    private suspend fun ensureUserExists(user: TestUser) {
        try {
            koin.get<RegisterUserUseCase>()(
                RegistrationRequest(user.userName, user.password, TestUsers.AVATAR_ID)
            )
        } catch (e: Exception) {
            // The account already exists on this device; signing in below verifies it.
        }
        signInInBackground(user)
    }

    private suspend fun signInInBackground(user: TestUser) {
        val response = koin.get<LogInUserUseCase>()(LogInRequest(user.userName, user.password))
        koin.get<UserTokenRuntimeHolder>().setUserToken(response.token)
    }

    private suspend fun ensureChatExists(title: String) {
        val chats = koin.get<GetChatsUseCase>()(page = 0, limit = SEARCH_LIMIT, name = title)
        if (chats.chats.none { chat -> chat.name == title }) {
            val emojiId = koin.get<GetEmojisUseCase>()().first().id
            koin.get<CreateChatUseCase>()(title, emojiId)
        }
    }

    private companion object {
        const val SEARCH_LIMIT = 100
    }
}
