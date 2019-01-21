package fw.docan.docan.kwitansi;

/**
 * Created by wolfsoft1 on 24/3/18.
 */

public class ModelClass {
    String iteamName,quantity,price;

    public ModelClass(String iteamName, String quantity, String price) {
        this.iteamName = iteamName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getIteamName() {
        return iteamName;
    }

    public void setIteamName(String iteamName) {
        this.iteamName = iteamName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
