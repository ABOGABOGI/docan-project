package fw.docan.docan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fw.docan.docan.adapter.PembelianAdapter;


public class PembelianActivity extends AppCompatActivity {
    ListView listView;
    String[] itemname ={
            "TOP UP GO-PAY",
            "TOP UP GRABPAY",
            "TOP UP UNIKQU",
            "TOKEN LISTRIK"
    };
    Integer[] imgid={
            R.drawable.gopay,
            R.drawable.grabpay,
            R.drawable.unikqu,
            R.drawable.tokenlistrik,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        PembelianAdapter adapter=new PembelianAdapter(this, itemname, imgid);

        listView = findViewById(R.id.listview_pembelian);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    Intent intent = new Intent(PembelianActivity.this,Pembelian_1.class);
                    startActivity(intent);
                }
                if(i == 1)
                {
                    Intent intent = new Intent(PembelianActivity.this,Pembelian_2.class);
                    startActivity(intent);
                }
                if(i == 2)
                {
                    Intent intent = new Intent(PembelianActivity.this,Pembelian_3.class);
                    startActivity(intent);
                }
                if(i == 3)
                {
                    Intent intent = new Intent(PembelianActivity.this,Pembelian_4.class);
                    startActivity(intent);
                }


            }
        });
    }
}
