package com.technowavegroup.printerlib;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BTDeviceListAdapter extends RecyclerView.Adapter<BTDeviceListAdapter.PrinterListViewHolder> {

    private final BTDeviceListDialog context;
    private final List<BluetoothDevice> bluetoothDeviceList;
    private final BTSelectDeviceListener btSelectDeviceListener;

    public BTDeviceListAdapter(BTDeviceListDialog context, List<BluetoothDevice> bluetoothDeviceList, BTSelectDeviceListener btSelectDeviceListener) {
        this.context = context;
        this.bluetoothDeviceList = bluetoothDeviceList;
        this.btSelectDeviceListener = btSelectDeviceListener;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PrinterListViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.printer_item, parent, false);
        return new PrinterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull BTDeviceListAdapter.PrinterListViewHolder holder, int position) {
        BluetoothDevice device = bluetoothDeviceList.get(position);
        holder.textView.setText(device.getName());
        holder.itemView.setOnClickListener(v -> {
            btSelectDeviceListener.onBTDeviceSelected(device);
            context.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return bluetoothDeviceList.size();
    }

    protected static class PrinterListViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PrinterListViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.printerName);
        }
    }
}
