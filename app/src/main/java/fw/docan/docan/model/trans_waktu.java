package fw.docan.docan.model;

import java.util.Comparator;

public class trans_waktu implements Comparable<trans_waktu>{
    private int id;
    private long waktu;

    public static final Comparator<trans_waktu> ASCENDING_COMPARATOR = new Comparator<trans_waktu>() {
        // Overriding the compare method to sort the age
        public int compare(trans_waktu d, trans_waktu d1) {
            return ((Long)d1.waktu).compareTo(d.waktu);
        }
    };

    public trans_waktu(int id, long waktu) {
        this.id = id;
        this.waktu = waktu;
    }

    public int getId() {
        return id;
    }

    public long getTime(){
        return waktu;
    }

    // Overriding the compareTo method
    public int compareTo(trans_waktu d) {
        return ((Long)this.waktu).compareTo(d.waktu);
    }
}