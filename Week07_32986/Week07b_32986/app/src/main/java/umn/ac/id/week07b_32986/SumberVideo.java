package umn.ac.id.week07b_32986;

import java.io.Serializable;

public class SumberVideo implements Serializable {
    private String strJudul;
    private String strKeterangan;
    private String strVideoURI;
    public SumberVideo(String judul, String keterangan,
                       String videoURI){
        this.strJudul = judul;
        this.strKeterangan = keterangan;
        this.strVideoURI = videoURI;
    }
    public String getJudul() { return this.strJudul; }
    public String getKeterangan() { return this.strKeterangan; }
    public String getVideoURI() { return this.strVideoURI; }
    public void setJudul(String judul){ this.strJudul = judul; }
    public void setKeterangan(String keterangan) {
        this.strKeterangan = keterangan;
    }
    public void setVideoURI(String videoURI) {
        this.strVideoURI = videoURI;
    }
    public String toString() { return this.getJudul() + " => "
            + this.getKeterangan(); }
}
