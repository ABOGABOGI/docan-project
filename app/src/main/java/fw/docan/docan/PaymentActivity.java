package fw.docan.docan;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import fw.docan.docan.cardreader.LoyaltyCardReader;
import fw.docan.docan.kwitansi.ModelClass;
import fw.docan.docan.kwitansi.RecyclerAdapter;

public class PaymentActivity extends AppCompatActivity implements LoyaltyCardReader.AccountCallback {
    NfcAdapter mNfcAdapter;


    private ArrayList<ModelClass> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    Context mContext;
    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mLoyaltyCardReader = new LoyaltyCardReader(this);
        String iteamName[]={"Orange and Rosemerry","Cool Water-Rebel","Flat White"};
        String quantity[]={"1","1","1"};
        String price[]={"1.95","2.90","3.00"};
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        homeListModelClassArrayList = new ArrayList<>();

        for (int i = 0; i < iteamName.length; i++) {
            ModelClass beanClassForRecyclerView_contacts = new ModelClass(iteamName[i],quantity[i],price[i]);
            homeListModelClassArrayList.add(beanClassForRecyclerView_contacts);
        }
        mAdapter = new RecyclerAdapter(PaymentActivity.this.getApplicationContext(),homeListModelClassArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PaymentActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mContext=this;
        // Disable Android Beam and register our card reader callback
        enableReaderMode();
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


//        if(account!="0") {
//            setTransfer(account);
//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    BalanceManager balance = new BalanceManager();
//                    balance.UpdateBalance(mContext);
//                }
//            });
//
//            final TextView txtvw = (TextView) findViewById(R.id.textView);
        final LinearLayout lyPayment = (LinearLayout)findViewById(R.id.linearLayoutPayment);
        final LinearLayout lyBayar = (LinearLayout)findViewById(R.id.linearLayoutBayar);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    lyPayment.setVisibility(View.VISIBLE);
                    lyBayar.setVisibility(View.VISIBLE);
//                    txtvw.setText(account);
                }
            });
//        }

    }
}
