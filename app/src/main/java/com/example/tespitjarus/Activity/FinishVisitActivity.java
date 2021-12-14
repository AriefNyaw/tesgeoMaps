package com.example.tespitjarus.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tespitjarus.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishVisitActivity extends AppCompatActivity {


    private int checkCardview;
    String check;

    //Tes PitJarus Arief Aly Arrosyid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_visit);
        ButterKnife.bind(this);
        checkCardview = getIntent().getIntExtra("isSelected", 0);
        check = getIntent().getStringExtra("isKlik");
    }

    @OnClick(R.id.btn_selesai)
    public void onClick() {
        Intent intent = new Intent(this, ListMapActivity.class);
        intent.putExtra("isSelected", checkCardview);
        intent.putExtra("isKlik", check);
        startActivity(intent);
        finish();
    }
}