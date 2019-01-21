package fw.docan.docan;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    public ArrayList<String> ImgUrl= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        ListPromo();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run(){
                Intent homeIntent = new Intent(StartScreenActivity.this,CenterActivity.class);
                homeIntent.putStringArrayListExtra("ImgUrl",ImgUrl);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }

    private void ListPromo() {
        final User user = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PROMO,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("promo");

                            //create new planet objects to add to planets list
                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject jo = userJson.getJSONObject(i);
                                int id = jo.getInt("id");
                                String name = jo.getString("name");
                                String desc = jo.getString("description");
                                String image = jo.getString("image");
                                ImgUrl.add(image);
                                // drawArrayList[i] = drawable_from_url(image);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}
