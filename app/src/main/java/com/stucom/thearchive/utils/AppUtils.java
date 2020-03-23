package com.stucom.thearchive.utils;

import android.app.Activity;
import android.content.Context;
import com.stucom.thearchive.R;
import com.tapadoo.alerter.Alerter;


public class AppUtils {
    Context mContext;

    public AppUtils(Context context) {
        this.mContext = context;
    }


    public void showAlert(Activity destination) {
        Alerter.create(destination)
                .setTitle("Estanteria")
                .setText("Libro agregado correctamente")
                .setIcon(R.drawable.ic_book_black_24dp)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setDuration(2500)
                .enableSwipeToDismiss()
                .show();
    }
}