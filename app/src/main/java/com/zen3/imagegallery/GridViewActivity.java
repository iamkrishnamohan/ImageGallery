package com.zen3.imagegallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zen3.imagegallery.common.NDUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class GridViewActivity extends AppCompatActivity {
    private static final String TAG = GridViewActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_grid_view);

        

        // Use AsyncTask execute Method To Prevent ANR Problem
        new DownloadImagesAsyncTask().execute();


    }



    @Override
    public void onResume() {
        super.onResume();


    }





    @Override
    public void onBackPressed() {
        Intent menuIntent = new Intent(GridViewActivity.this, MainActivity.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
        finish();
        startActivity(menuIntent);
    }

    private class DownloadImagesAsyncTask extends AsyncTask<String, Void, String> {
        private boolean isInternetOff = false;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progressDialog = new ProgressDialog(GridViewActivity.this, android.R.style.Theme);
            if (!GridViewActivity.this.isFinishing()) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                //progressDialog.setMessage(getString(R.string.progressbar_load_categories));

            }
        }

        @Override
        protected void onPostExecute(String result) {



            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Log.e(TAG, "result " + result);
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    if (jsonObj.getString("status").equalsIgnoreCase("0")) {
                        // Getting JSON Array
                        JSONArray categoryArray = jsonObj.getJSONArray("object");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get any data from the url");
            }

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gridview);
            mRecyclerView.setHasFixedSize(true);

            // The number of Columns
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);



            /*if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }*/
        }

        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
    }

    private String GET(String url) {
        String result = "";
        try {
            URL getUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
            conn.setRequestMethod("GET");

            InputStream inputStream = conn.getInputStream();
            // convert inputstream to string
            if (inputStream != null)
                result = NDUtils.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            if (!NDUtils.checkNetworkAvailability(GridViewActivity.this)) {

            }
        }

        return result;
    }
}