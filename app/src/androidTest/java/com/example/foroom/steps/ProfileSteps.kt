package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

object ProfileSteps {
    private const val CHANGE_LANGUAGE_GEORGIAN = "ენის შეცვლა"
    private const val SIGN_OUT_GEORGIAN = "გამოსვლა"
    private const val CHANGE_LANGUAGE_ENGLISH = "Change Language"
    private const val SIGN_OUT_ENGLISH = "Sign Out"

    fun openProfile() {
        ProfilePage.open()
        ProfilePage.assertDisplayed()
    }

    fun changePassword(newPassword: String) {
        ProfilePage.tapChangePassword()
        ChangePasswordPage.assertDisplayed()
        ChangePasswordPage.enterNewPassword(newPassword)
        ChangePasswordPage.enterRepeatPassword(newPassword)
        ChangePasswordPage.tapConfirm()
    }

    fun changeLanguageToGeorgian() {
        ProfilePage.tapChangeLanguage()
        ChangeLanguagePage.assertDisplayed()
        ChangeLanguagePage.selectGeorgian()
    }

    fun changeLanguageToEnglish() {
        ProfilePage.tapChangeLanguage()
        ChangeLanguagePage.assertDisplayed()
        ChangeLanguagePage.selectEnglish()
    }

    fun checkProfileIsInGeorgian() {
        ProfilePage.assertChangeLanguageTitle(CHANGE_LANGUAGE_GEORGIAN)
        ProfilePage.assertSignOutTitle(SIGN_OUT_GEORGIAN)
    }

    fun checkProfileIsInEnglish() {
        ProfilePage.assertChangeLanguageTitle(CHANGE_LANGUAGE_ENGLISH)
        ProfilePage.assertSignOutTitle(SIGN_OUT_ENGLISH)
    }
}
