package com.shgbievi.websocketchat


data class Message(
    val id: Long,
    val message: String = "",
    val user: String = ""
)