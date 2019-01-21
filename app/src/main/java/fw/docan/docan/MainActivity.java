package fw.docan.docan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    TextView txtvw;
    Button buttonTambahSaldo;
    Button buttonKurangSaldo;
    EditText editTextToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button btnKirim = (Button)findViewById(R.id.btnKirim);
//




//
//        User user = SharedPrefManager.getInstance(this).getUser();
//
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//            return;
//        }
//        //when the user presses logout button
//        //calling the logout method
//        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
//            }
//        });
//
//        btnKirim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Kirim.class));
//            }
//        });
//
//        editTextToken = findViewById(R.id.editTextToken);
//        editTextToken.setText(user.getToken());
//        Button btnTerima = (Button)findViewById(R.id.btnTerima);
////        buttonTambahSaldo = (Button)findViewById(R.id.buttonAddSaldo);
////        buttonKurangSaldo = (Button)findViewById(R.id.buttonKurangSaldo);
//
//        btnTerima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Terima.class));
//            }
//        });

////        //this.AddSaldo(10000);
////        SharedPreferences pref = getApplicationContext().getSharedPreferences("docan", MODE_PRIVATE);
////        String passEncrypted = pref.getString(encrypt("password"), encrypt("0"));
//        int saldo = Integer.parseInt(decrypt(passEncrypted));
        //int saldo = pref.getInt(encrypt("password"), 0);

//        buttonTambahSaldo.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v)
//            {
//                AddSaldo(5000);
//            }
//        });
//        buttonKurangSaldo.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v)
//            {
//                AddSaldo(-5000);
//            }
//        });


//        SharedPrefManager.Editor editor = getSharedPreferences("docan", MODE_PRIVATE).edit();
//        editor.putString("bank", "Kesejahteraan");
//        editor.putInt("saldo", 50000);
//        editor.apply();
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            txtvw = (TextView) findViewById(R.id.txtSaldoo);
//            txtvw.setText("Rp " + String.format("%,d", Long.parseLong(user.getBalance())));
//        }
//        SharedPrefManager prefs = getSharedPreferences("docan", MODE_PRIVATE);
//        String restoredText = prefs.getString("text", null);
//        if (restoredText != null) {
//            String name = prefs.getString("bank", "No name defined");//"No name defined" is the default value.
//            int saldo = prefs.getInt("saldo", 0); //0 is the default value.
////            txtvw = (TextView) findViewById(R.id.txtSaldo);
////            txtvw.setText(saldo);
//        }


//        if (sharedPref.getInt("saldo",0)<50000) {
//            sharedPref = getSharedPreferences("docan", Context.MODE_PRIVATE);
//            editor = sharedPref.edit();
//            editor.putString("saldo", "50000");
//            editor.commit();
//        }

        //Toast.makeText(this,sharedPref.getString("saldo","0"), Toast.LENGTH_LONG).show();
        //txtvw = (TextView) findViewById(R.id.txtSaldo);
        //txtvw.setText(sharedPref.getString("saldo",""));saldo



    }

    @Override
    public void onResume(){
        super.onResume();
//        User user = SharedPrefManager.getInstance(this).getUser();
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            txtvw = (TextView) findViewById(R.id.txtSaldoo);
//            txtvw.setText("Rp " + String.format("%,d", Long.parseLong(user.getBalance())));
//
//        }
        // put your code here...
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("docan", MODE_PRIVATE);
//        int saldo = pref.getInt("saldo", 0);
//
//        SharedPreferences preferences = getSharedPreferences("docan", MODE_PRIVATE);
//        String passEncrypted = preferences.getString(encrypt("password"), encrypt("0"));
//        String pass = decrypt(passEncrypted);
//
//
//        txtvw = (TextView) findViewById(R.id.txtSaldoo);
//        //txtvw.setText("Rp "+String.format("%,d", Long.parseLong(String.valueOf(pass))));
//        txtvw.setText(pass);
//        txtvw.setText("Rp "+String.format("%,d", Long.parseLong(txtvw.getText().toString())));
    }

//    public static String encrypt(String input) {
//        // This is base64 encoding, which is not an encryption
//        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
//    }
//
//    public static String decrypt(String input) {
//        return new String(Base64.decode(input, Base64.DEFAULT));
//    }

//    public void AddSaldo(int number){
//        SharedPreferences preferences = getSharedPreferences("docan", MODE_PRIVATE);
//        String passEncrypted = preferences.getString(encrypt("password"), encrypt("0"));
//        int saldo = Integer.parseInt(decrypt(passEncrypted));
//
//        preferences = getSharedPreferences("docan", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(encrypt("password"), encrypt(String.valueOf(saldo+number)));
//        editor.apply(); // Or commit if targeting old devices
//
//        txtvw.setText("Rp "+String.format("%,d", Long.parseLong(String.valueOf(saldo+number))));
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//                txtvw.setText("OK BERES");
//
//    }//onActivityResult
//
//    public void setBalance(String balance)
//    {
//        txtvw.setText(balance);
//    }
}
