package com.zen3.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zen3.imagegallery.controller.User;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";

    private static final int TAKE_PICTURE = 2 << 2;

    private ImageView profileImage;
    private ImageButton cameraBtn;
    private TextView nameTextView;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                SharedPreferences preferences = getSharedPreferences(Application.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                Editor editor = preferences.edit();
                editor.remove(Application.PREF_ACCES_TOKEN);
                editor.remove(Application.PREF_TOKEN_SECRET);
                editor.apply();

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void btnGridVIew(View view) {
        startActivity(new Intent(MainActivity.this,GridViewActivity.class));
    }

    public void btnListView(View view) {
    }
}
