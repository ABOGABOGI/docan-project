package fw.docan.docan.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fw.docan.docan.BalanceManager;
import fw.docan.docan.R;
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;
import fw.docan.docan.activity.DeviceList;
import fw.docan.docan.activity.PrinterCommands;
import fw.docan.docan.activity.Utils;
import fw.docan.docan.cardreader.LoyaltyCardReader;
import fw.docan.docan.kwitansi.KwitansiActivity;
import fw.docan.docan.kwitansi.ModelClass;
import fw.docan.docan.kwitansi.RecyclerAdapter;

import static fw.docan.docan.cardemulation.CardService.ByteArrayToHexString;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment implements LoyaltyCardReader.AccountCallback {

    NfcAdapter nfcAdapter;

    private ArrayList<ModelClass> homeListModelClassArrayList;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private String idtransaksi;
    private TextView txtPenjual;
    private TextView txtJumlah;
    private TextView txtJumlahBawah;
    private String[] separated;

    public LoyaltyCardReader mLoyaltyCardReader;
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    NfcAdapter mNfcAdapter;

    Context mContext;
    public static final String TAG = "CardReaderFragment";


    private LinearLayout linearBelumDibayar;
    private LinearLayout linearTempelkanPembeli;
    private LinearLayout linearPembayaranBerhasil;
    private LinearLayout linearPrint;

    EditText message;
    Button btnPrint, btnBill;

    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            idtransaksi = getArguments().getString("idtransaksi");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container,
                false);
        // Inflate the layout for this fragment
        String iteamName[]={"Orange and Rosemerry","Cool Water-Rebel","Flat White"};
        String quantity[]={"1","1","1"};
        String price[]={"1.95","2.90","3.00"};
        txtPenjual = (TextView) view.findViewById(R.id.txtPenjual);
        txtJumlah = (TextView) view.findViewById(R.id.txtJumlah);
        txtJumlahBawah = (TextView) view.findViewById(R.id.txtJumlahBawah);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        linearBelumDibayar = (LinearLayout)  view.findViewById(R.id.linearBelumDibayar);
        linearTempelkanPembeli = (LinearLayout)  view.findViewById(R.id.linearTempelkanPembeli);
        linearPembayaranBerhasil = (LinearLayout)  view.findViewById(R.id.linearPembayaranBerhasil);
        linearPrint = (LinearLayout)  view.findViewById(R.id.linearPrint);
        linearPrint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                printDemo();
            }
        });


        homeListModelClassArrayList = new ArrayList<>();

        for (int i = 0; i < iteamName.length; i++) {
            ModelClass beanClassForRecyclerView_contacts = new ModelClass(iteamName[i],quantity[i],price[i]);
            homeListModelClassArrayList.add(beanClassForRecyclerView_contacts);
        }
        mAdapter = new RecyclerAdapter(getActivity().getApplicationContext(),homeListModelClassArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        homeListModelClassArrayList = new ArrayList<>();

        ViewGroup.LayoutParams params_new=recyclerView.getLayoutParams();
        params_new.height=RecyclerView.LayoutParams.WRAP_CONTENT;
        recyclerView.setLayoutParams(params_new);


//        Intent intent = getIntent();
//        idtransaksi = intent.getStringExtra("idtransaksi");

        GetTransaksi(idtransaksi);


        mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity().getApplicationContext());
        if (mNfcAdapter == null) {
            Toast.makeText(getActivity().getApplicationContext(), "NFC is not available", Toast.LENGTH_LONG).show();
//            finish();
//            return;
        }

        mContext=getActivity().getApplicationContext();
        // Register callback
        //mNfcAdapter.setNdefPushMessageCallback(this, this);

