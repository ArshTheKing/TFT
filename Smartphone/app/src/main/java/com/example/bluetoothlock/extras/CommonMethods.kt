package com.example.bluetoothlock.extras

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import com.example.bluetoothlock.R

class CommonMethods {


    private val rqEnable = 1


    fun loadBattery(activity: Activity): String {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            activity.registerReceiver(null, ifilter)
        }
        val batteryPct: Int? = batteryStatus?.let { intent ->
            intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        }
        return if(batteryPct!!<100)
            batteryPct.toString()
        else
            "100"
    }

    fun getAdapter(activity: Activity): BluetoothAdapter{
        val btAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val manager: BluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            manager.adapter
        } else {
            @Suppress("DEPRECATION")
            BluetoothAdapter.getDefaultAdapter()
        }
        if (btAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent,rqEnable)
        }
        return btAdapter
    }
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: Notification.Builder
    private var channelId= "com.example.bluetoothlock"
    private var description= "Device disconnected: "

    fun sendNofication(name:String, activity: Activity){
        val intent=Intent(activity,activity.javaClass)
        val pendingIntent= getActivity(activity,0,intent, FLAG_UPDATE_CURRENT)
        notificationManager= activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel= NotificationChannel(channelId,description+name,
                                        NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder=Notification.Builder(activity,channelId)
            .setContentTitle("BluetoothLock")
            .setContentText("Se a desconectado el dispositivo vinculado: $name")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
        }else{
            notificationBuilder=Notification.Builder(activity)
                .setContentTitle("BluetoothLock")
                .setContentText("Se ha desconectado el dispositivo vinculado: $name")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1234,notificationBuilder.notification)
    }
}
