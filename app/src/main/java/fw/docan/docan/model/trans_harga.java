package fw.docan.docan.model;

import java.util.Comparator;

public class trans_harga implements Comparable<trans_harga>{
    private int id,harga;

    public static final Comparator<trans_harga> ASCENDING_COMPARATOR = new Comparator<trans_harga>() {
        // Overriding the compare method to sort the age
        public int compare(trans_harga d, trans_harga d1) {
            return ((Integer)d1.harga).compareTo(d.harga);
        }
    };

    public trans_harga(int id, int harga) {
        this.id = id;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public int getPrice(){
        return harga;
    }

    // Overriding the compareTo method
    public int compareTo(trans_harga d) {
        return ((Integer)this.harga).compareTo(d.harga);
    }
}
