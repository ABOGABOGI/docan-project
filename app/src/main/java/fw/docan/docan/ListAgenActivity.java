package fw.docan.docan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import fw.docan.docan.adapter.AgenAdapter;
import fw.docan.docan.model.ListAgen;

public class ListAgenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AgenAdapter adapter;
    private ArrayList<ListAgen> agenArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agen);

        addData();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AgenAdapter(agenArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListAgenActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void addData(){
        agenArrayList = new ArrayList<>();
        agenArrayList.add(new ListAgen("Joni Aja","100.000","1.000.000"));
        agenArrayList.add(new ListAgen("Guntur lu","200.000","2.000.000"));
        agenArrayList.add(new ListAgen("Hafi aja","300.000","3.000.000"));
        agenArrayList.add(new ListAgen("Galuh coy","400.000","4.000.000"));
        agenArrayList.add(new ListAgen("Rafli moo","500.000","5.000.000"));
        agenArrayList.add(new ListAgen("Keane joss","600.000","6.000.000"));
        agenArrayList.add(new ListAgen("Dunn Kin","700.000","7.000.000"));
    }
}
