package fw.docan.docan.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by one on 22/1/18.
 */
public class Categories implements Serializable {

    private String name;
    public ArrayList<Products> productsArrayList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Products> getProductsArrayList() {
        return productsArrayList;
    }

    public void setProductsArrayList(ArrayList<Products> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }




}