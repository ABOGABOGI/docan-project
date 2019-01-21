package fw.docan.docan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BankSetorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_setor);
        Button btnDashboard = (Button)findViewById(R.id.btnSetor);
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankSetorActivity.this, BankResultActivity.class));
            }
        });

        User user = SharedPrefManager.getInstance(this).getUser();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            TextView txtvw = (TextView) findViewById(R.id.txtSaldoDocan);
            txtvw.setText(String.format("%,d", Long.parseLong(user.getBalance())));

        }
    }
}
