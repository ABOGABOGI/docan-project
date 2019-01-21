package fw.docan.docan.model;

public class RiwayatTransaksi {
    private int idTransaksi;
    private int iconTransaksi;
    private String judulTransaksi;
    private String waktuTransaksi;
    private String jumlahTransaksi;

    public RiwayatTransaksi(int idTransaksi, int iconTransaksi, String judulTransaksi, String waktuTransaksi, String jumlahTransaksi) {
        this.idTransaksi = idTransaksi;
        this.iconTransaksi = iconTransaksi;
        this.judulTransaksi = judulTransaksi;
        this.waktuTransaksi = waktuTransaksi;
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public RiwayatTransaksi(int iconTransaksi, String judulTransaksi, String waktuTransaksi, String jumlahTransaksi) {
        this.iconTransaksi = iconTransaksi;
        this.judulTransaksi = judulTransaksi;
        this.waktuTransaksi = waktuTransaksi;
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public RiwayatTransaksi() {
    }

    public int getIconTransaksi() {
        return iconTransaksi;
    }

    public void setIconTransaksi(int iconTransaksi) {
        this.iconTransaksi = iconTransaksi;
    }

    public String getJudulTransaksi() {
        return judulTransaksi;
    }

    public void setJudulTransaksi(String judulTransaksi) {
        this.judulTransaksi = judulTransaksi;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(String jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
}
