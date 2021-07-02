package com.technowavegroup.printerlib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BTUtil {
    public static final int BT_ENABLE_REQUEST_CODE = 100;
    public static final String PRINTER_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    private final Activity activity;
    private final List<BluetoothDevice> paredDevices = new ArrayList<>();
    private BluetoothAdapter bluetoothAdapter;

    public BTUtil(Activity activity) {
        this.activity = activity;
    }

    public void findBT() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                Toast.makeText(activity, "Bluetooth adapter not found!", Toast.LENGTH_LONG).show();
            }
            if (!bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, BT_ENABLE_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<BluetoothDevice> availParedDevices = bluetoothAdapter.getBondedDevices();

        if (availParedDevices.size() > 0) {
           /* Iterator<BluetoothDevice> iterator = paredDevices.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().getName().isEmpty()) {

                }
            }*/
            for (BluetoothDevice device : availParedDevices) {
                ParcelUuid[] uuids = device.getUuids();
                for (ParcelUuid uuid : uuids) {
                    if (uuid.getUuid().toString().equals(PRINTER_UUID)) {
                        paredDevices.add(device);
                        Log.d("printerUUID" + " " + device.getName(), uuid.getUuid().toString());
                        break;
                    }
                }
            }
            chooseBTDeviceDialog();
        } else {
            Toast.makeText(activity, "No bluetooth device available!", Toast.LENGTH_LONG).show();
        }
    }

    private void chooseBTDeviceDialog() {
        BTDeviceListDialog btDeviceListDialog = new BTDeviceListDialog(activity, paredDevices, device -> {
            Toast.makeText(activity, device.getName(), Toast.LENGTH_LONG).show();
        });
        btDeviceListDialog.show();
    }
}
