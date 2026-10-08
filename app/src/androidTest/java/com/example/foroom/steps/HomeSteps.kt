package com.example.foroom.steps

import androidx.test.espresso.action.ViewActions.click
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.constants.Timeouts
import com.example.foroom.pages.HomePage

class HomeSteps {
    fun checkHomeScreenIsDisplayed(): HomeSteps {
        waitUntilDisplayed(HomePage.navBar, Timeouts.SCREEN_TIMEOUT_MS)
        waitUntilDisplayed(HomePage.homeContainer)
        return this
    }

    fun openChatsTab(): ChatListSteps {
        waitAndPerform(HomePage.chatsTab, click())
        return ChatListSteps()
    }

    fun openProfileTab(): ProfileSteps {
        waitAndPerform(HomePage.profileTab, click())
        return ProfileSteps()
    }
}
