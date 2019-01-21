package fw.docan.docan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class VendorPulsaActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner operator;
    public String nama, nominal;
    public int no;
    ImageView logo;
    RadioGroup radiopoin;
    RadioButton btn;
    Button tukar;
    private TextInputLayout textinput;

    private String[] operatorpulsa = {
            "XL", "Telkomsel", "Three", "Smartfren", "Indosat", "Axis"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukarpulsa);

        operator = findViewById(R.id.operator);
        operator.setOnItemSelectedListener(this);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, operatorpulsa);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // mengeset Array Adapter tersebut ke Spinner
        operator.setAdapter(adapter);


        radiopoin = findViewById(R.id.radiopoin);

        radiopoin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int i) {
                RadioButton rbtn = (RadioButton) radiopoin.findViewById(i);

                switch (i){
                    case R.id.poin100_pulsa:
                        nominal = "Rp. 10.000";
                        break;
                    case R.id.poin500_pulsa:
                        nominal = "Rp. 50.000";
                        break;
                    case R.id.poin1000_pulsa:
                        nominal = "Rp. 100.000";
                        break;
                }

            }
        });


        tukar = findViewById(R.id.Tukar);
        tukar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textinput = findViewById(R.id.layout_no);
                no = Integer.parseInt(textinput.getEditText().getText().toString()); //notelp

                Toast.makeText(VendorPulsaActivity.this, "Pulsa berhasil dikirim ke : " + no + " Operator : " + nama + " Dengan nominal :" + nominal, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.poin100_pulsa:
                if (checked)
                    nominal = "Rp. 10.000";
                break;
            case R.id.poin500_pulsa:
                if (checked)
                    nominal = "Rp. 50.000";
                break;
            case R.id.poin1000_pulsa:
                if (checked)
                    nominal = "Rp. 100.000";
                break;
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