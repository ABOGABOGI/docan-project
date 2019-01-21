package fw.docan.docan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AgenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agen);

        LinearLayout linearListAgen = findViewById(R.id.linearListAgen);
        linearListAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgenActivity.this, ListAgenActivity.class));
            }
        });

        LinearLayout linearDaftarAgen = findViewById(R.id.linearDaftarAgen);
        linearDaftarAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgenActivity.this, DaftarAgenActivity.class));
            }
        });

        LinearLayout linearJualCash = findViewById(R.id.linearJualCash);
        linearJualCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgenActivity.this, JualCashActivity.class));
            }
        });

        LinearLayout linearJualSaldo = findViewById(R.id.linearJualSaldo);
        linearJualSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgenActivity.this,JualSaldoActivity.class));
            }
        });


    }
}
