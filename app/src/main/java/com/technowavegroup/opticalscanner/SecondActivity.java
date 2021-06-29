package com.technowavegroup.opticalscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import com.technowavegroup.opticalscannerlib.BarcodeEditText;

public class SecondActivity extends AppCompatActivity {

    private BarcodeEditText barcodeItem;
    private BarcodeEditText barcodePrice;

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
}