package com.example.tespitjarus.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.tespitjarus.MainActivity;
import com.example.tespitjarus.Model.ResponseLogin;
import com.example.tespitjarus.Model.Room.TokoDb;
import com.example.tespitjarus.Model.StoresItem;
import com.example.tespitjarus.Model.Room.Toko;
import com.example.tespitjarus.R;
import com.example.tespitjarus.Service.ConfigApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    String Username, Password;
    TokoDb tokoDatabase;
    private Object PlacesClient;
    boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    //Tes PitJarus Arief Aly Arrosyid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        edtPasswordAction();

        tokoDatabase = Room.databaseBuilder(getApplicationContext(),
                TokoDb.class, "toko").build();

        //permission location
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        PlacesClient = Places.createClient(this);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        Username = edtUsername.getText().toString();
        Password = edtPassword.getText().toString();

        if (Username.isEmpty()){
            edtUsername.setError("Username Tidak Boleh Kosong");
            edtUsername.requestFocus();
            return;
        }

        if (Password.isEmpty()){
            edtPassword.setError("Password Tidak Boleh Kosong");
            edtPassword.requestFocus();
            return;
        }

        ConfigApi.service.login(Username, Password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    String status = response.body().getStatus();
                    if (status.equals("success") || status.equals("Success")){

                        String storeId, storeCode, storeName, address, dcId, dcName, accountId,
                                accountName, subChannelId, subChannelName, channelId, channelName,
                                areaId, areaName, regionId, regionName, latitude, longitude;

                        List<StoresItem> storesItems = response.body().getStores();
                        for (int i = 0; i < storesItems.size(); i++){


                            Log.d("cek", "onResponse: " + i);
                            //insert data
                            storeId = storesItems.get(i).getStoreId();
                            storeCode = storesItems.get(i).getStoreCode();
                            storeName = storesItems.get(i).getStoreName();
                            address = storesItems.get(i).getAddress();
                            dcId = storesItems.get(i).getDcId();
                            dcName = storesItems.get(i).getDcName();
                            accountId = storesItems.get(i).getAccountId();
                            accountName = storesItems.get(i).getAccountName();
                            subChannelId = storesItems.get(i).getSubchannelId();
                            subChannelName = storesItems.get(i).getSubchannelName();
                            channelId = storesItems.get(i).getChannelId();
                            channelName = storesItems.get(i).getChannelName();
                            areaId = storesItems.get(i).getAreaId();
                            areaName = storesItems.get(i).getAreaName();
                            regionId = storesItems.get(i).getRegionId();
                            latitude = storesItems.get(i).getLatitude();
                            longitude = storesItems.get(i).getLongitude();
                            regionName = storesItems.get(i).getRegionName();

                            Toko data = new Toko();
                            data.setStore_id(String.valueOf(i));
                            data.setStore_code(storeCode);
                            data.setStore_name(storeName);
                            data.setAdrress(address);
                            data.setDc_id(dcId);
                            data.setDc_name(dcName);
                            data.setAccount_id(accountId);
                            data.setAccount_name(accountName);
                            data.setSubchannel_id(subChannelId);
                            data.setSubchannel_name(subChannelName);
                            data.setChannel_id(channelId);
                            data.setChannel_name(channelName);
                            data.setArea_id(areaId);
                            data.setArea_name(areaName);
                            data.setRegion_id(regionId);
                            data.setLatitude(latitude);
                            data.setLogintude(longitude);
                            data.setRegion_name(regionName);

                            insertData(data);

                        }

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(LoginActivity.this, "Login Berhasil",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Gagal Login",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Periksa Kembali Koneksi...",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void insertData(Toko toko) {
        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                return tokoDatabase.dao().insertToko(toko);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }
        }.execute();
    }

    private void edtPasswordAction(){
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(LoginActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        locationPermissionGranted = false;
        switch (requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }else{
                    finish();
                }
            }
        }
    }

    private void getLocationPermission(){

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true;
        }else {
            ActivityCompat.requestPermissions(this,
                    new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}