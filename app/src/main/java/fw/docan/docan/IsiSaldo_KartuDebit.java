package fw.docan.docan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class IsiSaldo_KartuDebit extends FragmentActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spin;
    static EditText DateEdit;
    EditText no,masa,cvv;
    String nomor,date,CVV,print_saldo;
    int saldo;
    private String[] nominal = {
            "Rp100.000", "Rp200.000", "Rp500.000"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_debit);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nominal);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // mengeset Array Adapter tersebut ke Spinner
        spin = findViewById(R.id.spinner_debit);
        spin.setOnItemSelectedListener(this);
        spin.setAdapter(adapter);

        DateEdit = findViewById(R.id.masa_berlaku);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });

        no = findViewById(R.id.no_kartu);
        masa = findViewById(R.id.masa_berlaku);
        cvv = findViewById(R.id.CVV);

        Button btn = findViewById(R.id.topup_debit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomor = no.getText().toString();
                CVV = cvv.getText().toString();
                Toast.makeText(IsiSaldo_KartuDebit.this, print_saldo+" "+nomor+" "+CVV, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long id) {
        switch (i) {
            case 0:
                saldo = 100000;
                print_saldo = Integer.toString(saldo);
                break;
            case 1:
                saldo = 200000;
                print_saldo = Integer.toString(saldo);
                break;
            case 2:
                saldo = 500000;
                print_saldo = Integer.toString(saldo);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateEdit.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

}
