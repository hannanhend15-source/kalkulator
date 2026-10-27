package com.swu.kalkulator;

import java.util.ArrayList;

public class Riwayat {

    private String perhitungan;
    private String hasil;

    public static ArrayList<Riwayat> daftarRiwayat =
            new ArrayList<>();

    public Riwayat(String perhitungan, String hasil) {
        this.perhitungan = perhitungan;
        this.hasil = hasil;
    }

    public String getPerhitungan() {
        return perhitungan;
    }

    public String getHasil() {
        return hasil;
    }
}
