package com.shgbievi.websocketchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChatView {

    private var adapter: MessagesAdapter? = null
    private val socketManager = SocketManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RestService().getApi().login("MorANDarty", "111111")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.status == "ok"){
                    startChat()
                    Toast.makeText(this, "Success auth",Toast.LENGTH_LONG).show()
                }
            },{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
            .apply {  }
    }

    private fun startChat() {
        initRecycler()
        socketManager.init()
    }

    private fun initRecycler() {
        adapter = MessagesAdapter(arrayListOf())
        adapter?.setHasStableIds(true)
        rv_msg.adapter = adapter
    }

    override fun onGetMessages(messages: GetMessagesResponse) {
        adapter?.updateData(messages.items)
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onGetMsg(msg: String) {
        adapter?.addMessage(Message(1,msg, "MorANDarty"))
    }
}
