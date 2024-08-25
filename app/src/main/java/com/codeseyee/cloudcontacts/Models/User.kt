package com.codeseyee.cloudcontacts.Models

data class User(
    val email: String = "",
    val phone: String = "",
    val name: String = "",
    val password: String = "",
    val profileImageUrl: String = "",
    val accountType: String = ""
)