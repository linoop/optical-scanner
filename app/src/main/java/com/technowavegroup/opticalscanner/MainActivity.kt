package com.technowavegroup.opticalscanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.technowavegroup.networklib.NetworkUtil
import com.technowavegroup.printerlib.BTUtil

class MainActivity : AppCompatActivity() {

    private lateinit var buttonCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCheck = findViewById(R.id.buttonCheck)

        buttonCheck.setOnClickListener {
            checkBT()
        }
    }

    private fun checkBT(){
        val btUtil=BTUtil(this)
        btUtil.findBT()
    }

    private fun checkInternet(){
        val networkUtil = NetworkUtil(this)
        if(networkUtil.hasInternetConnection()){
            Toast.makeText(this,"Internet available",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Internet not available",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == BTUtil.BT_ENABLE_REQUEST_CODE) {
            Toast.makeText(this, "BT enabled", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "BT not enabled", Toast.LENGTH_LONG).show()
        }
    }
}