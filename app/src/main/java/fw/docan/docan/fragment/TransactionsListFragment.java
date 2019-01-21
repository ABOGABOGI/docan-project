package fw.docan.docan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fw.docan.docan.R;
import fw.docan.docan.RiwayatTransaksiAdapter;
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.TransaksiActivity;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;
import fw.docan.docan.kwitansi.KwitansiActivity;
import fw.docan.docan.model.RiwayatTransaksi;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsListFragment extends Fragment {
    private ListView listView;
    private String JSON_STRING;
    ProgressDialog loading;

    private RiwayatTransaksiAdapter riwayatAdapter;
    private List<RiwayatTransaksi> mTransaksiList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsListFragment newInstance(String param1, String param2) {
        TransactionsListFragment fragment = new TransactionsListFragment();
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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_transactions_list, container, false);

        loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
        listView = (ListView) view.findViewById(R.id.listViewTransaksi);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RiwayatTransaksi dataModel= mTransaksiList.get(position);
                //Intent intent = new Intent(getActivity(), KwitansiActivity.class);
//        RiwayatTransaksiAdapter map =(RiwayatTransaksiAdapter)parent.getItemAtPosition(position);
//        Log.d("**********", map.toString());
//        String empId = map.get("nip").toString();
                //intent.putExtra("idtransaksi",String.valueOf(dataModel.getIdTransaksi()));
                //startActivity(intent);

                TransactionFragment nextFrag= new TransactionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("idtransaksi",String.valueOf(dataModel.getIdTransaksi()));
                nextFrag.setArguments(bundle);
                //view.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
//
//                Intent myIntent = new Intent(CurrentActivity.this, NextActivity.class);
//                CurrentActivity.this.startActivity(myIntent);
            }
        });
        ReadRiwayatTransaksi();


        Button newTransaction = (Button) view.findViewById(R.id.btnNewTransaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseProductsFragment nextFrag= new ChooseProductsFragment();
                view.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
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

    private void ReadRiwayatTransaksi() {

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SELLER_TRANSACTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("success");

                            //create new planet objects to add to planets list

                            for(int i = 0; i<userJson.length(); i++){
                                JSONObject jo = userJson.getJSONObject(i);
                                int id = jo.getInt("id");
                                String type = jo.getString("type");
                                String amount = jo.getString("amount");
                                String dateTime = jo.getString("dateTime");
                                int icon= R.drawable.bill;
                                if(type.equals("0"))
                                {
                                    type="Kirim";
                                    icon=R.drawable.send;
                                }
                                if(type.equals("1"))
                                {
                                    type="Terima";
                                    icon=R.drawable.received;
                                }
//                                HashMap<String,String> transaksi = new HashMap<>();
//                                transaksi.put("type",type);
//                                transaksi.put("icon",icon);
//                                transaksi.put("amount",String.format("%,d", Long.parseLong(amount.toString())));
//                                transaksi.put("dateTime",dateTime);
//                                list.add(transaksi);
                                RiwayatTransaksi mtransaksi = new RiwayatTransaksi(id,icon, type,dateTime,amount);
                                mTransaksiList.add(mtransaksi);
                            }

//                            RiwayatTransaksiAdapter adapter = new RiwayatTransaksiAdapter(TransaksiActivity.this,);
//                            ListAdapter adapter = new SimpleAdapter(
//                                    TransaksiActivity.this, list, R.layout.list_item_riwayat_transaksi,
//                                    new String[]{"type","amount","dateTime","icon"},
//                                    new int[]{R.id.txtJudul, R.id.txtAmount, R.id.txtDateTime, R.id.iconTransaksi});
                            riwayatAdapter = new RiwayatTransaksiAdapter(getActivity().getApplicationContext(), mTransaksiList);
                            listView.setAdapter(riwayatAdapter);

//                            JSONObject osaldo = (new JSONObject(response)).getJSONObject("user");
//                            String balance = osaldo.getString("balance");
//                            String pengeluaran = osaldo.getString("pengeluaran");
//                            String pemasukan = osaldo.getString("pemasukan");
//                            final TextView txtsaldo = (TextView) view.findViewById(R.id.txtSaldo);
//                            txtsaldo.setText(String.format("%,d", Long.parseLong(balance)));
//                            final TextView txtpengeluaran = (TextView) view.findViewById(R.id.txtPengeluaran);
//                            txtpengeluaran.setText(String.format("%,d", Long.parseLong(pengeluaran)));
//                            final TextView txtpemasukan = (TextView) view.findViewById(R.id.txtPemasukan);
//                            txtpemasukan.setText(String.format("%,d", Long.parseLong(pemasukan)));
                            loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }
}
