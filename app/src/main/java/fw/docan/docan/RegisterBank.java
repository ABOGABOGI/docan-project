package fw.docan.docan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Delayed;

public class RegisterBank extends AppCompatActivity {
    TextInputEditText userid,mpin;
    public String indeks_bank, mpin_bank, user_id;
    Button login_mbank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bank);

        userid = findViewById(R.id.id_mbank);
        mpin = findViewById(R.id.mpin);
        user_id = userid.getText().toString();
        mpin_bank= mpin.getText().toString();

        login_mbank = findViewById(R.id.Login);
        login_mbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indeks_bank = "bca";
                ShowDialog();

            }
        });
    }

    private void ShowDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tambah Bank")
                .setMessage("Anda Yakin Ingin Menambahkan Bank?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(RegisterBank.this, "Berhasil Login "+user_id+" Mpin : "+ mpin_bank, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(RegisterBank.this, BankActivity.class));
//                        bankdua = findViewById(R.id.mybank2);
//                        switch (bank){
//                            case 1: bankdua.setImageResource(R.drawable.bca); break;
//                            case 2: bankdua.setImageResource(R.drawable.mandiri); break;
//                            case 3: bankdua.setImageResource(R.drawable.bri); break;
//                            case 4: bankdua.setImageResource(R.drawable.jatim); break;
//                            case 5: bankdua.setImageResource(R.drawable.arfindo); break;
//                            case 6: bankdua.setImageResource(R.drawable.kuningan); break;
//                            case 7: bankdua.setImageResource(R.drawable.mega); break;
//                            case 8: bankdua.setImageResource(R.drawable.sumsel); break;
//                            case 9: bankdua.setImageResource(R.drawable.ocbc); break;
//                        }

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alert.show();
    }
}
