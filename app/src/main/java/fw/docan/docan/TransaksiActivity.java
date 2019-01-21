package fw.docan.docan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fw.docan.docan.kwitansi.KirimActivity;
import fw.docan.docan.kwitansi.KwitansiActivity;
import fw.docan.docan.kwitansi.TerimaActivity;
import fw.docan.docan.model.RiwayatTransaksi;
import fw.docan.docan.model.trans_harga;
import fw.docan.docan.model.trans_waktu;

public class TransaksiActivity extends AppCompatActivity implements ListView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private ListView listView;
    private Spinner sorting;
    private String JSON_STRING;
    ProgressDialog loading;
    ImageView filt;
    public int harga,cekdetik,tipefilter;
    long iGabunganWaktu;
    public int jenis_sort;
    ArrayList<trans_harga> array_harga;
    ArrayList<trans_waktu> array_waktu;
    int id,icon;
    String type,amount,dateTime,tanggal,waktu,tahun,bulan,hari,jam,menit,detik,sGabunganWaktu;
    String[] parseDate,parseTanggal,parseWaktu;
    Dialog dialog;
    CheckBox CB1,CB2,CB3;
    Boolean check1,check2,check3;
    TextView text;

    private RiwayatTransaksiAdapter riwayatAdapter;
    private List<RiwayatTransaksi> mTransaksiList = new ArrayList<>();
    private String[] sort = {
            "Default","Tanggal", "Harga"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        jenis_sort = 0;
        tipefilter=123;

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sort);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        loading = ProgressDialog.show(TransaksiActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
        listView = (ListView) findViewById(R.id.listViewTransaksi);
        listView.setOnItemClickListener(this);
        ReadRiwayatTransaksi();
        sorting = findViewById(R.id.sorting);
        sorting.setOnItemSelectedListener(this);
        // mengeset Array Adapter tersebut ke Spinner
        sorting.setAdapter(adapter);

        text = findViewById(R.id.tipe_filter);
        filt= findViewById(R.id.filter);
        filt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(tipefilter){
                    case 1 : text.setText("1");
                        break;
                    case 2 : text.setText("2");
                        break;
                    case 3 : text.setText("3");
                        break;
                    case 12 : text.setText("1,2");
                        break;
                    case 13 : text.setText("1,3");
                        break;
                    case 23 : text.setText("2,3");
                        break;
                    case 123 : text.setText("ALL");
                        break;
                }
                PopupFilter();

            }
        });
    }

    private void PopupFilter(){
        dialog = new Dialog(TransaksiActivity.this);
        dialog.setContentView(R.layout.popup_filter);
        dialog.setTitle("FILTER");
        dialog.setCancelable(true);
        // there are a lot of settings, for dialog, check them all out!
        // set up radiobutton
        CB1 = dialog.findViewById(R.id.filter1);
        CB2 = dialog.findViewById(R.id.filter2);
        CB3 = dialog.findViewById(R.id.filter3);
        Button set = dialog.findViewById(R.id.set);

        // now that the dialog is set up, it's time to show it
        dialog.show();
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1 = CB1.isChecked(); //type 0
                if(check1){
                    CB1.setChecked(check1);
                }
                check2 = CB2.isChecked(); //type 1
                if(check2){
                    CB2.setChecked(check2);
                }
                check3 = CB3.isChecked(); //type 2
                if(check3){
                    CB3.setChecked(check3);
                }
                if(check1 == true && check2 == false && check3 == false){
                    tipefilter = 1;
                }
                if(check1 == false && check2 == true && check3 == false){
                    tipefilter = 2;
                }
                if(check1 == false && check2 == false && check3 == true){
                    tipefilter = 3;
                }
                if(check1 == true && check2 == true && check3 == false){
                    tipefilter = 12;
                }
                if(check1 == true && check2 == false && check3 == true){
                    tipefilter = 13;
                }
                if(check1 == false && check2 == true && check3 == true){
                    tipefilter = 23;
                }
                if(check1 == true && check2 == true && check3 == true){
                    tipefilter = 123;
                }
                String a = Integer.toString(tipefilter);
                Toast.makeText(TransaksiActivity.this,a,Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long id) {
        switch (i) {
            case 0:
                if(jenis_sort == 2 || jenis_sort == 1){
                    riwayatAdapter.clear();
                    loading = ProgressDialog.show(TransaksiActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
                    ReadRiwayatTransaksi();
                }
                jenis_sort = 0; //default
                break;
            case 1:
                if(jenis_sort == 0 || jenis_sort == 2) {
                    riwayatAdapter.clear();
                    loading = ProgressDialog.show(TransaksiActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
                    ReadRiwayatTransaksiTanggal();
                }
                jenis_sort = 1; //tanggal
                break;
            case 2:
                if(jenis_sort == 0 || jenis_sort == 1) {
                    riwayatAdapter.clear();
                    loading = ProgressDialog.show(TransaksiActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
                    ReadRiwayatTransaksiHarga();
                }
                jenis_sort = 2; //harga
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RiwayatTransaksi dataModel= mTransaksiList.get(position);
//            Object string=parent.getItemAtPosition(position);
            Log.d("**********", String.valueOf(dataModel.getJudulTransaksi()));
            if(String.valueOf(dataModel.getJudulTransaksi()).equals("Kirim"))
            {
                Intent intent = new Intent(this, KirimActivity.class);
//        RiwayatTransaksiAdapter map =(RiwayatTransaksiAdapter)parent.getItemAtPosition(position);
//        Log.d("**********", map.toString());
//        String empId = map.get("nip").toString();
                intent.putExtra("idtransaksi",String.valueOf(dataModel.getIdTransaksi()));
                startActivity(intent);
            }
            else if(String.valueOf(dataModel.getJudulTransaksi()).equals("Terima"))
            {
                Intent intent = new Intent(this, TerimaActivity.class);
//        RiwayatTransaksiAdapter map =(RiwayatTransaksiAdapter)parent.getItemAtPosition(position);
//        Log.d("**********", map.toString());
//        String empId = map.get("nip").toString();
                intent.putExtra("idtransaksi",String.valueOf(dataModel.getIdTransaksi()));
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(this, KwitansiActivity.class);
//        RiwayatTransaksiAdapter map =(RiwayatTransaksiAdapter)parent.getItemAtPosition(position);
//        Log.d("**********", map.toString());
//        String empId = map.get("nip").toString();
                intent.putExtra("idtransaksi",String.valueOf(dataModel.getIdTransaksi()));
                startActivity(intent);
            }

    }

    private void ReadRiwayatTransaksi() {

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("success");

                            //create new planet objects to add to planets list
                                for (int i = 0; i < userJson.length(); i++) {
                                    JSONObject jo = userJson.getJSONObject(i);
                                    id = jo.getInt("id");
                                    type = jo.getString("type");
                                    amount = jo.getString("amount");
                                    dateTime = jo.getString("dateTime");
                                    icon = R.drawable.bill;

                                    if (type.equals("0")) {
                                        type = "Kirim";
                                        icon = R.drawable.send;
                                    }
                                    if (type.equals("1")) {
                                        type = "Terima";
                                        icon = R.drawable.received;
                                    }
                                    if(tipefilter==123){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==1) && (type.equals("Kirim"))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==2) && (type.equals("Terima"))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==3) && (type.equals("transaction"))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==12) && ((type.equals("Kirim")) || (type.equals("Terima")))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==13) && ((type.equals("Kirim")) || (type.equals("transaction")))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                    if((tipefilter==23) && ((type.equals("Terima")) || (type.equals("transaction")))){
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }
                                }
//                            RiwayatTransaksiAdapter adapter = new RiwayatTransaksiAdapter(TransaksiActivity.this,);
//                            ListAdapter adapter = new SimpleAdapter(
//                                    TransaksiActivity.this, list, R.layout.list_item_riwayat_transaksi,
//                                    new String[]{"type","amount","dateTime","icon"},
//                                    new int[]{R.id.txtJudul, R.id.txtAmount, R.id.txtDateTime, R.id.iconTransaksi});
                            riwayatAdapter = new RiwayatTransaksiAdapter(getApplicationContext(), mTransaksiList);
                            listView.setAdapter(riwayatAdapter);
                            riwayatAdapter.notifyDataSetChanged();


                            JSONObject osaldo = (new JSONObject(response)).getJSONObject("user");
                            String balance = osaldo.getString("balance");
                            String pengeluaran = osaldo.getString("pengeluaran");
                            String pemasukan = osaldo.getString("pemasukan");
                            final TextView txtsaldo = (TextView) findViewById(R.id.txtSaldo);
                            txtsaldo.setText(String.format("%,d", Long.parseLong(balance)));
                            final TextView txtpengeluaran = (TextView) findViewById(R.id.txtPengeluaran);
                            txtpengeluaran.setText(String.format("%,d", Long.parseLong(pengeluaran)));
                            final TextView txtpemasukan = (TextView) findViewById(R.id.txtPemasukan);
                            txtpemasukan.setText(String.format("%,d", Long.parseLong(pemasukan)));
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
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void ReadRiwayatTransaksiHarga() {

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("success");

                            //create new planet objects to add to planets list
                            array_harga = new ArrayList<trans_harga>();
                                for (int i = 0; i < userJson.length(); i++) {
                                    JSONObject jo = userJson.getJSONObject(i);
                                    id = jo.getInt("id");
                                    type = jo.getString("type");
                                    amount = jo.getString("amount");
                                    harga = Integer.parseInt(amount);
                                    dateTime = jo.getString("dateTime");
                                    icon = R.drawable.bill;
                                    trans_harga h = new trans_harga(id,harga);
                                    if (type.equals("0")) {
                                        type = "Kirim";
                                        icon = R.drawable.send;
                                    }
                                    if (type.equals("1")) {
                                        type = "Terima";
                                        icon = R.drawable.received;
                                    }

                                    if(tipefilter==123){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==1) && (type.equals("Kirim"))){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==2) && (type.equals("Terima"))){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==3) && (type.equals("transaction"))){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==12) && ((type.equals("Kirim")) || (type.equals("Terima")))){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==13) && ((type.equals("Kirim")) || (type.equals("transaction")))){
                                        array_harga.add(h); //array list harga
                                    }
                                    if((tipefilter==23) && ((type.equals("Terima")) || (type.equals("transaction")))){
                                        array_harga.add(h); //array list harga
                                    }

                                }
                                Collections.sort(array_harga,trans_harga.ASCENDING_COMPARATOR);
                                for(int x = 0; x < array_harga.size();x++){
                                    for(int v = 0; v < userJson.length(); v++) {
                                        JSONObject jo = userJson.getJSONObject(v);
                                        id = jo.getInt("id");
                                        type = jo.getString("type");
                                        amount = jo.getString("amount");
                                        harga = Integer.parseInt(amount);
                                        dateTime = jo.getString("dateTime");
                                        icon = R.drawable.bill;
                                        if (type.equals("0")) {
                                            type = "Kirim";
                                            icon = R.drawable.send;
                                        }
                                        if (type.equals("1")) {
                                            type = "Terima";
                                            icon = R.drawable.received;
                                        }
                                         trans_harga take = array_harga.get(x);
                                        if ((take.getPrice() == harga)&&(take.getId() == id)) {
                                            RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                            mTransaksiList.add(mtransaksi);
                                        }

                                    }

                                }

