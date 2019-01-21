package fw.docan.docan.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import fw.docan.docan.R;
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;
import fw.docan.docan.cardemulation.AccountStorage;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductCreateFragment extends Fragment {
    private Button btnAdd,pilihfoto;
    private View view;

    private TextInputEditText txtProductCode;
    private TextInputEditText txtName;
    private TextInputEditText txtCategory;
    private TextInputEditText txtUnit;
    private TextInputEditText txtRawPrice;
    private TextInputEditText txtPrice;
    private TextInputEditText txtDescription;
    public Bitmap product_image,scale_product_image;
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int PICK_IMAGE = 1;
    String imgDecodableString;
    ImageView Image;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductCreateFragment newInstance(String param1, String param2) {
        ProductCreateFragment fragment = new ProductCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container,new ProductCreateFragment());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
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
        view =  inflater.inflate(R.layout.fragment_product_create, container, false);
        pilihfoto = view.findViewById(R.id.choose_image);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        pilihfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateProduct();
            }
        });

        txtProductCode = (TextInputEditText) view.findViewById(R.id.txtProductCode);
        txtName = (TextInputEditText) view.findViewById(R.id.txtName);
        txtCategory = (TextInputEditText) view.findViewById(R.id.txtCategory);
        txtUnit = (TextInputEditText) view.findViewById(R.id.txtUnit);
        txtRawPrice = (TextInputEditText) view.findViewById(R.id.txtRawPrice);
        txtPrice = (TextInputEditText) view.findViewById(R.id.txtPrice);
        txtDescription = (TextInputEditText) view.findViewById(R.id.txtDescription);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                //ImageView imgView = (ImageView) view.findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                product_image = BitmapFactory.decodeFile(imgDecodableString);
                scale_product_image = Bitmap.createScaledBitmap(product_image,1280,1280,false);
                //Pop up gambar produknya
                    // custom dialog
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                // set the custom dialog components - text, image and button
                ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);

                // Close Button
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //TODO Close button action
                    }
                });

                //Image = view.findViewById(R.id.image_product);
                //Image.setImageBitmap(scale_product_image);
                Drawable d = new BitmapDrawable(getResources(),scale_product_image);
                dialog.getWindow().setBackgroundDrawable(d);
                dialog.show();
                //Toast.makeText(getActivity(), "Gambar berhasil dipilih", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
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

    private void CreateProduct() {
        //first getting the values
        //final String amount = mEdit.getText().toString();
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATE_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);

                            ProductsFragment nextFrag= new ProductsFragment();
                            view.setVisibility(View.INVISIBLE);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                            //JSONObject result = jsonObject.getJSONObject("MataKuliah");
                            // kode = jsonObject.getString("token");
                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            //AccountStorage.SetAccount(getActivity(), kode);

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
                params.put("category", txtCategory.getText().toString());
                params.put("name", txtName.getText().toString());
                params.put("code", txtProductCode.getText().toString());
                params.put("description", txtDescription.getText().toString());
                params.put("unit", txtUnit.getText().toString());
                params.put("rawprice", txtRawPrice.getText().toString());
                params.put("price", txtPrice.getText().toString());
                params.put("image", "null");
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
