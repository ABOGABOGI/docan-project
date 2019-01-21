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

public class Pembelian_4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spin;
    Button token;
    EditText rek_debit,nama,no_meter;
    String rek,nama_s,no,layanan;

    private String[] layanan_token_listrik = {
            "Beli Token Listrik"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian_4);

        rek_debit = findViewById(R.id.no_rek);
        nama = findViewById(R.id.nama_singkat);
        no_meter = findViewById(R.id.no_meter);
        spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, layanan_token_listrik);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // mengeset Array Adapter tersebut ke Spinner
        spin.setAdapter(adapter);

        token = findViewById(R.id.token);
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rek = rek_debit.getText().toString();
                nama_s = nama.getText().toString();
                no = no_meter.getText().toString();

                Toast.makeText(Pembelian_4.this,rek+" "+nama_s+" "+no+" "+layanan,Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long id) {
        switch (i) {
            case 0:
                layanan = "Beli Token Listrik";
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
