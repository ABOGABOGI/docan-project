package fw.docan.docan.model;


public class ListAgen {
    private String nama;
    private String jumlahsaldo;
    private String jumlahcash;

    public ListAgen(String nama, String jumlahsaldo, String jumlahcash){
        this.nama = nama;
        this.jumlahsaldo = jumlahsaldo;
        this.jumlahcash = jumlahcash;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlahsaldo() {
        return jumlahsaldo;
    }

    public void setJumlahsaldo(String jumlahsaldo) {
        this.jumlahsaldo = jumlahsaldo;
    }

    public String getJumlahcash() {
        return jumlahcash;
    }

    public void setJumlahcash(String jumlahcash) {
        this.jumlahcash = jumlahcash;
    }



}
