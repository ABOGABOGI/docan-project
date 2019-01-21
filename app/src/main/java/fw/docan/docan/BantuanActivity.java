package fw.docan.docan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BantuanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        ListView listView = findViewById(R.id.listview);

        ListBantuanActivity[] items ={
                new ListBantuanActivity("Masalah dengan User Interface DOCAN"),
                new ListBantuanActivity("Bagaimana cara menggunakan aplikasi DOCAN"),
                new ListBantuanActivity("Masalah dengan GPS"),
                new ListBantuanActivity("Masalah dengan NFC"),
                new ListBantuanActivity("Masalah dengan koneksi Internet"),
        };

        ArrayAdapter<ListBantuanActivity> adapter = new ArrayAdapter<ListBantuanActivity>(this,android.R.layout.simple_list_item_1,items);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    Intent intent = new Intent(BantuanActivity.this,Bantuan_1.class);
                    startActivity(intent);
                }
                if(i == 1)
                {
                    Intent intent = new Intent(BantuanActivity.this,Bantuan_2.class);
                    startActivity(intent);
                }
                if(i == 2)
                {
                    Intent intent = new Intent(BantuanActivity.this,Bantuan_3.class);
                    startActivity(intent);
                }
                if(i == 3)
                {
                    Intent intent = new Intent(BantuanActivity.this,Bantuan_4.class);
                    startActivity(intent);
                }
                if(i == 4)
                {
                    Intent intent = new Intent(BantuanActivity.this,Bantuan_5.class);
                    startActivity(intent);
                }


            }
        });
    }
}
