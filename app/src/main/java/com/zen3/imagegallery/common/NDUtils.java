package com.zen3.imagegallery.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Locale;


public class NDUtils {

    private static final String TAG = "NDUtils";
    // Context
    Context _context;

    /**
     * Utility method to convert stream into Sting
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        Log.e(TAG, "convertInputStreamToString:  " + inputStream);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        Log.i(TAG, "convertInputStreamToString:  bufferedReader " + bufferedReader);
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        Log.i(TAG, "convertInputStreamToString: result " + result);
        return result;

    }

    public static boolean checkNetworkAvailability(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        // check for availability of network
        if ((wifiNetwork != null && wifiNetwork.isConnected())
                || (mobileNetwork != null && mobileNetwork.isConnected())) {
            Log.e(TAG, "NDUtil -checkNetworkAvailability--true");
            return true;
        } else {
            Log.e(TAG, "NDUtil -checkNetworkAvailability--false");
            return false;
        }
    }


    /**
     * Method validates mobile number
     *
     * @param inputMobile
     * @return
     */
    private boolean isMobileValid(String inputMobile) {
        return !inputMobile.trim().isEmpty()
                && inputMobile.length() == 10
                && (inputMobile.startsWith("9")
                || inputMobile.startsWith("8")
                || inputMobile.startsWith("7"));
    }



    public static void hideSoftInput(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService((Context.INPUT_METHOD_SERVICE));
        // if no view has focus
        View view = ((Activity) ctx).getCurrentFocus();
        if (view == null)
            return;

        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
