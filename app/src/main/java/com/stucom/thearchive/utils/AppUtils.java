package com.stucom.thearchive.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_user.User;
import com.tapadoo.alerter.Alerter;

import static android.content.Context.MODE_PRIVATE;


public class AppUtils {
    Context mContext;
    private String user = "";

    public AppUtils(Context context) {
        this.mContext = context;
    }


    public void showAlert(Activity destination) {
        Alerter.create(destination)
                .setTitle("Estanteria")
                .setText("Libro agregado correctamente")
                .setIcon(R.drawable.ic_book_black_24dp)
                .setBackgroundColorRes(R.color.colorAlert)
                .setDuration(2500)
                .enableSwipeToDismiss()
                .show();
    }

    public String getUsername() {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public User getUserConnected() {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void deleteUserData() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getPackageName(), MODE_PRIVATE).edit();
        editor.clear().apply();
    }

}