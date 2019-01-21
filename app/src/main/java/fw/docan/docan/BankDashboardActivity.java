package fw.docan.docan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BankDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_dashboard);

        Button btnDashboard = (Button)findViewById(R.id.btnSetor);
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankDashboardActivity.this, BankSetorActivity.class));
            }
        });

        Button btnSetor = findViewById(R.id.btnSetor2);
        btnSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BankDashboardActivity.this,BankSetorDocan.class));
            }
        });
    }
}
