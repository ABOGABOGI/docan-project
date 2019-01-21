package fw.docan.docan.kwitansi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fw.docan.docan.R;
import fw.docan.docan.ReceiptActivity;
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;

public class KirimActivity extends AppCompatActivity {
    private ArrayList<ModelClass> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private String idtransaksi;
    private TextView txtPenerima;
    private TextView txtJumlah;

    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kwitansi_kirim);

        txtPenerima = (TextView) findViewById(R.id.txtPenerima);
        txtJumlah = (TextView) findViewById(R.id.txtJumlah);

        Intent intent = getIntent();
        idtransaksi = intent.getStringExtra("idtransaksi");


        loading = ProgressDialog.show(KirimActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
        GetTransaksi(idtransaksi);
    }

    private void GetTransaksi(String id) {
        final String idtransaksi = id;
        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject result = jsonObject.getJSONObject("transaction");
                            String dateTime = result.getString("dateTime");
                            String amount = result.getString("amount");
                            String description = result.getString("description");
                            String penjual = result.getString("penjual");


                            txtPenerima.setText(penjual);
                            txtJumlah.setText("Rp."+String.format("%,d", Long.parseLong(amount)));



//                            editTextNama.setText(nama);
//                            editTextJK.setSelection(Integer.parseInt(jk));
//                            editTextProdi.setText(prodi);
//                            editTextNFCDosen.setText(nfc);
                            loading.dismiss();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idtransaksi);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
