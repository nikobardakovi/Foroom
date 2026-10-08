package com.example.foroom.constants

data class TestUser(val userName: String, val password: String)

object TestUsers {
    val USER_A = TestUser(userName = "academy_user_a", password = "UserA123!")
    val USER_B = TestUser(userName = "academy_user_b", password = "UserB123!")

    const val AVATAR_ID = 1
}
