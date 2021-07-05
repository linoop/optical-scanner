package com.technowavegroup.printerlib;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PRINTER_PREF = "printer_pref";
    private static SharedPrefManager sharedPrefManagerInstance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (sharedPrefManagerInstance == null) {
            sharedPrefManagerInstance = new SharedPrefManager(context);
        }
        return sharedPrefManagerInstance;
    }

    public boolean savePrinterMacAddress(String macAddress) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRINTER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("macAddress", macAddress);
        return editor.commit();
    }

    public String getPrinterMacAddress() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRINTER_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString("macAddress", "");
    }

    public boolean clearPrinterMacAddress(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PRINTER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }
}
