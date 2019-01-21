package fw.docan.docan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


import fw.docan.docan.cardemulation.AccountStorage;
import fw.docan.docan.cardreader.LoyaltyCardReader;

import static android.nfc.NdefRecord.createMime;

public class Terima extends AppCompatActivity  implements LoyaltyCardReader.AccountCallback {
    NfcAdapter mNfcAdapter;
    TextView textView;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    int latest;
    String token,jumlah;
    Context mContext;
    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("0");
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

//        Button btnJual = (Button)findViewById(R.id.buttonJual);
//
//        btnJual.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Terima.this, JualActivity.class));
//            }
//        });
        latest = 0;
        mContext=this;
        // Register callback
        //mNfcAdapter.setNdefPushMessageCallback(this, this);

//        TransactionsManager trm = new TransactionsManager();
//        trm.CreateTransaction("3", "2018/03/16", "8", "Transaksi", "Rumah", "Personal");
//        BalanceManager balance = new BalanceManager();
//        balance.UpdateBalance();
        mLoyaltyCardReader = new LoyaltyCardReader(this);

        // Disable Android Beam and register our card reader callback
        enableReaderMode();

    }


//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//        String text = ("Beam me up, Android!\n\n" +
//                "Beam Time: " + System.currentTimeMillis());
//        NdefMessage msg = new NdefMessage(
//                new NdefRecord[] { createMime(
//                        "application/vnd.com.example.android.beam", text.getBytes())
//                        /**
//                         * The Android Application Record (AAR) is commented out. When a device
//                         * receives a push with an AAR in it, the application specified in the AAR
//                         * is guaranteed to run. The AAR overrides the tag dispatch system.
//                         * You can add it back in to guarantee that this
//                         * activity starts when receiving a beamed message. For now, this code
//                         * uses the tag dispatch system.
//                        */
//                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
//                });
//        return msg;
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        Intent intent = getIntent();
//        textView = (TextView) findViewById(R.id.textView);
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
//            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
//                    NfcAdapter.EXTRA_NDEF_MESSAGES);
//
//            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
//            String Dekrip = decrypt(new String(message.getRecords()[0].getPayload()));
//            String[] separated = Dekrip.split(";");
//             // this will contain "Fruit"
//
//            int terima = Integer.parseInt(separated[1]);
//            int number = Integer.parseInt(separated[2]);
//            textView.setText(String.valueOf(terima));
//            //this.AddSaldo(terima);
//            //sharedPref= getSharedPreferences("docan", Context.MODE_PRIVATE);
//            //editor=sharedPref.edit();
//            //editor.putInt("saldo", sharedPref.getInt("saldo",0)+Integer.parseInt(textView.getText().toString()));
//            //editor.commit();
//            textView.setText("Rp "+String.format("%,d", Long.parseLong(textView.getText().toString())));
//            //textView.setText(Dekrip);
//            //if(latest!=number) {
//
//                TransactionsManager trm = new TransactionsManager();
//                trm.CreateTransaction(mContext,separated[0], "2018/03/16", separated[1], "Transaksi", "Rumah", "Personal");
//                BalanceManager balance = new BalanceManager();
//                balance.UpdateBalance(mContext);
//                latest=number;
////            }
//
//        } else
//            textView.setText("0");
//
//    }
////
////    @Override
////    public void onNewIntent(Intent intent) {
////        // onResume gets called after this to handle the intent
////        setIntent(intent);
////    }
////
////    /**
////     * Parses the NDEF Message from the intent and prints to the TextView
////     */
////    void processIntent(Intent intent) {
////        textView = (TextView) findViewById(R.id.textView);
////        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
////                NfcAdapter.EXTRA_NDEF_MESSAGES);
////        // only one message sent during the beam
////        NdefMessage msg = (NdefMessage) rawMsgs[0];
////        // record 0 contains the MIME type, record 1 is the AAR, if present
////        textView.setText(new String(msg.getRecords()[0].getPayload()));
////    }
//
//    public static String decrypt(String input) {
//        return new String(Base64.decode(input, Base64.DEFAULT));
//    }
//    public static String encrypt(String input) {
//        // This is base64 encoding, which is not an encryption
//        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
//    }
//
//    public void AddSaldo(int number){
//        User user = SharedPrefManager.getInstance(this).getUser();
//
//
//        SharedPreferences preferences = getSharedPreferences("docan", MODE_PRIVATE);
//        String passEncrypted = preferences.getString(encrypt("password"), encrypt("0"));
//        int saldo = Integer.parseInt(decrypt(passEncrypted));
//
//        preferences = getSharedPreferences("docan", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(encrypt("password"), encrypt(String.valueOf(saldo+number)));
//        editor.apply(); // Or commit if targeting old devices
//    }
    public void onBackPressed(){
        BalanceManager balance = new BalanceManager();
        balance.UpdateBalance(mContext);
        finish();
    }

















    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
        //textView.setText(AccountStorage.GetAmount(this));
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        if(account!="0") {
            setTransfer(account);
            this.runOnUiThread(new Runnable() {
                                @Override
                public void run() {
                                    BalanceManager balance = new BalanceManager();
                                    balance.UpdateBalance(mContext);
                }
            });

//            final TextView txtvw = (TextView) findViewById(R.id.textView);
//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    txtvw.setText(account);
//                }
//            });
        }

    }


    private void setTransfer(String a) {
        //first getting the values
        final String token = a;
        final User user = SharedPrefManager.getInstance(this).getUser();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_KIRIM_COMPLETED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            //JSONObject result = jsonObject.getJSONObject("MataKuliah");
                            String kode = jsonObject.getString("amount");
                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            jumlah = kode;
                            final TextView txtvw = (TextView) findViewById(R.id.textView);
                            txtvw.setText("Rp. "+String.format("%,d", Long.parseLong(kode)));
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
                params.put("token", token);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}
