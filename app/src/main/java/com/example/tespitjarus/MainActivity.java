package com.example.tespitjarus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.tespitjarus.Activity.ListMapActivity;
import com.example.tespitjarus.Activity.LoginActivity;
import com.example.tespitjarus.Model.Room.Toko;
import com.example.tespitjarus.Model.Room.TokoDb;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    TokoDb tokoDatabase;
    ArrayList<Toko> daftarToko;

    @BindView(R.id.ln_kunjungan)
    LinearLayout lnKunjungan;
    @BindView(R.id.ln_logout)
    LinearLayout lnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Tes PitJarus Arief Aly Arrosyid

    }

    @OnClick({R.id.ln_kunjungan, R.id.ln_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_kunjungan:
                startActivity(new Intent(this, ListMapActivity.class));
                break;
            case R.id.ln_logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}