package fw.docan.docan.kwitansi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
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
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;

public class KwitansiActivity extends AppCompatActivity {

    private ArrayList<ModelClass> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private String idtransaksi;
    private TextView txtPenjual;
    private TextView txtJumlah;
    private TextView txtJumlahBawah;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kwitansi);


        String iteamName[]={"Orange and Rosemerry","Cool Water-Rebel","Flat White"};
        String quantity[]={"1","1","1"};
        String price[]={"1.95","2.90","3.00"};
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        homeListModelClassArrayList = new ArrayList<>();

        ViewGroup.LayoutParams params_new=recyclerView.getLayoutParams();
        params_new.height=RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setLayoutParams(params_new);

        txtPenjual = (TextView) findViewById(R.id.txtPenjual);
        txtJumlah = (TextView) findViewById(R.id.txtJumlah);
        txtJumlahBawah = (TextView) findViewById(R.id.txtJumlahBawah);

        Intent intent = getIntent();
        idtransaksi = intent.getStringExtra("idtransaksi");


        loading = ProgressDialog.show(KwitansiActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
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


                            txtPenjual.setText(penjual);
                            txtJumlah.setText("Rp."+String.format("%,d", Long.parseLong(amount)));
                            txtJumlahBawah.setText("Rp."+String.format("%,d", Long.parseLong(amount)));
                            String[] separated = description.split(";");
                            if(separated.length>1) {
                                for (int i = 0; i < separated.length; i++) {
                                    String[] dataTransaksi = separated[i].split(":");
                                    if(dataTransaksi.length>3) {
                                        Log.d("**********", dataTransaksi[0]);
                                        Log.d("**********", dataTransaksi[1]);
                                        Log.d("**********", dataTransaksi[2]);
                                        Log.d("**********", dataTransaksi[3]);
                                        Log.d("**********", dataTransaksi[4]);
                                        ModelClass beanClassForRecyclerView_contacts = new ModelClass(dataTransaksi[1], dataTransaksi[3], dataTransaksi[4]);
                                        homeListModelClassArrayList.add(beanClassForRecyclerView_contacts);
                                    }
                                }
                                mAdapter = new RecyclerAdapter(KwitansiActivity.this, homeListModelClassArrayList);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(KwitansiActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                                if(RecyclerView.LayoutParams.WRAP_CONTENT > 50){
                                    ViewGroup.LayoutParams params_new=recyclerView.getLayoutParams();
                                    params_new.height=50;
                                    recyclerView.setLayoutParams(params_new);
                                }

                            }
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
