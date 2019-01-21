package fw.docan.docan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Faishal Wahiduddin on 3/13/2018.
 */

public class BalanceManager extends Activity{

    public void SendBalance( String amount, String email) {

        final String ip = getLocalIpAddress();
        final String samount = amount;
        final String semail = email;

        final User user = SharedPrefManager.getInstance(this).getUser();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "multipart/form-data;");
                params.put("Authorization", "Bearer "+user.getToken());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ipAddress", ip);
                params.put("amount", samount);
                params.put("receiver", semail);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        //Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            //Log.e(TAG, ex.toString());
        }
        return null;
    }

    public void UpdateBalance(Context konteks) {

        final Context ktx = konteks;
        final User user = SharedPrefManager.getInstance(this).getUser();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CHECK_BALANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


//
//                                Log.d("tag", usrJson.getString("username"));
//                                Log.d("tag", usrJson.getString("email"));
//                                Log.d("tag", userJson.getString("token"));
//                                Log.d("tag", usrJson.getString("phone"));
                            //creating a new user object

                            //user.setBalance();
                            SharedPrefManager.getInstance(ktx).userBalance(obj.getString("balance"));
                            LogsManager lgm = new LogsManager();
                            lgm.CreateLog(ktx,"4.0000,5.0000","Update Balance");
                            //storing the user in shared preferences
                            //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//                            LogsManager lgm = new LogsManager();
//                            lgm.CreateLog("4.0000,5.0000","Update Balance");
                            //starting the profile activity
                            //finish();
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            } else {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("balance", error.getMessage());
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "multipart/form-data;");
                params.put("Authorization", "Bearer "+user.getToken());
                return params;
            }

        };

        VolleySingleton.getInstance(konteks).addToRequestQueue(stringRequest);
    }
}
