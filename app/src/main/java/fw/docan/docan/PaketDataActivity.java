package fw.docan.docan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

public class PaketDataActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener{
    private Spinner operator_paketdata;
    Button tukar;
    ImageView logo;
    public String nama,jenis_paket;
    public int no_paket;
    private TextInputLayout text;
    EditText paketdata;
    private String[] operatorpulsa = {
            "XL", "Telkomsel", "Three", "Smartfren", "Indosat", "Axis"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paketdata);

        operator_paketdata = findViewById(R.id.operator_paketdata);
        operator_paketdata.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, operatorpulsa);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // mengeset Array Adapter tersebut ke Spinner
        operator_paketdata.setAdapter(adapter);

        tukar = findViewById(R.id.tukar_paketdata);
        tukar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = findViewById(R.id.no_paket);
                no_paket = Integer.parseInt(text.getEditText().getText().toString()); //notelp
                Toast.makeText(PaketDataActivity.this, "Paket Data berhasil dibeli ke : " + no_paket + " Operator : " + nama + " Jenis Paket Data :" + jenis_paket, Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = findViewById(R.id.popup_paketdata);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(PaketDataActivity.this,view);
                popup.setOnMenuItemClickListener(PaketDataActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        jenis_paket = item.getTitle().toString();
        paketdata = findViewById(R.id.paket_data);
        paketdata.setText(jenis_paket);
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.inet1gb:
                // do your code
                return true;
            case R.id.inet3gb:
                // do your code
                return true;
            case R.id.inet5gb:
                // do your code
                return true;
            case R.id.inet10gb:
                // do your code
                return true;
            default:
                return false;
        }
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long id) {
        logo = findViewById(R.id.logo_operator);
        nama = arg0.getItemAtPosition(i).toString();
        switch (i) {
            case 0:
                logo.setImageResource(R.drawable.xl);
                break;
            case 1:
                logo.setImageResource(R.drawable.telkomsel);
                break;
            case 2:
                logo.setImageResource(R.drawable.threee);
                break;
            case 3:
                logo.setImageResource(R.drawable.smartfren);
                break;
            case 4:
                logo.setImageResource(R.drawable.indosat);
                break;
            case 5:
                logo.setImageResource(R.drawable.axis);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}
