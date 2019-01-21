package fw.docan.docan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;
import android.widget.VideoView;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewToken, textViewUsername, textViewEmail, textViewPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        /*
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/


        //textViewToken = (TextView) findViewById(R.id.textViewToken);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        //textViewToken.setText(user.getToken());
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewPhone.setText(user.getPhone());

        //Verifikasi Akun
        findViewById(R.id.VerAkun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pindah ke AktivasiActivity
                Intent intent = new Intent(ProfileActivity.this,AktivasiActivity.class);
                startActivity(intent);
            }
        });

        //Verifikasi Peringkat
        findViewById(R.id.perangkat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,DeviceInfo.class);
                startActivity(intent);
            }
        });

        //Ubah Bahasa
        findViewById(R.id.ubah_bahasa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,UbahBahasa.class);
                startActivity(intent);
            }
        });

        //Ketentuan Layanan
        findViewById(R.id.ketentuan_layanan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,KetentuanLayanan.class);
                startActivity(intent);
            }
        });

        //Kebijakan Privasi
        findViewById(R.id.kebijakan_privasi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pindah ke KebijakanPrivasi
                Intent intent = new Intent(ProfileActivity.this,KebijakanPrivasi.class);
                startActivity(intent);
            }
        });

        //Beri Kami Nilai
        findViewById(R.id.BeriNilai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
