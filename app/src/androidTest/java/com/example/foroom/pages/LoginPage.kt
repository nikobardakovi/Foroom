package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.editTextOfInput

object LoginPage {
    val userNameInput = withId(R.id.userNameInput)
    val passwordInput = withId(R.id.passwordInput)
    val userNameEditText = editTextOfInput(R.id.userNameInput)
    val passwordEditText = editTextOfInput(R.id.passwordInput)
    val logInButton = withId(R.id.logInButton)
}
