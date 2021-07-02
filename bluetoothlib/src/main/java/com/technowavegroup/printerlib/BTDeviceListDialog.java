package com.technowavegroup.printerlib;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BTDeviceListDialog extends AppCompatDialog {
    private BTDeviceListAdapter deviceListAdapter;
    private final Context context;
    List<BluetoothDevice> bluetoothDevices;
    private BTSelectDeviceListener btSelectDeviceListener;

    public BTDeviceListDialog(Context context, List<BluetoothDevice> bluetoothDevices, BTSelectDeviceListener btSelectDeviceListener) {
        super(context);
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
        this.btSelectDeviceListener = btSelectDeviceListener;
    }

    public BTDeviceListDialog(Context context, List<BluetoothDevice> bluetoothDevices, int theme) {
        super(context, theme);
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
    }

    protected BTDeviceListDialog(Context context, List<BluetoothDevice> bluetoothDevices, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.bluetoothDevices = bluetoothDevices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_device_list_dialog);
        setTitle("Choose device");
        RecyclerView recyclerViewDeviceList = findViewById(R.id.recyclerViewDeviceList);

        assert recyclerViewDeviceList != null;
        recyclerViewDeviceList.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewDeviceList.setAdapter(new BTDeviceListAdapter(this, bluetoothDevices, btSelectDeviceListener));


        findViewById(R.id.cancel).setOnClickListener(v -> dismiss());
    }
}
