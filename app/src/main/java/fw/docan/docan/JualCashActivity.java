package fw.docan.docan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JualCashActivity extends AppCompatActivity {
    Button button_cash;
    EditText nominal;
    public int nominalcash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual_cash);

        button_cash = findViewById(R.id.btnCash);
        button_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nominal = findViewById(R.id.nominal_cash);
                nominalcash  = Integer.parseInt(nominal.getText().toString());
                showDialog();
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Cari Agen dengan nominal Cash "+nominalcash+" ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk mencari agen!")
                .setIcon(R.drawable.logo_tipis)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan ...
                        startActivity(new Intent(JualCashActivity.this, ListAgenActivity.class));
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }



}
