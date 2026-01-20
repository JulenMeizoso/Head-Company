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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean meteorologiaSwitch;
    private boolean seguridadVialSwitch;
    private boolean retencionSwitch;
    private boolean vialidadInvernalSwitch;
    private boolean puertosDeMontanaSwitch;
    private boolean otrosSwitch;
    private boolean camarasSwitch;
    private Map<Marker, Incidence> markerIncidenceMap = new HashMap<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favSwitch = false;

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
                    AnimUtils.slideRight(binding.menuLeft);
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
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#3C59EA")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#3C59EA")));
            } else {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.seguridadVial.setOnTouchListener((v, event) -> {
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

        binding.seguridadVial.setOnClickListener(v -> {
            seguridadVialSwitch = !seguridadVialSwitch;
            if (seguridadVialSwitch) {
                binding.seguridadVial.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#FFB02E")));
                binding.seguridadVial.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFB02E")));
            } else {
                binding.seguridadVial.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.seguridadVial.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.retencion.setOnTouchListener((v, event) -> {
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

        binding.retencion.setOnClickListener(v -> {
            retencionSwitch = !retencionSwitch;
            if (retencionSwitch) {
                binding.retencion.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#F8312F")));
                binding.retencion.setTextColor(ColorStateList.valueOf(Color.parseColor("#F8312F")));
            } else {
                binding.retencion.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.retencion.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.vialidadInvernal.setOnTouchListener((v, event) -> {
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

        binding.vialidadInvernal.setOnClickListener(v -> {
            vialidadInvernalSwitch = !vialidadInvernalSwitch;
            if (vialidadInvernalSwitch) {
                binding.vialidadInvernal.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#01A1E6")));
                binding.vialidadInvernal.setTextColor(ColorStateList.valueOf(Color.parseColor("#01A1E6")));
            } else {
                binding.vialidadInvernal.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.vialidadInvernal.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
        });

        binding.puertosDeMontana.setOnTouchListener((v, event) -> {
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

        binding.puertosDeMontana.setOnClickListener(v -> {
            puertosDeMontanaSwitch = !puertosDeMontanaSwitch;
            if (puertosDeMontanaSwitch) {
                binding.puertosDeMontana.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#8C5543")));
                binding.puertosDeMontana.setTextColor(ColorStateList.valueOf(Color.parseColor("#8C5543")));
            } else {
                binding.puertosDeMontana.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.puertosDeMontana.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
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

            if (meteorologiaSwitch) {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.meteorologia);
                meteorologiaSwitch = false;
            }

            if (seguridadVialSwitch) {
                binding.seguridadVial.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.seguridadVial.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.seguridadVial);
                seguridadVialSwitch = false;
            }

            if (retencionSwitch) {
                binding.retencion.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.retencion.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.retencion);
                retencionSwitch = false;
            }

            if (vialidadInvernalSwitch) {
                binding.vialidadInvernal.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.vialidadInvernal.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.vialidadInvernal);
                vialidadInvernalSwitch = false;
            }

            if (puertosDeMontanaSwitch) {
                binding.puertosDeMontana.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.puertosDeMontana.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                AnimUtils.blocknod(binding.puertosDeMontana);
                puertosDeMontanaSwitch = false;
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

        binding.bottomMenu.setVisibility(View.GONE);

        binding.close.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.jiggleDown(v);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);
                    AnimUtils.slideDown(binding.bottomMenu);
                    break;
            }
            return false;
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

        LatLng spain = new LatLng(40.4168, -3.7038);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spain, 6f));


        mMap.setOnMarkerClickListener(marker -> {
            Incidence selectedIncidence = markerIncidenceMap.get(marker);
            if (selectedIncidence != null) {
                binding.incidenceType.setText(selectedIncidence.getIncidenceType());
                binding.direction.setText(selectedIncidence.getDirection());
                binding.province.setText(selectedIncidence.getProvince());
                binding.startDate.setText(selectedIncidence.getStartDate());

                switch (binding.incidenceType.getText().toString()) {
                    case "Accidente":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.accidente));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.accidente));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#40FB2C36"));
                        break;
                    case "Obras":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.obras));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.obras));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#40FF6900"));
                        break;
                    case "Meteorología":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.meteorologia));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.meteorologia));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#403C59EA"));
                        break;
                    case "Seguridad vial":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.seguridad_vial));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.seguridad_vial));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#40FFB02E"));
                        break;
                    case "Retención":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.retencion));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.retencion));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#40F8312F"));
                        break;
                    case "Vialidad invernal":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.vialidad_invernal));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.vialidad_invernal));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#4001A1E6"));
                        break;
                    case "Puertos de montaña":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.puertos_de_montana));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.puertos_de_montana));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#408C5543"));
                        break;
                    case "Otros":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.otros));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.otros));
                        binding.typeBackground.setBackgroundColor(Color.parseColor("#40AD46FF"));
                        break;
                }

                AnimUtils.slideUp(binding.bottomMenu);
            }

            return false; // false allows default behavior (camera moves to marker)
        });
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
                        Marker marker = mMap.addMarker(new MarkerOptions().position(position));
                        markerIncidenceMap.put(marker, incidence); // associate marker with data
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