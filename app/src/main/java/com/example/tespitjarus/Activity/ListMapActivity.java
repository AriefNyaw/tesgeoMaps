package com.example.tespitjarus.Activity;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.tespitjarus.Adapter.ListLocationAdapter;
import com.example.tespitjarus.Model.Room.Toko;
import com.example.tespitjarus.Model.Room.TokoDb;
import com.example.tespitjarus.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    TokoDb tokoDatabase;
    ArrayList<Toko> listToko;
    ListLocationAdapter locationAdapter;
    @BindView(R.id.rv_list_location)
    RecyclerView rvListLocation;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.ib_search_map)
    ImageButton ibSearchMap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_map);
        ButterKnife.bind(this);

        listToko = new ArrayList<>();
        tokoDatabase = Room.databaseBuilder(getApplicationContext(), TokoDb.class, "toko")
                .allowMainThreadQueries().build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        listToko.addAll(Arrays.asList(tokoDatabase.dao().readDataToko()));
        locationAdapter = new ListLocationAdapter(listToko, this);
        rvListLocation.setLayoutManager(new LinearLayoutManager(this));
        rvListLocation.setAdapter(locationAdapter);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    geoLocation();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.ib_search_map)
    public void onClick() {
        geoLocation();
    }

    private void goToLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        gMap.moveCamera(cameraUpdate);
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        //MarkerMap
        gMap = googleMap;
        LatLng TokoA = new LatLng(-6.3122517, 106.9544633);
        gMap.addMarker(new MarkerOptions().position(TokoA).title("Toko Cahaya"));
        float zoomLevel = 16.0f;
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TokoA, zoomLevel));
        drawMarkerWithCircle(TokoA);
    }


    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 100.0;
        int strokeColor = 0xFF009DFF ; //red outline
        int shadeColor = 0xFFE6F5FF; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position)
                .radius(radiusInMeters).fillColor(shadeColor)
                .strokeColor(strokeColor).strokeWidth(2);
        gMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        gMap.addMarker(markerOptions);
    }

    private void geoLocation() {
        String lokasi = edtSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> listAlamat = geocoder.getFromLocationName(lokasi, 1);
            if (listAlamat.size() > 0){
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
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}