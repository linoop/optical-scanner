package com.technowavegroup.opticalscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import com.technowavegroup.opticalscannerlib.BarcodeEditText;
import com.technowavegroup.printerlib.PrintStatusListener;

public class SecondActivity extends AppCompatActivity {

    private BarcodeEditText barcodeItem;
    private BarcodeEditText barcodePrice;

    PrintStatusListener printStatusListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        barcodeItem = findViewById(R.id.barcodeItem);
        barcodePrice = findViewById(R.id.barcodePrice);

        barcodeItem.setOnBarcodeListener(new BarcodeEditText.BarcodeListener() {
            @Override
            public void onBarcode(int viewId, String barcode) {

            }
        }, "Ook", EditorInfo.IME_ACTION_DONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}