package fw.docan.docan;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AktivasiActivity extends AppCompatActivity{
    private ImageView btnFotoSelfie, btnFotoKTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_akun);
        btnFotoSelfie = findViewById(R.id.selfie);
        btnFotoKTP = findViewById(R.id.ktp);

        btnFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buka kamera
                Intent intent = new Intent(AktivasiActivity.this, SelfieActivity.class);
                startActivity(intent);
            }
        });

        btnFotoKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buka kamera
                Intent intent = new Intent(AktivasiActivity.this, KTPActivity.class);
                startActivity(intent);
            }
        });

    }





}
