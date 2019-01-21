package fw.docan.docan;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TukarSaldoActivity extends AppCompatActivity {
    RadioGroup radio;
    Button tukarsaldo;
    public String jumlahsaldo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukarsaldo);

        radio=findViewById(R.id.radiosaldo);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int i) {
                RadioButton rbtn = (RadioButton) radio.findViewById(i);

                switch (i){
                    case R.id.poin100_saldo:
                        jumlahsaldo = "Rp. 10.000";
                        break;
                    case R.id.poin500_saldo:
                        jumlahsaldo = "Rp. 50.000";
                        break;
                    case R.id.poin1000_saldo:
                        jumlahsaldo = "Rp. 100.000";
                        break;
                }

            }
        });

        tukarsaldo=findViewById(R.id.poin_saldo);
        tukarsaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TukarSaldoActivity.this, "Point berhasil ditukar menjadi Saldo dengan nominal :" + jumlahsaldo, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