//                            RiwayatTransaksiAdapter adapter = new RiwayatTransaksiAdapter(TransaksiActivity.this,);
//                            ListAdapter adapter = new SimpleAdapter(
//                                    TransaksiActivity.this, list, R.layout.list_item_riwayat_transaksi,
//                                    new String[]{"type","amount","dateTime","icon"},
//                                    new int[]{R.id.txtJudul, R.id.txtAmount, R.id.txtDateTime, R.id.iconTransaksi});
                            riwayatAdapter = new RiwayatTransaksiAdapter(getApplicationContext(), mTransaksiList);
                            listView.setAdapter(riwayatAdapter);
                            riwayatAdapter.notifyDataSetChanged();


                            JSONObject osaldo = (new JSONObject(response)).getJSONObject("user");
                            String balance = osaldo.getString("balance");
                            String pengeluaran = osaldo.getString("pengeluaran");
                            String pemasukan = osaldo.getString("pemasukan");
                            final TextView txtsaldo = (TextView) findViewById(R.id.txtSaldo);
                            txtsaldo.setText(String.format("%,d", Long.parseLong(balance)));
                            final TextView txtpengeluaran = (TextView) findViewById(R.id.txtPengeluaran);
                            txtpengeluaran.setText(String.format("%,d", Long.parseLong(pengeluaran)));
                            final TextView txtpemasukan = (TextView) findViewById(R.id.txtPemasukan);
                            txtpemasukan.setText(String.format("%,d", Long.parseLong(pemasukan)));
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
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void ReadRiwayatTransaksiTanggal() {

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("success");

                            //create new planet objects to add to planets list
                            array_waktu = new ArrayList<trans_waktu>();
                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject jo = userJson.getJSONObject(i);
                                id = jo.getInt("id");
                                type = jo.getString("type");
                                amount = jo.getString("amount");
                                dateTime = jo.getString("dateTime");
                                parseDate = dateTime.split(" ");
                                tanggal = parseDate[0];
                                waktu = parseDate[1];
                                parseTanggal = tanggal.split("-");
                                tahun = parseTanggal[0];
                                bulan = parseTanggal[1];
                                hari = parseTanggal[2];
                                parseWaktu = waktu.split(":");
                                jam = parseWaktu[0];
                                menit = parseWaktu[1];
                                //detik = parseWaktu[2];
                                //cekdetik = Integer.parseInt(detik);
                                //Gabungin waktu
                                sGabunganWaktu = tahun+bulan+hari+jam+menit;
                                iGabunganWaktu = Long.parseLong(sGabunganWaktu);
                                Log.d("waktu",sGabunganWaktu);
                                trans_waktu w = new trans_waktu(id,iGabunganWaktu);
                                icon = R.drawable.bill;

                                if (type.equals("0")) {
                                    type = "Kirim";
                                    icon = R.drawable.send;
                                }
                                if (type.equals("1")) {
                                    type = "Terima";
                                    icon = R.drawable.received;
                                }

                                if(tipefilter==123){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==1) && (type.equals("Kirim"))){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==2) && (type.equals("Terima"))){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==3) && (type.equals("transaction"))){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==12) && ((type.equals("Kirim")) || (type.equals("Terima")))){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==13) && ((type.equals("Kirim")) || (type.equals("transaction")))){
                                    array_waktu.add(w);
                                }
                                if((tipefilter==23) && ((type.equals("Terima")) || (type.equals("transaction")))){
                                    array_waktu.add(w);
                                }

                            }
                            Collections.sort(array_waktu,trans_waktu.ASCENDING_COMPARATOR);
                            for(int x = 0; x < array_waktu.size();x++){
                                for(int v = 0; v < userJson.length(); v++) {
                                    JSONObject jo = userJson.getJSONObject(v);
                                    id = jo.getInt("id");
                                    type = jo.getString("type");
                                    amount = jo.getString("amount");
                                    dateTime = jo.getString("dateTime");
                                    parseDate = dateTime.split(" ");
                                    tanggal = parseDate[0];
                                    waktu = parseDate[1];
                                    parseTanggal = tanggal.split("-");
                                    tahun = parseTanggal[0];
                                    bulan = parseTanggal[1];
                                    hari = parseTanggal[2];
                                    parseWaktu = waktu.split(":");
                                    jam = parseWaktu[0];
                                    menit = parseWaktu[1];
                                    //detik = parseWaktu[2];
                                    //Gabungin waktu
                                    sGabunganWaktu = tahun+bulan+hari+jam+menit;
                                    iGabunganWaktu = Long.parseLong(sGabunganWaktu);

                                    icon = R.drawable.bill;
                                    if (type.equals("0")) {
                                        type = "Kirim";
                                        icon = R.drawable.send;
                                    }
                                    if (type.equals("1")) {
                                        type = "Terima";
                                        icon = R.drawable.received;
                                    }
                                    trans_waktu take = array_waktu.get(x);
                                    if ((take.getTime() == iGabunganWaktu)&&(take.getId() == id)) {
                                        RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id, icon, type, dateTime, amount);
                                        mTransaksiList.add(mtransaksi);
                                    }

                                }

                            }

