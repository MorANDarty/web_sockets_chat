package com.shgbievi.websocketchat

import android.util.Log
import com.google.gson.Gson
import okhttp3.*


class SocketManager(private  val view: ChatView) {

    private lateinit var socket: WebSocket
    private val client = OkHttpClient().newBuilder().build()

    fun init(){
        val request: Request = Request.Builder().url("wss://backend-chat.cloud.technokratos.com/chat").build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val json = "{ \"device_id\":\"111111\" }"
                webSocket.send(json)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                if (text == "{\"status\": \"ok\"}"){
                    view.onGetMsg(text)
                }else if (text.contains("items")){
                    val messages = Gson().fromJson(text, GetMessagesResponse::class.java)
                    view.onGetMessages(messages)
                }
                Log.d("Socket", "onMessage $text")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("Socket", "onClosing $reason")
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("Socket", "error $t response $response")
                socket = client.newWebSocket(request, this)
                view.onError(t.localizedMessage )
            }

        }
        socket = client.newWebSocket(request, listener)
    }

    fun sendMsg(msg:String){
        socket.send("{ \"message\":\"$msg\" }")
    }

    fun getMessages(count:Int){
        val json = "{ \"history\": { \"limit\": $count} }"
        socket.send(json)
    }
}