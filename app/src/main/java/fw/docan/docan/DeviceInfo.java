package fw.docan.docan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fw.docan.docan.model.RiwayatTransaksi;

public class DeviceInfo extends AppCompatActivity {

    TextView Board, Bootloader, Brand, Hardware, Host, Id, Manufaktur, Model, Produk, Serial, Type, User;
    String user_docan, model_api, serial_api, hardware_api, id_api;
    JSONObject name, model, mac, nfc, version;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);


        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        String board = "BOARD : " + Build.BOARD;
        String bootloader = "BOOTLOADER : " + Build.BOOTLOADER;
        String brand = "BRAND : " + Build.BRAND;
        String hardware = "HARDWARE : " + Build.HARDWARE;
        hardware_api = Build.HARDWARE;
        String host = "HOST : " + Build.HOST;
        String id = "ID : " + Build.ID;
        id_api = Build.ID;
        String manufaktur = "MANUFACTURER : " + Build.MANUFACTURER;
        String model = "MODEL : " + Build.MODEL;
        model_api = Build.MODEL;
        String produk = "PRODUCT : " + Build.PRODUCT;
        String serial = "SERIAL : " + Build.SERIAL;
        serial_api = Build.SERIAL;
        String type = "TYPE : " + Build.TYPE;
        String user = "USER : " + Build.USER;

        Board = findViewById(R.id.Board);
        Bootloader = findViewById(R.id.Bootloader);
        Brand = findViewById(R.id.Brand);
        Hardware = findViewById(R.id.Hardware);
        Host = findViewById(R.id.Host);
        Id = findViewById(R.id.Id);
        Manufaktur = findViewById(R.id.Manufaktur);
        Model = findViewById(R.id.Model);
        Produk = findViewById(R.id.Produk);
        Serial = findViewById(R.id.Serial);
        Type = findViewById(R.id.Type);
        User = findViewById(R.id.User);

        Board.setText(board);
        Bootloader.setText(bootloader);
        Brand.setText(brand);
        Hardware.setText(hardware);
        Host.setText(host);
        Id.setText(id);
        Manufaktur.setText(manufaktur);
        Model.setText(model);
        Produk.setText(produk);
        Serial.setText(serial);
        Type.setText(type);
        User.setText(user);

        //ReadDeviceInfo();
        checkNetworkConnection();
        send();

    }

    // check network connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            Log.d("device","Connected");
        }
        return isConnected;
    }


    private String httpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("device",result);
        }
    }


    public void send() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        // perform HTTP POST request
        if(checkNetworkConnection())
            new HTTPAsyncTask().execute(URLs.URL_DEVICE_INFO);
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

    }

    private JSONObject buidJsonObject() throws JSONException {
        final User us = SharedPrefManager.getInstance(this).getUser();
        user_docan = us.getUsername();

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("name", user_docan);
        jsonObject.accumulate("model", model_api);
        jsonObject.accumulate("mac", serial_api);
        jsonObject.accumulate("nfc", hardware_api);
        jsonObject.accumulate("version", id_api);
        JSONObject json = new JSONObject();
        json.put("perangkat",jsonObject);

        return json;
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }


//    private void ReadDeviceInfo() {
//        //getting the current user
//        final User us = SharedPrefManager.getInstance(this).getUser();
//        user_docan = us.getUsername();
//
//        try {
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            JSONObject perangkat = new JSONObject();
//            perangkat.put("name", user_docan);
//            perangkat.put("model", model_api);
//            perangkat.put("mac", serial_api);
//            perangkat.put("nfc", hardware_api);
//            perangkat.put("version", id_api);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DEVICE_INFO, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("VOLLEY", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.toString());
//                }
//            });
//
//            requestQueue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}


