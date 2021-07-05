package com.technowavegroup.printerlib;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BTDeviceListDialog extends AppCompatDialog {
    private final Context context;
    List<BluetoothDevice> bluetoothDevices;
    private BTSelectDeviceListener btSelectDeviceListener;

    public BTDeviceListDialog(Context context, List<BluetoothDevice> bluetoothDevices, BTSelectDeviceListener btSelectDeviceListener) {
        super(context);
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
        this.btSelectDeviceListener = btSelectDeviceListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_device_list_dialog);
        //setTitle("Choose device");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerViewDeviceList = findViewById(R.id.recyclerViewDeviceList);

        assert recyclerViewDeviceList != null;
        recyclerViewDeviceList.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDeviceList.addItemDecoration(new RecyclerViewDecoration((int) context.getResources().getDimension(R.dimen.spacing_small)));
        recyclerViewDeviceList.setAdapter(new BTDeviceListAdapter(this, bluetoothDevices, btSelectDeviceListener));


        findViewById(R.id.buttonCancel).setOnClickListener(v -> dismiss());
    }
}
