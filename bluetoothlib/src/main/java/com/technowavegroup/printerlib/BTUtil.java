package com.technowavegroup.printerlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.ParcelUuid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BTUtil {
    public static final int BT_ENABLE_REQUEST_CODE = 100;
    public static final String PRINTER_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    @SuppressLint("StaticFieldLeak")
    private static BTUtil btUtilInstance;
    private final Activity activity;
    private final List<BluetoothDevice> paredDevices = new ArrayList<>();
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BluetoothDevice bluetoothDevice = null;
    private final String defaultPrinterMac;

    private final PrintStatusListener printStatusListener;

    public static synchronized BTUtil getInstance(Activity activity, PrintStatusListener printStatusListener) {
        if (btUtilInstance == null) {
            btUtilInstance = new BTUtil(activity, printStatusListener);
        }
        return btUtilInstance;
    }

    public BTUtil(Activity activity, PrintStatusListener printStatusListener) {
        this.activity = activity;
        this.printStatusListener = printStatusListener;
        defaultPrinterMac = SharedPrefManager.getInstance(activity).getPrinterMacAddress();
        findBTPrinter();
    }

    private void findBTPrinter() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                printStatusListener.onDeviceError("Bluetooth adapter not found!");
            }
            assert bluetoothAdapter != null;
            if (!bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(intent, BT_ENABLE_REQUEST_CODE);
            }
            Set<BluetoothDevice> availParedDevices = bluetoothAdapter.getBondedDevices();
            if (availParedDevices.size() > 0) {
                for (BluetoothDevice device : availParedDevices) {
                    ParcelUuid[] uuids = device.getUuids();
                    for (ParcelUuid uuid : uuids) {
                        if (uuid.getUuid().toString().equals(PRINTER_UUID)) {
                            paredDevices.add(device);
                            break;
                        }
                    }
                    if (defaultPrinterMac.equals(device.getAddress())) {
                        bluetoothDevice = device;
                    }
                }
            } else {
                printStatusListener.onDeviceError("No bluetooth device available!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseBTDeviceDialog() {
        BTDeviceListDialog btDeviceListDialog = new BTDeviceListDialog(activity, paredDevices, device -> {
            bluetoothDevice = device;
            connectBTDevice();
        });
        btDeviceListDialog.show();
    }

    private boolean isConnected = false;
    private Handler handler;

    public void connectBTDevice() {
        /*if (defaultPrinterMac.equals("")) {
            printStatusListener.onDeviceConnected(false, "Failed to get default printer!", bluetoothDevice);
            return;
        }*/
        handler = new Handler();
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.showProgress("Please wait...", "Connecting to printer...");
        progressDialog.show();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                if (bluetoothDevice != null) {
                    UUID uuid = UUID.fromString(PRINTER_UUID);
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                    bluetoothSocket.connect();
                    outputStream = bluetoothSocket.getOutputStream();
                    inputStream = bluetoothSocket.getInputStream();
                    isConnected = true;
                } else {
                    isConnected = false;
                }
                progressDialog.dismissProgress();
            } catch (Exception e) {
                e.printStackTrace();
                isConnected = false;
                progressDialog.dismissProgress();
            } finally {
                handler.post(() -> {
                    if (isConnected) {
                        printStatusListener.onDeviceConnected(true, "Printer connected successfully", bluetoothDevice);
                        SharedPrefManager.getInstance(activity).savePrinterMacAddress(bluetoothDevice.getAddress());
                    } else {
                        printStatusListener.onDeviceConnected(false, "Failed to connect printer!", bluetoothDevice);
                    }
                });
            }
        }).start();
    }

    private boolean printSuccess;

    public void print(String text) {
        handler = new Handler();
        if (bluetoothDevice != null && isConnected) {
            new Thread(() -> {
                printSuccess = false;
                try {
                    outputStream.write(text.getBytes());
                    printSuccess = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    printSuccess = false;
                } finally {
                    handler.post(() -> {
                        if (printSuccess)
                            printStatusListener.onPrintComplete(true, "Print completed");
                        else
                            printStatusListener.onPrintComplete(false, "Printer write failure!!!");
                    });
                }
            }).start();
        } else {
            printStatusListener.onPrintComplete(false, "Failed to connect printer!");
        }
    }

    public void disconnectBTDevice() {
        handler = new Handler();
        if (bluetoothDevice != null && isConnected) {
            new Thread(() -> {
                try {
                    outputStream.close();
                    inputStream.close();
                    bluetoothSocket.close();
                    isConnected = false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (isConnected) {
                                printStatusListener.onDeviceDisconnected(false, "Failed to disconnect!!");
                            } else {
                                printStatusListener.onDeviceDisconnected(true, "Device disconnected successfully");
                            }
                        }
                    });
                }
            }).start();
        } else {
            printStatusListener.onDeviceDisconnected(false, "Device not found");
        }
    }
}
