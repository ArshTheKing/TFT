package com.example.bluetoothlock

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val rqEnable = 1
    private var btAdapter: BluetoothAdapter? =null
    companion object{
        var EXTRA_ADDRESS: String = "Device_address"
        var EXTRA_NAME:String = "Device_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btAdapter = CommonMethods().getAdapter(this)
        val loadBattery = CommonMethods().loadBattery(this)
        findViewById<TextView>(R.id.DevBTData).text=btAdapter!!.name
        findViewById<ImageButton>(R.id.refreshIcon).setOnClickListener { pairedDeviceList() }
        pairedDeviceList()
        findViewById<Button>(R.id.switchMode).setOnClickListener(){
            val intent= Intent(this,ServerActivity::class.java)
            startActivity(intent)

        }
    }

    private fun pairedDeviceList() {
        var dev = findViewById<ListView>(R.id.devices)
        val list = ArrayList<CustomDevice>()
        btAdapter!!.bondedDevices?.forEach{
                device -> list.add(CustomDevice(device))
        }
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,list)
        dev.adapter=adapter
        dev.onItemClickListener= AdapterView.OnItemClickListener{ _, _, position, _ ->
            val device:CustomDevice = list[position]
            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,device.dev.address)
            intent.putExtra(EXTRA_NAME,device.dev.name)
            startActivity(intent)
        }

    }
}