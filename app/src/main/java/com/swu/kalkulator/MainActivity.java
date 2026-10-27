package com.swu.kalkulator;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editNum1;
    private EditText editNum2;

    private Button buttonPlus;
    private Button buttonMin;
    private Button buttonMulti;
    private Button buttonDiv;
    private Button buttonCalc;
    private Button buttonClear;

    private TextView textResult;

    private String operasi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNum1 = findViewById(R.id.editNum1);
        editNum2 = findViewById(R.id.editNum2);

        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMin = findViewById(R.id.buttonMin);
        buttonMulti = findViewById(R.id.buttonMulti);
        buttonDiv = findViewById(R.id.buttonDiv);

        buttonCalc = findViewById(R.id.buttonCalc);
        buttonClear = findViewById(R.id.buttonClear);

        textResult = findViewById(R.id.textResult);

        buttonPlus.setOnClickListener(v -> pilihOperasi("+"));
        buttonMin.setOnClickListener(v -> pilihOperasi("-"));
        buttonMulti.setOnClickListener(v -> pilihOperasi("*"));
        buttonDiv.setOnClickListener(v -> pilihOperasi("/"));

        buttonCalc.setOnClickListener(v -> hitung());
        buttonClear.setOnClickListener(v -> clear());
    }

    private void pilihOperasi(String operator) {
        operasi = operator;

        Toast.makeText(
                this,
                "Operasi " + operator + " dipilih",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void hitung() {
        String angka1 = editNum1.getText().toString().trim();
        String angka2 = editNum2.getText().toString().trim();

        if (angka1.isEmpty() || angka2.isEmpty()) {
            Toast.makeText(
                    this,
                    "Masukkan kedua angka terlebih dahulu",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (operasi.isEmpty()) {
            Toast.makeText(
                    this,
                    "Pilih operasi terlebih dahulu",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(angka1.replace(',', '.'));
            double num2 = Double.parseDouble(angka2.replace(',', '.'));
            double hasil;

            switch (operasi) {
                case "+":
                    hasil = num1 + num2;
                    break;

                case "-":
                    hasil = num1 - num2;
                    break;

                case "*":
                    hasil = num1 * num2;
                    break;

                case "/":
                    if (num2 == 0) {
                        Toast.makeText(
                                this,
                                "Tidak dapat membagi dengan 0",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    hasil = num1 / num2;
                    break;

                default:
                    Toast.makeText(
                            this,
                            "Pilih operasi terlebih dahulu",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
            }

            if (Double.isNaN(hasil) || Double.isInfinite(hasil)) {
                Toast.makeText(
                        this,
                        "Hasil perhitungan tidak valid",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            String hasilTeks = formatHasil(hasil);
            textResult.setText("Hasil = " + hasilTeks);

            String perhitungan = angka1 + " " + operasi + " " + angka2;

            // Simpan perhitungan ke riwayat.
            Riwayat.daftarRiwayat.add(
                    0,
                    new Riwayat(perhitungan, hasilTeks)
            );

            // Jalankan fitur sesuai hasil kalkulator.
            jalankanFiturTersembunyi(hasil);

        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    "Angka yang dimasukkan tidak valid",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void jalankanFiturTersembunyi(double hasil) {
        if (hasil == 10.0) {


            Intent intentKamera = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE
            );

            bukaIntent(intentKamera, "Kamera tidak tersedia");

        } else if (hasil == 9.0) {

            // Hasil 4: buka halaman gambar.
            Intent intentGambar = new Intent(
                    MainActivity.this,
                    ImageActivity.class
            );

            startActivity(intentGambar);

        } else if (hasil == 5.0) {

            // Hasil 5: buka daftar download.
            Intent intentDownload = new Intent(
                    DownloadManager.ACTION_VIEW_DOWNLOADS
            );

            bukaIntent(
                    intentDownload,
                    "Halaman download tidak tersedia"
            );

        } else if (hasil == 3.0) {

            // Hasil 7: buka pemutar musik.
            Intent intentMusik = new Intent(
                    MainActivity.this,
                    MusicActivity.class
            );

            startActivity(intentMusik);

        } else if (hasil == 28.0) {

            // Hasil 9: buka halaman sensor.
            Intent intentSensor = new Intent(
                    MainActivity.this,
                    SensorActivity.class
            );

            startActivity(intentSensor);

        } else if (hasil == 18.0) {

            // Hasil 15: buka Google Maps.
            bukaPeta();
        }
    }

    private void bukaIntent(Intent intent, String pesanError) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    pesanError,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private String formatHasil(double hasil) {
        // Hilangkan .0 jika hasilnya bilangan bulat.
        if (hasil == Math.rint(hasil)
                && Math.abs(hasil) < 1.0E15) {

            return String.valueOf((long) hasil);
        }

        DecimalFormatSymbols simbol =
                DecimalFormatSymbols.getInstance(Locale.US);

        DecimalFormat format =
                new DecimalFormat("0.##########", simbol);

        return format.format(hasil);
    }

    private void bukaPeta() {
        Uri uriMaps = Uri.parse(
                "https://maps.google.com/?q=lokasi"
        );

        Intent intentMaps = new Intent(
                Intent.ACTION_VIEW,
                uriMaps
        );

        try {
            startActivity(intentMaps);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    "Tidak ada aplikasi untuk membuka peta",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void clear() {
        editNum1.setText("");
        editNum2.setText("");
        textResult.setText("Hasil");

        operasi = "";

        editNum1.requestFocus();
    }
}