//                            RiwayatTransaksiAdapter adapter = new RiwayatTransaksiAdapter(TransaksiActivity.this,);
//                            ListAdapter adapter = new SimpleAdapter(
//                                    TransaksiActivity.this, list, R.layout.list_item_riwayat_transaksi,
//                                    new String[]{"type","amount","dateTime","icon"},
//                                    new int[]{R.id.txtJudul, R.id.txtAmount, R.id.txtDateTime, R.id.iconTransaksi});
                            riwayatAdapter = new RiwayatTransaksiAdapter(getApplicationContext(), mTransaksiList);
                            listView.setAdapter(riwayatAdapter);
                            riwayatAdapter.notifyDataSetChanged();


                            JSONObject osaldo = (new JSONObject(response)).getJSONObject("user");
                            String balance = osaldo.getString("balance");
                            String pengeluaran = osaldo.getString("pengeluaran");
                            String pemasukan = osaldo.getString("pemasukan");
                            final TextView txtsaldo = (TextView) findViewById(R.id.txtSaldo);
                            txtsaldo.setText(String.format("%,d", Long.parseLong(balance)));
                            final TextView txtpengeluaran = (TextView) findViewById(R.id.txtPengeluaran);
                            txtpengeluaran.setText(String.format("%,d", Long.parseLong(pengeluaran)));
                            final TextView txtpemasukan = (TextView) findViewById(R.id.txtPemasukan);
                            txtpemasukan.setText(String.format("%,d", Long.parseLong(pemasukan)));
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
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
