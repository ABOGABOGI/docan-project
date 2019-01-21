package fw.docan.docan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class DaftarAgenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_agen);

        LinearLayout linearlokasi = findViewById(R.id.linearlokasi);
        linearlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarAgenActivity.this,fotolokasi.class));
            }
        });

        LinearLayout lineargps = findViewById(R.id.lineargps);
        lineargps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarAgenActivity.this,kirimgps.class));
            }
        });

    }
}
