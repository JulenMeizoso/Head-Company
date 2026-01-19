package com.example.headcompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.headcompany.api.ApiClient;
import com.example.headcompany.databinding.ActivityMapsBinding;
import com.example.headcompany.model.CameraResponse;
import com.example.headcompany.model.Incidence;
import com.example.headcompany.model.IncidenceResponse;
import com.example.headcompany.model.UsersResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    // SWITCHES
    private boolean favSwitch;
    private boolean accidentesSwitch;
    private boolean obrasSwitch;
    private boolean obstaculosSwitch;
    private boolean traficoSwitch;
    private boolean eventosSwitch;
    private boolean meteorologiaSwitch;
    private boolean otrosSwitch;
    private boolean camarasSwitch;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        favSwitch = false;

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        binding.fav.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.jiggleDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.fav.setOnClickListener(v -> {
            favSwitch = !favSwitch;
            binding.fav.setImageResource(
                    favSwitch ? R.drawable.fav_on : R.drawable.fav_off
            );
        });

        binding.profile.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.jiggleDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Intent gotoProfileActivity = new Intent(MapsActivity.this, ProfileActivity.class);
                    startActivity(gotoProfileActivity);
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.menu.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.jiggleDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                    AnimUtils.jiggleUp(v);
                    AnimUtils.slideEaseInOut(binding.menuLeft);
                    break;
            }
            return false;
        });

        binding.accidentes.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.accidentes.setOnClickListener(v -> {
            accidentesSwitch = !accidentesSwitch;
            if (accidentesSwitch) {
                binding.accidentes.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#FB2C36")));
                binding.accidentes.setTextColor(ColorStateList.valueOf(Color.parseColor("#FB2C36")));
            } else {
                binding.accidentes.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.accidentes.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.obras.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.obras.setOnClickListener(v -> {
            obrasSwitch = !obrasSwitch;
            if (obrasSwitch) {
                binding.obras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#FF6900")));
                binding.obras.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6900")));
            } else {
                binding.obras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.obras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.obstaculos.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.obstaculos.setOnClickListener(v -> {
            obstaculosSwitch = !obstaculosSwitch;
            if (obstaculosSwitch) {
                binding.obstaculos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#FF6900")));
                binding.obstaculos.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF6900")));
            } else {
                binding.obstaculos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.obstaculos.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.trafico.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.trafico.setOnClickListener(v -> {
            traficoSwitch = !traficoSwitch;
            if (traficoSwitch) {
                binding.trafico.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
                binding.trafico.setTextColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
            } else {
                binding.trafico.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.trafico.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.eventos.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.eventos.setOnClickListener(v -> {
            eventosSwitch = !eventosSwitch;
            if (eventosSwitch) {
                binding.eventos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
                binding.eventos.setTextColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
            } else {
                binding.eventos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.eventos.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.meteorologia.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.meteorologia.setOnClickListener(v -> {
            meteorologiaSwitch = !meteorologiaSwitch;
            if (meteorologiaSwitch) {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#F0B100")));
            } else {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.otros.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.otros.setOnClickListener(v -> {
            otrosSwitch = !otrosSwitch;
            if (otrosSwitch) {
                binding.otros.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#AD46FF")));
                binding.otros.setTextColor(ColorStateList.valueOf(Color.parseColor("#AD46FF")));
            } else {
                binding.otros.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.otros.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.camaras.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    break;
            }
            return false;
        });

        binding.camaras.setOnClickListener(v -> {
            camarasSwitch = !camarasSwitch;
            if (camarasSwitch) {
                binding.camaras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#2B7FFF")));
                binding.camaras.setTextColor(ColorStateList.valueOf(Color.parseColor("#2B7FFF")));
            } else {
                binding.camaras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.camaras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.cleanup.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.bruhsoff(v);
                    break;
            }
            return false;
        });


        binding.cleanup.setOnClickListener(v -> {

            if (accidentesSwitch) {
                binding.accidentes.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.accidentes.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.accidentes);
                accidentesSwitch = false;
            }

            if (obrasSwitch) {
                binding.obras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.obras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.obras);
                obrasSwitch = false;
            }

            if (obstaculosSwitch) {
                binding.obstaculos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.obstaculos.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.obstaculos);
                obstaculosSwitch = false;
            }

            if (traficoSwitch) {
                binding.trafico.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.trafico.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.trafico);
                traficoSwitch = false;
            }

            if (eventosSwitch) {
                binding.eventos.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.eventos.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.eventos);
                eventosSwitch = false;
            }

            if (meteorologiaSwitch) {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.meteorologia);
                meteorologiaSwitch = false;
            }

            if (otrosSwitch) {
                binding.otros.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.otros.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.otros);
                otrosSwitch = false;
            }

            if (camarasSwitch) {
                binding.camaras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.camaras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.camaras);
                camarasSwitch = false;
            }
        });

    }







    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fetchIncidencesPage(1);

    }

    private void fetchIncidencesPage(final int page) {
        ApiClient.getApiService().getIncidences(page).enqueue(new Callback<IncidenceResponse>() {
            @Override
            public void onResponse(Call<IncidenceResponse> call, Response<IncidenceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    IncidenceResponse incidenceResponse = response.body();
                    List<Incidence> incidencias = incidenceResponse.getItems();

                    for (Incidence incidence : incidencias) {
                        LatLng position = new LatLng(incidence.getLatitude(), incidence.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(position));
                    }

                    if (incidenceResponse.getCurrentPage() < incidenceResponse.getTotalPages()) {
                        fetchIncidencesPage(page + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<IncidenceResponse> call, Throwable t) {
                Log.e("API_ERROR", "Request failed on page " + page, t);
            }
        });
    }
}