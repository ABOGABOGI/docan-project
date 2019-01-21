package fw.docan.docan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fw.docan.docan.adapter.Adapter;



public class CenterActivity extends AppCompatActivity {

    private RecyclerView rv;
    ArrayList<Beanlist> Bean;
    private RecyclerViewAdapter baseAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    Adapter adapter;
    private Context context;
    String[] urls;
    SliderLayout sliderLayout;

    Typeface fonts1,fonts2,fonts3;
    TextView amountnumber;
    TextView txtvw;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    TextView buttonKirim;
    TextView buttonTerima;


    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    public ArrayList<String> ImgUrl= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_center);
        ImgUrl = getIntent().getStringArrayListExtra("ImgUrl");

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :

        setSliderViews();



        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }


        fonts1 =  Typeface.createFromAsset(getAssets(),
                "fonts/Liquide.ttf");
        fonts2 =  Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Medium.ttf");
        fonts3 =  Typeface.createFromAsset(getAssets(),
                "fonts/MavenPro-Regular.ttf");


        amountnumber = (TextView)findViewById(R.id.amountnumber);
        amountnumber.setTypeface(fonts1);

        //*********RECYCLERVIEWS*********


//        this.recyclerView = (RecyclerView)findViewById(R.id.rv);
//        Manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(Manager);
//        adapter = new Adapter(ImgUrl, this);
//        recyclerView.setAdapter(adapter);

        //Bean = new ArrayList<Beanlist>();
//
//        for (int i= 0; i< AMOUNT.length; i++){
//            Beanlist bean = new Beanlist(drawArrayList[i], AMOUNT[i], BILLNAME[i], DATE[i]);
//            Bean.add(bean);
//        }
//
//
//        baseAdapter = new RecyclerViewAdapter(CenterActivity.this, Bean) {
//        };
//
//        rv.setAdapter(baseAdapter);

        Button btnKirim = (Button)findViewById(R.id.buttonKirim);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, Kirim.class));
            }
        });

        Button btnTerima = (Button)findViewById(R.id.buttonTerima);
        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, Terima.class));
            }
        });

        LinearLayout LayoutTransaksi = (LinearLayout)findViewById(R.id.linearTransaksi);
        LayoutTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, TransaksiActivity.class));
            }
        });

        LinearLayout LayoutIsiSaldo = (LinearLayout)findViewById(R.id.linearIsiSaldo);
        LayoutIsiSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, IsiSaldoActivity.class));
            }
        });

        LinearLayout LayoutBank = (LinearLayout)findViewById(R.id.linearBank);
        LayoutBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, BankActivity.class));
            }
        });

        LinearLayout LayoutPembelian = (LinearLayout)findViewById(R.id.linearPembelian);
        LayoutPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CenterActivity.this, PembayaranActivity.class));
                startActivity(new Intent(CenterActivity.this, PembelianActivity.class));
            }
        });

        LinearLayout LayoutAgen = (LinearLayout)findViewById(R.id.linearAgen);
        LayoutAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, AgenActivity.class)); //MapsActivity
            }
        });

        LinearLayout LayoutPoint = findViewById(R.id.linearPoint);
        LayoutPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CenterActivity.this,PointActivity.class);
                intent.putStringArrayListExtra("ImgUrl",ImgUrl);
                startActivity(intent);
            }
        });

        LinearLayout LayoutBantuan = (LinearLayout)findViewById(R.id.linearBantuan);
        LayoutBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CenterActivity.this, BantuanActivity.class));
            }
        });

        LinearLayout LayoutAkun = (LinearLayout)findViewById(R.id.linearAkun);
        LayoutAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, ProfileActivity.class));
            }
        });

        LinearLayout LayoutPenjual = (LinearLayout)findViewById(R.id.linearPenjual);
        LayoutPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CenterActivity.this, PenjualActivity.class)); //tadinya MainActivity.class
            }
        });


//        ImageView imageNotif = (ImageView)findViewById(R.id.imageNotifications);
//        imageNotif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(CenterActivity.this, NotifikasiActivity.class));
//            }
//        });

        User user = SharedPrefManager.getInstance(this).getUser();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            txtvw = (TextView) findViewById(R.id.amountnumber);
            txtvw.setText(String.format("%,d", Long.parseLong(user.getBalance())));
        }

        String[] web = CenterActivity.this.getResources().getStringArray(R.array.nav_drawer_labels);


        Integer[] imageId = {
                R.drawable.ic_home,
                R.drawable.ic_tv,
                R.drawable.ic_ticket,
                R.drawable.ic_offers,
                R.drawable.ic_earning,
                R.drawable.ic_usemoney,
                R.drawable.ic_ranking,
                R.drawable.ic_faq,
                R.drawable.ic_symbol21,


        };



        CustomList adapter1 = new
                CustomList(CenterActivity.this, web, imageId);
        ListView list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter1);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

    }

    @Override
    public void onResume() {
        super.onResume();
        User user = SharedPrefManager.getInstance(this).getUser();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            txtvw = (TextView) findViewById(R.id.amountnumber);
            txtvw.setText(String.format("%,d", Long.parseLong(user.getBalance())));

        }
    }

    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;
        public CustomList(Activity context,
                          String[] web, Integer[] imageId) {
            super(context, R.layout.nav_drawer_row, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View rowView= inflater.inflate(R.layout.nav_drawer_row, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web[position]);

            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }


    private void setSliderViews() {

        for (int i = 0; i <= 2; i++) {

            SliderView sliderView = new SliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageUrl(ImgUrl.get(0));
                    break;
                case 1:
                    sliderView.setImageUrl(ImgUrl.get(1));
                    break;
                case 2:
                    sliderView.setImageUrl(ImgUrl.get(2));
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("PROMO " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(CenterActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}
