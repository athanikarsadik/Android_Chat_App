package com.example.sdk_chat_app

class Message {
    var massage: String? = null
    var senderId:String? = null

    constructor(){}

    constructor(massage: String?, senderId: String?) {
        this.massage = massage
        this.senderId = senderId
    }
}