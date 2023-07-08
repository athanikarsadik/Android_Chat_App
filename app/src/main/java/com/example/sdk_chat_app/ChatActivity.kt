package com.example.sdk_chat_app

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var msgRV: RecyclerView
    private lateinit var msgBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var msgAdapter: MessageAdapter
    private lateinit var msgList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        supportActionBar?.title = name


        msgRV = findViewById(R.id.chatRV)
        msgBox = findViewById(R.id.msgBox)
        sendButton = findViewById(R.id.sendMsg)
        msgList = ArrayList()
        msgAdapter = MessageAdapter(this,msgList)

        msgRV.layoutManager = LinearLayoutManager(this)
        msgRV.adapter = msgAdapter
        //logic for adding data to RV
        mDbRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    msgList.clear()
                    for(postSnap in snapshot.children){
                        val msg = postSnap.getValue(Message::class.java)
                        msgList.add(msg!!)
                    }
                    msgAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener{
            val msg = msgBox.text.toString()
            val msgObject = Message(msg,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(msgObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("message").push()
                        .setValue(msgObject)
                }

            msgBox.setText("")
        }


    }
}