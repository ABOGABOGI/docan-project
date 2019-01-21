package fw.docan.docan;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by apple on 15/03/16.
 */
public class Beanlist {


    private Drawable image;
    private String amount;
    private String billname;
    private String date;



    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Beanlist(Drawable image, String amount, String billname, String date) {

        this.image = image;
        this.amount = amount;
        this.billname = billname;
        this.date = date;

    }
}