//        TransactionsManager trm = new TransactionsManager();
//        trm.CreateTransaction("3", "2018/03/16", "8", "Transaksi", "Rumah", "Personal");
//        BalanceManager balance = new BalanceManager();
//        balance.UpdateBalance();
        mLoyaltyCardReader = new LoyaltyCardReader(this);

        // Disable Android Beam and register our card reader callback
        enableReaderMode();





        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void GetTransaksi(String id) {
        final String idtransaksi = id;
        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
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
                            String userKirim = result.getString("userKirim");
                            if(!userKirim.equals("0"))
                            {
                                linearBelumDibayar.setVisibility(View.GONE);
                                linearTempelkanPembeli.setVisibility(View.GONE);
                                linearPembayaranBerhasil.setVisibility(View.VISIBLE);
                                linearPrint.setVisibility(View.VISIBLE);
                            }


                            txtPenjual.setText(penjual);
                            txtJumlah.setText("Rp."+String.format("%,d", Long.parseLong(amount)));
                            txtJumlahBawah.setText("Rp."+String.format("%,d", Long.parseLong(amount)));
                            separated = description.split(";");
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
                                mAdapter = new RecyclerAdapter(getActivity(), homeListModelClassArrayList);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
                            //loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


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
        //Log.i(TAG, "Enabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null ) {
            nfc.enableReaderMode(activity, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        //Log.i(TAG, "Disabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {

            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Detected = "+account, Toast.LENGTH_SHORT).show();
                }
            });

        if(account.contains("chip:")) {
            payTransactionChip(account.replace("chip:", ""));

        }
        else if(account!="0") {
            payTransaction(account);


//            setTransfer(account);
//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    BalanceManager balance = new BalanceManager();
//                    balance.UpdateBalance(mContext);
//                }
//            });

//            final TextView txtvw = (TextView) findViewById(R.id.textView);
//            this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    txtvw.setText(account);
//                }
//            });
        }

    }

    private void payTransaction(String token) {
        final String usertoken = token;
        //first getting the values
        //final String amount = mEdit.getText().toString();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        //validating inputs
//        if (TextUtils.isEmpty(amount)) {
//            mEdit.setError("Please enter your email");
//            mEdit.requestFocus();
//            return;
//        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PAY_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject result = jsonObject.getJSONObject("status");
                            linearBelumDibayar.setVisibility(View.GONE);
                            linearTempelkanPembeli.setVisibility(View.GONE);
                            linearPembayaranBerhasil.setVisibility(View.VISIBLE);
                            linearPrint.setVisibility(View.VISIBLE);
//                            String idtransaction = result.getString("idtransaction");
//                            String idtransaction="0";
//                            JSONArray userJson = jsonObject.getJSONArray("success");
//                            for(int i = 0; i<userJson.length(); i++) {
//                                JSONObject jo = userJson.getJSONObject(i);
//                                idtransaction = jo.getString("idtransaction");
//                            }

                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            // AccountStorage.SetAccount(getActivity(), kode);
//                            TransactionFragment nextFrag= new TransactionFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("idtransaksi",idtransaction);
//                            nextFrag.setArguments(bundle);
//                            //view.setVisibility(View.INVISIBLE);
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, nextFrag,"findThisFragment")
//                                    .addToBackStack(null)
//                                    .commit();
                            //user.setKirim(kode);
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("idtransaction", idtransaksi);
                params.put("token", usertoken);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    private void payTransactionChip(String chip) {
        final String userchip = chip;
        //first getting the values
        //final String amount = mEdit.getText().toString();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        //validating inputs
//        if (TextUtils.isEmpty(amount)) {
//            mEdit.setError("Please enter your email");
//            mEdit.requestFocus();
//            return;
//        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_TOKEN_CHIP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.getString("token");
                            payTransaction(token);


//                            JSONObject result = jsonObject.getJSONObject("status");
//                            linearBelumDibayar.setVisibility(View.GONE);
//                            linearTempelkanPembeli.setVisibility(View.GONE);
//                            linearPembayaranBerhasil.setVisibility(View.VISIBLE);
//                            linearPrint.setVisibility(View.VISIBLE);
//                            String idtransaction = result.getString("idtransaction");
//                            String idtransaction="0";
//                            JSONArray userJson = jsonObject.getJSONArray("success");
//                            for(int i = 0; i<userJson.length(); i++) {
//                                JSONObject jo = userJson.getJSONObject(i);
//                                idtransaction = jo.getString("idtransaction");
//                            }

                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            // AccountStorage.SetAccount(getActivity(), kode);
//                            TransactionFragment nextFrag= new TransactionFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("idtransaksi",idtransaction);
//                            nextFrag.setArguments(bundle);
//                            //view.setVisibility(View.INVISIBLE);
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, nextFrag,"findThisFragment")
//                                    .addToBackStack(null)
//                                    .commit();
                            //user.setKirim(kode);
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //params.put("idtransaction", idtransaksi);
                params.put("chip", userchip);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    protected void printDemo() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getActivity(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();

                byte[] printformat = { 0x1B, 0*21, FONT_TYPE };
                //outputStream.write(printformat);

                //print title
                printUnicode();
                //print normal text
                //printCustom("AAAAAAAAAAAA",0,0);
                printPhoto(R.drawable.header);
                printNewLine();
                if(separated.length>1) {
                    for (int i = 0; i < separated.length; i++) {
                        String[] dataTransaksi = separated[i].split(":");
                        if(dataTransaksi.length>3) {
                            printText(dataTransaksi[0]+"   " +dataTransaksi[1]+"   " +dataTransaksi[3]+"   " +dataTransaksi[4]);
                            printNewLine();
                        }
                    }
                }
                printNewLine();
                printText("Total "+txtJumlah.getText());
                printNewLine();
                printText("     >>>>   Thank you  <<<<     "); // total 32 char in a single line
                //resetPrint(); //reset printer
                printUnicode();
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if(btsocket != null){
                //printText("AAAAAAAAAAAAA");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
