package fw.docan.docan;

import android.support.v7.app.AppCompatActivity;

public class ListBantuanActivity extends AppCompatActivity {
    private String name;

    public ListBantuanActivity(){
        super();
    }
    public ListBantuanActivity(String name){
        super();
        this.name = name;
    }
    public String toString(){
        return this.name;
    }
}
