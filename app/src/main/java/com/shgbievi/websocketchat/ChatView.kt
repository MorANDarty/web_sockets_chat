package com.shgbievi.websocketchat


interface ChatView {

    fun onGetMessages(messages:GetMessagesResponse)

    fun onError(error:String)

    fun onGetMsg(msg:String)
}