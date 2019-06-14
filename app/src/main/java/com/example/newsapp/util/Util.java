package com.example.newsapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Util {
    public static final String API_KEY="2a16b6542f65427e9154022dc04fbe48";
    private static ConnectivityManager connectivityManager;
    private static NetworkInfo networkInfo;
    private static boolean connected = false;
    private static ProgressDialog progressDialog = null;

    public static ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public static void setProgressDialog(ProgressDialog progressDialog) {
        Util.progressDialog = progressDialog;
    }
    public static void showProgDialog(Context context, String msg) {
        if (!((Activity) context).isFinishing()) {
            if (progressDialog == null)
                progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);

            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
                progressDialog.setMessage(msg);
                progressDialog.setCanceledOnTouchOutside(false);

            } else {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        }
    }

    public static void hideProgDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    public static boolean checkConnection(Context context){
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    public static String formattedDate(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy, MMM dd");
        String formattedDate = null;
        try {
            formattedDate = outputFormat.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
