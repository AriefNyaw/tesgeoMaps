package com.example.tespitjarus.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.tespitjarus.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisitActivity extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.btn_visit)
    Button btnVisit;
    @BindView(R.id.tv_alamat)
    TextView tvAlamat;

    String alamat, check;
    int jarak, isTagging = 0, Tagging, isCapture = 0, checkCardview;
    Bitmap bitmap;

    //Tes PitJarus Arief Aly Arrosyid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        ButterKnife.bind(this);

        alamat = getIntent().getStringExtra("alamat");
        jarak = getIntent().getIntExtra("jarak", 0);
        Tagging = getIntent().getIntExtra("checkTagging", 0);
        isCapture = getIntent().getIntExtra("checkPicture", 0);
        checkCardview = getIntent().getIntExtra("isSelected", 0);
        bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        check = getIntent().getStringExtra("isKlik");

        getImage();

    }

    private void cameraPermissons() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    private boolean openCamera() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Allow Permission untuk mengunakan Kamera", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isTakeImage = false;

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                isTakeImage = true;
            } else {
                isTakeImage = false;
                Toast.makeText(this, "Gambar Belum Diambil", Toast.LENGTH_SHORT).show();
            }
        }
        if (isTakeImage) {
            if (data != null) {
               bitmap = (Bitmap) data.getExtras().get("data");
                Glide.with(this).load(bitmap).into(imageView);
                isCapture = 1;
            } else {
                Toast.makeText(this, "Ambil Gambar Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getImage() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("toVisit")) {
            boolean getImage = extras.getBoolean("toVisit");
            if (getImage) {
                tvAlamat.setText(String.valueOf(alamat));
                Glide.with(this).load(bitmap).into(imageView);
            }
        }
    }

        private void validasiVisit(){

            if (isCapture == 1 && Tagging == 1) {
                if (jarak == 0 && jarak <= 100){
                    Intent intent = new Intent(this, FinishVisitActivity.class);
                    intent.putExtra("isSelected", checkCardview);
                    intent.putExtra("isKlik", check);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "Jarak Terlalu Jauh...", Toast.LENGTH_SHORT).show();
                }
            } else if (isCapture == 0 && Tagging == 0) {
                Toast.makeText(this, "Ambil Gambar Terlebih dahulu... dan Tagging Lokasi", Toast.LENGTH_SHORT).show();
            } else if (isCapture == 1 || Tagging == 0) {
                Toast.makeText(this, "Silahkan Tagging Lokasi Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            } else if (isCapture == 0 || Tagging == 1) {
                Toast.makeText(this, "Ambil Gambar Terlebih dahulu...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
            }

        }

    private void toTagging(){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("checkTagging", isTagging);
        intent.putExtra("checkPicture", isCapture);
        intent.putExtra("BitmapImage", bitmap);
        intent.putExtra("isSelected", checkCardview);
        intent.putExtra("isKlik", check);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btn_visit, R.id.ib_camera, R.id.ib_target})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_visit:
                validasiVisit();
                break;
            case R.id.ib_camera:
                cameraPermissons();
                break;
            case R.id.ib_target:
                toTagging();
                break;
        }
    }

}