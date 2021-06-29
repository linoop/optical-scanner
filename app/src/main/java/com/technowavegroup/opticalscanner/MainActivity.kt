package com.technowavegroup.opticalscanner

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.technowavegroup.opticalscannerlib.BarcodeEditText

class MainActivity : AppCompatActivity(), BarcodeEditText.BarcodeListener {

    private lateinit var barcodeItem: BarcodeEditText
    private lateinit var barcodePrice: BarcodeEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barcodeItem = findViewById(R.id.barcodeItem)
        barcodePrice = findViewById(R.id.barcodePrice)

        barcodeItem.setOnBarcodeListener(this, EditorInfo.IME_ACTION_DONE)
        barcodePrice.setOnBarcodeListener(this, "Ook", EditorInfo.IME_ACTION_GO)
    }


    private fun decodeItemBarcode(code: String) {
        Log.d("barcode", code)
        Toast.makeText(this, "Item $code", Toast.LENGTH_LONG).show()
    }

    private fun decodePriceBarcode(code: String) {
        Log.d("barcode", code)
        Toast.makeText(this, "Price $code", Toast.LENGTH_LONG).show()
    }

    override fun onBarcode(viewId: Int, barcode: String?) {
        when (viewId) {
            R.id.barcodeItem -> decodeItemBarcode(barcode!!)
            R.id.barcodePrice -> decodePriceBarcode(barcode!!)
        }
    }
}