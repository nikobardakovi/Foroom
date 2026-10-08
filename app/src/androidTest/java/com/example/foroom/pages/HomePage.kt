package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R

object HomePage {
    val homeContainer = withId(R.id.homeContainer)
    val navBar = withId(R.id.navBar)
    val chatsTab = withId(R.id.homeNavigationChats)
    val profileTab = withId(R.id.homeNavigationProfile)
}
