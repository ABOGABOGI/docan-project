package fw.docan.docan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.KeyEvent;
import android.widget.ImageView;

public class BankActivity extends AppCompatActivity {
    public int bank;
    ImageView bankdua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);


        ImageButton btnDashboard = (ImageButton)findViewById(R.id.mybank1);
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankActivity.this, BankDashboardActivity.class));
            }
        });

        ImageButton btnBCA = findViewById(R.id.bank1);
        btnBCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=1;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnMandiri = findViewById(R.id.bank2);
        btnMandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=2;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnBRI = findViewById(R.id.bank3);
        btnBRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=3;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnJatim = findViewById(R.id.bank4);
        btnJatim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=4;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnArfindo = findViewById(R.id.bank5);
        btnArfindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=5;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnKuningan = findViewById(R.id.bank6);
        btnKuningan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=6;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnMega = findViewById(R.id.bank7);
        btnMega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=7;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnSumsel = findViewById(R.id.bank8);
        btnSumsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=8;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });

        ImageButton btnOCBC = findViewById(R.id.bank9);
        btnOCBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bank=9;
                startActivity(new Intent(BankActivity.this, RegisterBank.class));
            }
        });
    }



}

