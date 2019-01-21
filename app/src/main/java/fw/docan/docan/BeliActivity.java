package fw.docan.docan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

public class BeliActivity extends AppCompatActivity  implements NfcAdapter.CreateNdefMessageCallback,NfcAdapter.OnNdefPushCompleteCallback{
    NfcAdapter mNfcAdapter;
    EditText mEdit;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli);
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
        mEdit   = (EditText)findViewById(R.id.editText1);

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = encrypt("Toko Serba Ada:"
//                editJudul.getText().toString()+":"+
//                editHarga.getText().toString()+":"+
//                editKeterangan.getText().toString()+":"
        );
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        //Log.d(TAG, "onNdefPushComplete");
//        editJudul.setEnabled(false);
//        editHarga.setEnabled(false);
//        editKeterangan.setEnabled(false);

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
}
