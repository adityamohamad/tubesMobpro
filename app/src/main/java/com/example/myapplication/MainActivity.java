package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private EditText etEmail;
    private EditText etPassword;
    private ProgressDialog pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get Reference to variables
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {
        // Get text from email and password field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        // Initialize AsyncLogin() class with email and password
        new AsyncLogin().execute(email, password);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // This method will be running on UI thread
            pdLoading = new ProgressDialog(MainActivity.this);
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("http://192.168.100.6/TubesMobpro/login.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    return result.toString();
                } else {
                    return "unsuccessful";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // This method will be running on UI thread
            pdLoading.dismiss();
            if (result.equalsIgnoreCase("true")) {
                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                startActivity(intent);
                finish();
            } else if (result.equalsIgnoreCase("false")) {
                // If username and password do not match, display an error message
                Toast.makeText(MainActivity.this, "Email atau Password tidak valid", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                Toast.makeText(MainActivity.this, "Ups! Sepertinya koneksi sedang bermasalah!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
