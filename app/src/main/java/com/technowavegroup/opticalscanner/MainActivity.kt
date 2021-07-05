package com.technowavegroup.opticalscanner

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.technowavegroup.networklib.NetworkUtil
import com.technowavegroup.printerlib.BTUtil
import com.technowavegroup.printerlib.PrintStatusListener

class MainActivity : AppCompatActivity(), PrintStatusListener {

    private lateinit var btUtil: BTUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btUtil = BTUtil(this, this)

        findViewById<Button>(R.id.buttonConnect).setOnClickListener {
            btUtil.connectBTDevice()
        }

        findViewById<Button>(R.id.buttonChoose).setOnClickListener {
            btUtil.chooseBTDeviceDialog()
        }

        findViewById<Button>(R.id.buttonPrint).setOnClickListener {
            val text = "^XA\n^FO50,160^ADN,36,20^FD" + "linooplllllllllll" + "\n^FS\n^XZ"
            btUtil.print(text)
        }

        findViewById<Button>(R.id.buttonDisconnect).setOnClickListener {
            btUtil.disconnectBTDevice()
        }
    }

    private fun checkInternet() {
        val networkUtil = NetworkUtil(this)
        if (networkUtil.hasInternetConnection()) {
            Toast.makeText(this, "Internet available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == BTUtil.BT_ENABLE_REQUEST_CODE) {
            Toast.makeText(this, "BT enabled", Toast.LENGTH_LONG).show()
            btUtil = BTUtil(this, this)
        } else {
            Toast.makeText(this, "BT not enabled", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onDeviceConnected(
        isConnected: Boolean,
        statusMessage: String?,
        bluetoothDevice: BluetoothDevice?
    ) {
        Toast.makeText(this, statusMessage, Toast.LENGTH_LONG).show()
    }

    override fun onPrintComplete(isPrinted: Boolean, printStatus: String?) {
        Toast.makeText(this, printStatus, Toast.LENGTH_LONG).show()
    }

    override fun onDeviceDisconnected(isDisconnected: Boolean, statusMessage: String?) {
        Toast.makeText(this, statusMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDeviceError(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}