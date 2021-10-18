package com.example.bluetoothlock

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.textclassifier.TextClassificationSessionFactory
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ServerActivity : AppCompatActivity() {
    lateinit var remote:String
    private lateinit var acceptThread: AcceptThread
    private lateinit var remoteLabel: TextView
    private lateinit var chatBox: RecyclerView
    private lateinit var msgBox:EditText
    private lateinit var msgList: ArrayList<Message>
    private lateinit var msgAdapter: MessageAdapter
    private lateinit var sendButton: Button
    

    companion object{
        private var working: Boolean=true
        lateinit var adapter: BluetoothAdapter
        private const val myServiceName = "MyBtService"
        private val serviceUUID = UUID.fromString("7f49f6fa-12e5-11ec-82a8-0242ac130003")
        var socket: BluetoothSocket? =null
        var battery: String = "Unknown"
        var messageField: EditText? =null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        adapter = CommonMethods().getAdapter(this)
        batteryText=findViewById(R.id.RemoteBattery)
        findViewById<TextView>(R.id.DeviceLabel).text=adapter.name
        remoteLabel=findViewById(R.id.ClientDevice)
        acceptThread = AcceptThread()
        acceptThread.start()

        chatBox=findViewById(R.id.ChatBox)
        msgBox=findViewById(R.id.Message)
        sendButton = findViewById(R.id.SendButtom)
        msgList= ArrayList()
        msgList.add(Message("hey",true))
        msgList.add(Message("hey",false))
        msgAdapter= MessageAdapter(this,msgList)
        chatBox.layoutManager=LinearLayoutManager(this)
        chatBox.adapter=msgAdapter
        sendButton.setOnClickListener {
            sendText()
        }
    }

    private fun sendText() {
        val text = msgBox!!.text.toString()
        msgList.add(Message(text,true)!!)
        msgAdapter.notifyDataSetChanged()
        msgBox!!.setText("")
        if (socket!=null){
            socket!!.outputStream.write(text.toByteArray())
        }
    }
    private fun readText(data:String) {
        msgList.add(Message(data,false)!!)
        msgAdapter.notifyDataSetChanged()
    }

        var batteryText:TextView?=null
    private fun work() {
        val handler = Handler(Looper.getMainLooper())
        val value = ByteArray(1024)
        try {
            val read = socket!!.inputStream.read(value)
            if (read == -1) makeNotification()
            else
            {
                val data = String(value)
                if (data.contains("::")) {
                    if (data.substring(2).contains("::")) handler.post{readText(data)}
                    else{
                        if (data.contains("::exit")) disconnect()
                        else {
                            handler.post {
                                val level = data.substring(2).toFloat()
                                val temp = "$level %"
                                if (temp != battery) {
                                    battery = temp
                                    batteryText!!.text = battery
                                }
                            }
                        }
                    }
                }
                else handler.post { readText(data) }
            }
        }catch (e:IOException){
            handler.post {
                batteryText!!.text="Unknow"
                remoteLabel!!.text="UnKnow"
            }
            disconnect()
        }
        Thread.sleep(200)
        println(battery)
    }

    private fun disconnect() {
        try {
            socket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
        socket=null
        makeNotification()
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(this,"DESCONECTADO",Toast.LENGTH_SHORT)
        }
    }

    private fun makeNotification() {
        acceptThread.shouldLoop=true
        socket=null
        CommonMethods().sendNofication(remote,this)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        disconnect()
        working=false
        this.finish()
    }

    private inner class AcceptThread : Thread() {

        private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            adapter.listenUsingInsecureRfcommWithServiceRecord(myServiceName, serviceUUID)
        }
        var shouldLoop = true

        override fun run() {
            while (working){
                while (shouldLoop) {
                    socket = try {
                        mmServerSocket?.accept()
                    } catch (e: IOException) {
                        null
                    }
                    socket?.also {
                        shouldLoop = false
                        remote= socket!!.remoteDevice.name
                        val handler = Handler(Looper.getMainLooper())
                        handler.post {
                            batteryText!!.text=battery
                            remoteLabel!!.text=remote
                        }
                        CommunicationThread().start()
                    }
                }
            }
        }


    }

    private inner class CommunicationThread : Thread() {
        override fun run() {
            super.run()
                while(working){
                    if (socket!=null) work()
                }
        }

    }


}
