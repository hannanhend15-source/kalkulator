package com.swu.kalkulator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvRiwayat;
    private TextView textRiwayatKosong;
    private RiwayatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvRiwayat = findViewById(R.id.rv_riwayat);
        textRiwayatKosong = findViewById(R.id.textRiwayatKosong);
        Button buttonKembali = findViewById(R.id.buttonKembali);

        rvRiwayat.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new RiwayatAdapter(Riwayat.daftarRiwayat);
        rvRiwayat.setAdapter(adapter);

        tampilkanRiwayat();

        buttonKembali.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
            tampilkanRiwayat();
        }
    }

    private void tampilkanRiwayat() {
        boolean kosong = Riwayat.daftarRiwayat.isEmpty();

        textRiwayatKosong.setVisibility(
                kosong ? View.VISIBLE : View.GONE);

        rvRiwayat.setVisibility(
                kosong ? View.GONE : View.VISIBLE);
    }
}
