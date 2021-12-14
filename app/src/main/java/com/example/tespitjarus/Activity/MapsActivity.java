package com.example.tespitjarus.Activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tespitjarus.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.ib_search_map)
    ImageButton ibSearchMap;
    @BindView(R.id.fab_target)
    FloatingActionButton fabTarget;
    @BindView(R.id.ib_tagging_location)
    ImageButton ibTaggingLocation;

    //Tes PitJarus Arief Aly Arrosyid
    private int GPS_REQUEST_CODE = 1;
    GoogleMap gMap;
    private Circle mCircle;
    LatLng lokasi;
    private LocationManager locationManager;
    String provider, check;
    int isTagging, isCapture, checkCardView;
    Bitmap bitmap;


    private FusedLocationProviderClient providerClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        isTagging = getIntent().getIntExtra("checkTagging", 0);
        isCapture = getIntent().getIntExtra("checkPicture", 0);
        bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        checkCardView = getIntent().getIntExtra("isSelected", 0);
        check = getIntent().getStringExtra("isKlik");

        map();
        providerClient = LocationServices.getFusedLocationProviderClient(this);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    SeacrhGeoLocation();
                    return true;
                }
                return false;
            }
        });
    }

    private void map() {
        if (isGPSenable()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnable) {
            return true;
        } else {
            Toast.makeText(this, "Perlu Menggunakan GPS", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_REQUEST_CODE);
        }
        return false;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        //MarkerMap
        gMap = googleMap;
        lokasi = new LatLng(-6.3122517, 106.9544633);
        LatLng lokasiSaya = new LatLng(location.getLatitude(), location.getLongitude());
        gMap.addMarker(new MarkerOptions().position(lokasi).title("Toko Cahaya"));
        float zoomLevel = 18.0f;

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiSaya, zoomLevel));
        drawMarkerWithCircle(lokasi);
        gMap.setMyLocationEnabled(true);
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //chek radius jarak
        gMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(@NonNull Location location) {

                //mengecek radius jarak ke lokasi
                float[] lokasiSaya = new float[2];

                Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                        mCircle.getCenter().latitude, mCircle.getCenter().longitude, lokasiSaya);

                if (lokasiSaya[0] > mCircle.getRadius()) {
//                    Toast.makeText(MapsActivity.this, "Jarak Terlalu Jauh Dari Lokasi: "
//                            + lokasiSaya[0] + " radius: "
//                            + mCircle.getRadius(),
//                            Toast.LENGTH_SHORT).show();

                    ibTaggingLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taggingLokasi(lokasiSaya[0]);
                        }
                    });
                } else {
//                    Toast.makeText(MapsActivity.this, "Anda Berada Lokasi Silakan Visit: "
//                                    + lokasiSaya[0] + " radius: "
//                                    + mCircle.getRadius(),
//                            Toast.LENGTH_SHORT).show();

                    ibTaggingLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            taggingLokasi(lokasiSaya[0]);
                        }
                    });
                }
            }
        });
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        providerClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                goToLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void drawMarkerWithCircle(LatLng position) {
        double radiusInMeters = 100.0;
        int strokeColor = 0xFF009DFF; //red outline
        int shadeColor = 0xFFE6F5FF; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position)
                .radius(radiusInMeters).fillColor(shadeColor)
                .strokeColor(strokeColor).strokeWidth(2);
        mCircle = gMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        gMap.addMarker(markerOptions);
    }


    private void goToLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        gMap.moveCamera(cameraUpdate);
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        float zoomLevel = 18.0f;
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnable) {
                Toast.makeText(this, "Gps Sudah Aktif", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Gps Belum Aktif", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SeacrhGeoLocation() {
        String lokasi = edtSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> listAlamat = geocoder.getFromLocationName(lokasi, 1);
            if (listAlamat.size() > 0) {
                Address alamat = listAlamat.get(0);
                goToLocation(alamat.getLatitude(), alamat.getLongitude());
                gMap.addMarker(new MarkerOptions().position(new LatLng(alamat.getLatitude(),
                        alamat.getLongitude())));
                hideSoftKeyboard(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @SuppressLint("MissingPermission")
    private void taggingLokasi(float jarak) {

        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        int jarakLokasiSaya = (int) jarak;


        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            Intent intent = new Intent(this, VisitActivity.class);
            intent.putExtra("alamat", addresses.get(0).getAddressLine(0));
            intent.putExtra("jarak", jarakLokasiSaya);
            intent.putExtra("checkTagging", isTagging = 1);
            intent.putExtra("checkPicture", isCapture);
            intent.putExtra("BitmapImage", bitmap);
            intent.putExtra("isSelected", checkCardView);
            intent.putExtra("isKlik", check);
            intent.putExtra("toVisit", true);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.fab_target, R.id.ib_search_map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_target:
                getLocation();
                break;
            case R.id.ib_search_map:
                SeacrhGeoLocation();
                break;
        }
    }

}
