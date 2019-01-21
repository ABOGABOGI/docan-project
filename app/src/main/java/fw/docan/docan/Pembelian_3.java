package fw.docan.docan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Pembelian_3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spin;
    Button unikqu;
    EditText rek_debit,nohp,nominal;
    String rek,hp,nom,layanan;

    private String[] layanan_unikqu = {
            "Top Up UnikQu"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian_3);

        rek_debit = findViewById(R.id.no_rek);
        nohp = findViewById(R.id.nama_singkat);
        nominal = findViewById(R.id.no_meter);
        spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, layanan_unikqu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // mengeset Array Adapter tersebut ke Spinner
        spin.setAdapter(adapter);

        unikqu = findViewById(R.id.topup_unikqu);
        unikqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rek = rek_debit.getText().toString();
                hp = nohp.getText().toString();
                nom = nominal.getText().toString();

                Toast.makeText(Pembelian_3.this,rek+" "+hp+" "+nom+" "+layanan,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long id) {
        switch (i) {
            case 0:
                layanan = "Top Up UnikQu";
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
