package com.example.bluetoothlock

class Message{

    var content:String?=null
    var sent:Boolean?=null //True for sent, false for received

    constructor(msg:String,send:Boolean){
        content=msg
        sent=send
    }
}