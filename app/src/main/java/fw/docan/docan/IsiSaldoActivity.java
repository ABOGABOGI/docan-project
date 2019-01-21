package fw.docan.docan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class IsiSaldoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_saldo);

        LinearLayout linearAgenSaldo = findViewById(R.id.linearAgenSaldo);
        linearAgenSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IsiSaldoActivity.this, ListAgenActivity.class));
            }
        });

        LinearLayout linearMobileBanking = findViewById(R.id.linearMobileBanking);
        linearMobileBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IsiSaldoActivity.this,IsiSaldo_MobileBanking.class));
            }
        });

        LinearLayout linearKartuDebit = findViewById(R.id.linearKartuDebit);
        linearKartuDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IsiSaldoActivity.this,IsiSaldo_KartuDebit.class));
            }
        });

        LinearLayout linearEmoney = findViewById(R.id.linearEmoney);
        linearEmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IsiSaldoActivity.this,Terima.class));
            }
        });

        LinearLayout linearATM = findViewById(R.id.linearATM);
        linearATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IsiSaldoActivity.this,IsiSaldo_ATM.class));
            }
        });

    }
}
