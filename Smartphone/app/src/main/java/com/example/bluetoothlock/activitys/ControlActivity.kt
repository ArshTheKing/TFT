package com.example.bluetoothlock.activitys

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothlock.R
import com.example.bluetoothlock.extras.CommonMethods
import com.example.bluetoothlock.extras.MessageAdapter
import com.example.bluetoothlock.objects.Message
import java.io.IOException
import java.util.*


class ControlActivity : AppCompatActivity() {


    private lateinit var readerThread: ReaderThread
    private lateinit var communicationThread: CommunicationThread
    private lateinit var chatBox: RecyclerView
    private lateinit var msgBox: EditText
    private lateinit var msgList: ArrayList<Message>
    private lateinit var msgAdapter: MessageAdapter
    private lateinit var sendButton: Button

    companion object{
        var socket: BluetoothSocket?=null
        var handler = CommonMethods()
        lateinit var adapter: BluetoothAdapter
        lateinit var address: String
        var working=true
        private lateinit var myself: ControlActivity
    }

    private lateinit var acceptThread: AcceptThread

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("nuevo")
        setContentView(R.layout.control_layout)
        myself =this
        adapter = CommonMethods().getAdapter(this)
        address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS)!!
        var devName = intent.getStringExtra(MainActivity.EXTRA_NAME)!!
        findViewById<TextView>(R.id.Device).text=devName
        setButton(false)
        acceptThread = AcceptThread()
        println(acceptThread.isAlive.toString()+"previo")
        acceptThread.start()
        println(acceptThread.isAlive.toString()+"post")
        communicationThread = CommunicationThread()
        readerThread=ReaderThread()
        chatBox=findViewById(R.id.ChatBox)
        msgBox=findViewById(R.id.Message)
        sendButton = findViewById(R.id.SendButtom)
        msgList= ArrayList()
        msgAdapter= MessageAdapter(this,msgList)
        chatBox.layoutManager= LinearLayoutManager(this)
        chatBox.adapter=msgAdapter
        sendButton.setOnClickListener {
            sendText()
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onBackPressed() {
        super.onBackPressed()
        disconect()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onDestroy() {
        super.onDestroy()
        disconect()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun setButton(value:Boolean) {
        var handler= Handler(Looper.getMainLooper())
        if (value){
            handler.post{
                var button=findViewById<Button>(R.id.Action)
                button.text="Desconectar"
                button.setOnClickListener{disconect()}
                findViewById<TextView>(R.id.Status).text="Conectado"
            }
        }else{
            handler.post{
                var button=findViewById<Button>(R.id.Action)
                button.text="Reconectar"
                button.setOnClickListener{acceptThread.shouldLoop=true}
            }
        }
    }

    private fun sendText() {
        if (socket !=null){
            val text = msgBox!!.text.toString()
            val msg= secureText(text)
            msgList.add(Message(text,true)!!)
            msgAdapter.notifyDataSetChanged()
            communicationThread.send=false
            socket!!.outputStream.write(text.toByteArray())
            msgBox.setText("")
        }
    }

    private fun secureText(text: String): String {
        if (!text.contains("::")) return text
        else {
            val subtext= text.substring(0,2)
            if (subtext.contains("::")) return subtext
            else return text
        }
    }

    private fun readText(data:ByteArray) {
        val handler = Handler(Looper.getMainLooper())
        msgList.add(Message(String(data),false)!!)
        handler.post{msgAdapter.notifyDataSetChanged()}
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    protected fun sendCommand(){
            try {
                socket?.outputStream?.write(("::"+ CommonMethods().loadBattery(myself)).toByteArray())
            } catch (e: Exception){
            }
            Thread.sleep(2000)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun readCommand() {
            var value= ByteArray(1024)
            try {
                val read = socket!!.inputStream.read(value)
                if(read>0)readText(value)
            } catch(e:Exception){
                socket=null
                acceptThread.shouldLoop=true
            }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun disconect(){
        if (socket!=null) {
            try {
                socket!!.outputStream.write("::exit".toByteArray())
                socket!!.close()
            } catch (e: IOException){

            }
        }
        working =false
        socket =null
        Handler(Looper.getMainLooper()).post{Toast.makeText(this,"disconected", Toast.LENGTH_LONG).show()}
        this.finish()
    }

    private inner class AcceptThread : Thread() {
        var shouldLoop = true
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        override fun run() {
            while (working){
                while (shouldLoop) {
                        val device: BluetoothDevice = adapter.getRemoteDevice(address)
                        val fromString = UUID.fromString("7f49f6fa-12e5-11ec-82a8-0242ac130003")
                        socket = device.createRfcommSocketToServiceRecord(fromString)
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                        if(socket !=null)
                            try{
                                socket!!.connect()
                            }catch (e:IOException){
                                socket!!.close()
                                println("ENTRA")
                                socket =null
                                continue
                            }
                        if (socket !=null&& socket!!.isConnected) {
                            println("Conectado y funcional")
                            shouldLoop=false
                            setButton(true)
                            if(!!communicationThread.isAlive) communicationThread.start()
                            sendCommand()
                            if (!!readerThread.isAlive) readerThread.start()
                        }
                }
            }
        }
    }

    private inner class CommunicationThread : Thread() {
        var send: Boolean=true
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        override fun run() {
            super.run()
            while(working){
                if (socket !=null)
                    if(send)sendCommand()
                    else send=true
            }
        }
    }
    private inner class ReaderThread : Thread() {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        override fun run() {
            super.run()
            while(working){
                if (socket !=null) readCommand()
            }
        }
    }

}