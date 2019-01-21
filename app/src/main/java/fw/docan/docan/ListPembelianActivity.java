package fw.docan.docan;

import android.support.v7.app.AppCompatActivity;

public class ListPembelianActivity extends AppCompatActivity {
    private String name;

    public ListPembelianActivity(){
        super();
    }
    public ListPembelianActivity(String name){
        super();
        this.name = name;
    }
    public String toString(){
        return this.name;
    }
}
