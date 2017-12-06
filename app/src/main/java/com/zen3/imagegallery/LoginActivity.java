package com.zen3.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.PxApi;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.tasks.UserDetailTask;
import com.fivehundredpx.api.tasks.XAuth500pxTask;
import com.zen3.imagegallery.controller.User;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements XAuth500pxTask.Delegate, UserDetailTask.Delegate {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText editTextEmail, editTextPassword;
    private Button loginButton;
    private RelativeLayout loadingView, relativeLayout1;
    private User user;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.login_email);
        editTextPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_btn);
        loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);


        SharedPreferences preferences = getSharedPreferences(Application.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        final String accesToken = preferences.getString(Application.PREF_ACCES_TOKEN, null);
        final String tokenSecret = preferences
                .getString(Application.PREF_TOKEN_SECRET, null);

        if (null != accesToken && null != tokenSecret) {
            onSuccess(new AccessToken(accesToken, tokenSecret));
        }


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showSpinner();
                final XAuth500pxTask loginTask = new XAuth500pxTask(
                        LoginActivity.this);
                loginTask.execute(getString(R.string.consumer_key),
                        getString(R.string.consumer_secret), editTextEmail
                                .getText().toString(), editTextPassword.getText()
                                .toString());
            }
        });
    }

    @Override
    public void onSuccess(AccessToken result) {
        Log.d(TAG, "success " + result);
        Log.d(TAG, "getTocken  " + result.getToken());
        Log.d(TAG, "getTocken secret  " + result.getTokenSecret());
        // showSpinner();
        hideSpinner();
        //user.accessToken = result;
        accessToken = result;

        SharedPreferences preferences = getSharedPreferences(Application.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Application.PREF_ACCES_TOKEN, result.getToken());
        editor.putString(Application.PREF_TOKEN_SECRET, result.getTokenSecret());
        editor.apply();


        final PxApi api = new PxApi(accessToken,
                getString(R.string.consumer_key),
                getString(R.string.consumer_secret));

        //new UserDetailTask(LoginActivity.this).execute(api);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void showSpinner() {
        loginButton.setEnabled(false);
        loadingView.setVisibility(View.VISIBLE);
        relativeLayout1.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    private void hideSpinner() {
        loginButton.setEnabled(true);
        loadingView.setVisibility(View.GONE);
        relativeLayout1.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    @Override
    public void onSuccess(JSONObject user) {
        //Log.w(TAG, user.toString());
        /*try {
            this.user.userpic_url = user.getString("userpic_url");
            this.user.fullname = user.getString("fullname");
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }*/

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        //finish();
    }

    @Override
    public void onFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideSpinner();
                Toast.makeText(LoginActivity.this,
                        "Login Failed, please try again.", Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    @Override
    public void onFail(FiveHundredException e) {
        onFail();
    }


}
