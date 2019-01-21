package fw.docan.docan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import fw.docan.docan.adapter.PenjualAdapter;

public class PenjualActivity extends AppCompatActivity {
    ListView listView;
    String[] name ={
            "NASI CAMPUR B2",
            "DISTRO ASIK",
            "WATCH DOG",
            "BAG GO",
            "YAMIEN"
    };
    String[] address ={
            "Jl. Jalan no.1, Bandung",
            "Jl. yauda no.5, Cirebon",
            "Jl. mikir no.11, Bogor",
            "Jl. capek no.7, Jakarta",
            "Jl. Mie no.11, Bandung"
    };
    String[] type ={
            "Makanan",
            "Pakaian",
            "Jam Tangan",
            "Tas",
            "Makanan"
    };
    Integer[] img={
            R.drawable.nasicampur,
            R.drawable.distro,
            R.drawable.watch,
            R.drawable.bag,
            R.drawable.yamien
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_main);

        PenjualAdapter adapter=new PenjualAdapter(this, name, address,type, img);

        listView = findViewById(R.id.list_seller);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PenjualActivity.this,HomeSeller.class);
                startActivity(intent);
                //Toast.makeText(PenjualActivity.this,"Click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
