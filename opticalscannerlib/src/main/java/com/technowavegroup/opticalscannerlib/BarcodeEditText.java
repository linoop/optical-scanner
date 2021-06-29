package com.technowavegroup.opticalscannerlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class BarcodeEditText extends EditText {
    public BarcodeEditText(Context context) {
        super(context);
        init();
    }

    public BarcodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarcodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BarcodeEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    public void setOnBarcodeListener(BarcodeListener barcodeListener) {
        super.setSingleLine(true);
        super.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        super.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
            }
            return false;
        });

        super.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
                return false;
            }
            return false;
        });

    }

    public void setOnBarcodeListener(BarcodeListener barcodeListener, String label, int action) {
        super.setSingleLine(true);
        super.setImeActionLabel(label, action);
        super.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
            }
            return false;
        });

        super.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == action) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
                return false;
            }
            return false;
        });
    }

    public void setOnBarcodeListener(BarcodeListener barcodeListener, int action) {
        super.setSingleLine(true);
        super.setImeOptions(action);
        super.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
            }
            return false;
        });

        super.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == action) {
                barcodeListener.onBarcode(v.getId(), super.getText().toString());
                return false;
            }
            return false;
        });
    }


    public interface BarcodeListener {
        void onBarcode(int viewId, String barcode);
    }
}
