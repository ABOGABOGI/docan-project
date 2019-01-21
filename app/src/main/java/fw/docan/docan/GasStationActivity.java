package fw.docan.docan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fw.docan.docan.cardemulation.AccountStorage;

public class GasStationActivity extends AppCompatActivity  implements NfcAdapter.CreateNdefMessageCallback,NfcAdapter.OnNdefPushCompleteCallback {
    NfcAdapter mNfcAdapter;
    EditText mEdit;
    Button btnSetKirim;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station);
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this,"Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);
        mAdapter.setOnNdefPushCompleteCallback(this, this);
        mEdit   = (EditText)findViewById(R.id.txtJumlah);
        btnSetKirim = (Button)findViewById(R.id.btnBayar);

        EditText account = mEdit;
        account.setText(AccountStorage.GetAmount(this));
        account.addTextChangedListener(new GasStationActivity.AccountUpdater());

        //Button btnBeli = (Button)findViewById(R.id.buttonBeli);
        mContext=this;

        btnSetKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSetKirim.setVisibility(View.GONE);
                mEdit.setEnabled(false);
                //mEdit.setText("Rp " + String.format("%,d", Long.parseLong(mEdit.getText().toString())));
                setTransfer();
            }
        });
        //User user = SharedPrefManager.getInstance(this).getUser();
        //user.setBalance("9999");

    }

    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            AccountStorage.SetAmount(getApplicationContext(), account);
        }
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {

        User user = SharedPrefManager.getInstance(this).getUser();
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        String message = encrypt(user.getId()+";"+mEdit.getText().toString()+";"+n);
        //String message = "AAAAEEEEAAAA";
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        BalanceManager balance = new BalanceManager();
        balance.UpdateBalance(mContext);
        //finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivityForResult(intent, 1);
        //Toast.makeText(this,"Success",Toast.LENGTH_LONG);
        //Log.d(TAG, "onNdefPushComplete");

        //this.KurangSaldo(Integer.parseInt(mEdit.getText().toString()));


//        runOnUiThread(new Runnable() {
//            public void run() {
//                TextView textView = (TextView) findViewById(R.id.title);
//                textView.setText("Message beamed!");
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        //textView = (TextView) findViewById(R.id.textView);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        //textView.setText(new String(msg.getRecords()[0].getPayload()));
    }

    public static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }
    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
    public void KurangSaldo(int number){
        SharedPreferences preferences = getSharedPreferences("docan", MODE_PRIVATE);
        String passEncrypted = preferences.getString(encrypt("password"), encrypt("0"));
        int saldo = Integer.parseInt(decrypt(passEncrypted));

        preferences = getSharedPreferences("docan", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(encrypt("password"), encrypt(String.valueOf(saldo-number)));
        editor.apply(); // Or commit if targeting old devices
    }

    public void onBackPressed(){
        BalanceManager balance = new BalanceManager();
        balance.UpdateBalance(mContext);
        finish();
    }

    private void setTransfer() {
        //first getting the values
        final String amount = mEdit.getText().toString();
        final User user = SharedPrefManager.getInstance(this).getUser();
        //validating inputs
        if (TextUtils.isEmpty(amount)) {
            mEdit.setError("Please enter your email");
            mEdit.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_KIRIM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            //JSONObject result = jsonObject.getJSONObject("MataKuliah");
                            String kode = jsonObject.getString("token");
                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            AccountStorage.SetAccount(getApplicationContext(), kode);

                            user.setKirim(kode);
                            //starting the profile activity
                            //finish();
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
                params.put("amount", amount);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}