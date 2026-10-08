package com.example.foroom.steps

import com.example.foroom.Helper.clickOnVisiblePart
import com.example.foroom.Helper.waitAndPerform
import com.example.foroom.Helper.waitUntilDisplayed
import com.example.foroom.pages.ProfilePage

class ProfileSteps {
    fun checkProfileIsDisplayed(): ProfileSteps {
        waitUntilDisplayed(ProfilePage.changeLanguageItem)
        waitUntilDisplayed(ProfilePage.signOutItem)
        return this
    }

    fun tapSignOut(): LoginSteps {
        waitAndPerform(ProfilePage.signOutItem, clickOnVisiblePart())
        return LoginSteps()
    }
}
