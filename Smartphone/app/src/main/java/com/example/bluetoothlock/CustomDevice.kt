package com.example.bluetoothlock

import android.bluetooth.BluetoothDevice

class CustomDevice(val dev :BluetoothDevice) {
    override fun toString(): String {
        return dev.name
    }
}