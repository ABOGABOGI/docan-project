package fw.docan.docan;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Faishal Wahiduddin on 3/16/2018.
 */

public class TransactionsManager extends Activity {

    public void CreateTransaction(Context konteks, String userKirimS, String dateTimeS, String amountS, String descriptionS, String locationS, String typeS) {

        final String userKirim = userKirimS;
        final String dateTime = dateTimeS;
        final String amount = amountS;
        final String description = descriptionS;
        final String location = locationS;
        final String type = typeS;
        final Context ctx= konteks;
//        LogsManager lgm = new LogsManager();
//        lgm.CreateLog("4.0000,5.0000","Create Transaction "+userKirim+" amount "+amount+" ");
        final User user = SharedPrefManager.getInstance(this).getUser();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATE_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogsManager lgm = new LogsManager();
                        lgm.CreateLog(ctx,"4.0000,5.0000","Transaction");
                        BalanceManager balance = new BalanceManager();
                        balance.UpdateBalance(ctx);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("transaction", error.getMessage());
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
                params.put("userKirim", userKirim);
                params.put("dateTime", dateTime);
                params.put("amount", amount);
                params.put("description", description);
                params.put("location", location);
                params.put("type", type);
                return params;
            }
        };

        VolleySingleton.getInstance(konteks).addToRequestQueue(stringRequest);
    }
}
