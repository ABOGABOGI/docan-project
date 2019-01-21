package fw.docan.docan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.Map;

import fw.docan.docan.R;
import fw.docan.docan.SharedPrefManager;
import fw.docan.docan.URLs;
import fw.docan.docan.User;
import fw.docan.docan.VolleySingleton;
import fw.docan.docan.activity.MainActivity;
import android.support.v4.app.FragmentManager;



import fw.docan.docan.model.Categories;
import fw.docan.docan.model.Products;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment  implements View.OnClickListener{

    private LinearLayout linear_progressbar;

    private Toolbar toolbar;
    private TextView toolBarTxt;

    private RecyclerView recyclerView;
    private RecycleAdapter_AddProduct mAdapter;
    private int status_code;
    private String token,totalPriceOfProducts;


//    private ProductArrayList productsArrayList;

    private TextView quantityOfTotalProduct,priceOfTotalProduct,next;
    private Categories categories;

    private int[] IMAGES = {R.drawable.shoes,R.drawable.shoes,R.drawable.shoes,R.drawable.shoes,R.drawable.shoes};
    private String[] NamES = {"Shoes","T-shirt","Jacket","Tie","Shocks"};
    private String[] PRICE = {"150","132","101","93","85"};


    private View view;


    Animation startAnimation;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private Button aboutBtn;
    private String JSON_STRING;

    ProgressDialog loading;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_products, container,
                false);

        //ListView listView = (ListView) view.findViewById(R.id.list_view2);

//        listView = (ListView)view.findViewById(R.id.listViewProducts);
//        loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);

//        aboutBtn = (Button) view.findViewById(R.id.btnAdd);
//        aboutBtn.setOnClickListener(this);

        Button newGameButton = (Button) view.findViewById(R.id.btnAddBaru);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "This is my Toast message!",
//                        Toast.LENGTH_LONG).show();
//                ((fw.docan.docan.activity.MainActivity)getActivity()).ProductCreateFragment();

                ProductCreateFragment nextFrag= new ProductCreateFragment();
                view.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);


        initComponent(view);


        categories = new Categories();
        categories.productsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        ReadProducts();


//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ProductArrayList productsForSend = new ProductArrayList();
//                productsForSend =  productsToBeSend(categoriesAndProducts);
//                PrefUtils.setProducts(productsForSend, getActivity());
//
//                if (productsForSend.getProductsArrayList().size() >0){
//                    Intent it = new Intent(getActivity(), ProductSummaryActivity.class);
//                    startActivity(it);
//                }else {
//                    Toast.makeText(getActivity(), "Please Select The Products", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });




        return view;

       // return inflater.inflate(R.layout.fragment_products, container, false);

    }

    private void initComponent(View view) {


    }

    public class RecycleAdapter_AddProduct extends RecyclerView.Adapter<RecycleAdapter_AddProduct.MyViewHolder> {

        Context context;
        boolean showingFirst = true;
        private Categories categories;

        int recentPos = -1;



        public class MyViewHolder extends RecyclerView.ViewHolder {




            ImageView image;
            TextView title;
            TextView price;
            TextView description;
            TextView quantityTxt;
            private LinearLayout llMinus,llPlus;
            int quantity;


            public MyViewHolder(View view) {
                super(view);

                image = (ImageView) view.findViewById(R.id.image);
                title = (TextView) view.findViewById(R.id.title);
                price = (TextView) view.findViewById(R.id.price);
                description = (TextView) view.findViewById(R.id.description);
                quantityTxt = (TextView) view.findViewById(R.id.quantityTxt);
                llPlus = (LinearLayout)view.findViewById(R.id.llPlus);
                llMinus = (LinearLayout)view.findViewById(R.id.llMinus);
            }

        }



        public RecycleAdapter_AddProduct(Context context, Categories categories) {
            this.categories = categories;
            this.context = context;
        }

        @Override
        public RecycleAdapter_AddProduct.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_all_products, parent, false);

            return new RecycleAdapter_AddProduct.MyViewHolder(itemView);


        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final RecycleAdapter_AddProduct.MyViewHolder holder, final int position) {
//            Products movie = productsList.get(position);

            holder.title.setText(categories.getProductsArrayList().get(position).getName());
            holder.price.setText(categories.getProductsArrayList().get(position).getPrice());
            holder.description.setText(categories.getProductsArrayList().get(position).getDescription());
            holder.quantityTxt.setText(categories.getProductsArrayList().get(position).getQuantity() + "");


            holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            int totalPrice = holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice());


            if (position == recentPos) {
                Log.e("pos", "" + recentPos);
                // start animation
                holder.quantityTxt.startAnimation(startAnimation);

            } else {
                holder.quantityTxt.clearAnimation();

            }



            categories.getProductsArrayList().get(position).setPriceAsPerQuantity(""+ totalPrice);






        }

        @Override
        public int getItemCount() {
            return categories.getProductsArrayList().size();
        }

    }

    private void swapFragment(){
        ProductCreateFragment newGamefragment = new ProductCreateFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btnAdd:
//                fragment = new ProductCreateFragment();
//                replaceFragment(fragment);
                break;

//            case R.id.phbookButton:
//                fragment = new PhoneBookFragment();
//                replaceFragment(fragment);
//                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onActivityCreated(savedInstanceState);
//
//        listView = (ListView) container.findViewById(R.id.list);
//        //listView.setOnItemClickListener(getActivity().getApplicationContext());
//        loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
//        //getJSON();
//        Log.d("**********", "Mulaiiii");
//        ReadProducts();
//
//    }

    private void ReadProducts() {

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        final User user = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray userJson = (new JSONObject(response)).getJSONArray("products");
                            Log.d("**********", userJson.toString());
                            for(int i = 0; i<userJson.length(); i++){
                                JSONObject jo = userJson.getJSONObject(i);
                                String name = jo.getString("name");
                                String price = jo.getString("price");
                                String description = jo.getString("description");
                                Log.d("**********", name+"   "+price);
                                HashMap<String,String> mahasiswa = new HashMap<>();
                                mahasiswa.put("name",name);
                                mahasiswa.put("price","Rp."+price);
                                Products products = new Products();
                                products.setName(name);
                                products.setPrice(price);
                                products.setDescription(description);
                                products.setImage(IMAGES[0]);

                                categories.productsArrayList.add(products);
                                //list.add(mahasiswa);
                            }

                            mAdapter = new RecycleAdapter_AddProduct(getActivity(),categories);

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
//                            ListAdapter adapter = new SimpleAdapter(
//                                    getActivity(), list, R.layout.list_item_product,
//                                    new String[]{"name","price"},
//                                    new int[]{R.id.id, R.id.NamaProduct});
//
//                            listView.setAdapter(adapter);

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
                params.put("category", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }
}
