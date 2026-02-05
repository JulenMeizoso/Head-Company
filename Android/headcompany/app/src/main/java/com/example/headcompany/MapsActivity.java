package com.example.headcompany;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.headcompany.api.ApiClient;
import com.example.headcompany.database.DataManager;
import com.example.headcompany.databinding.ActivityMapsBinding;
import com.example.headcompany.model.Camera;
import com.example.headcompany.model.CameraResponse;
import com.example.headcompany.model.Incidence;
import com.example.headcompany.model.IncidenceResponse;
import com.example.headcompany.utils.AnimUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    // SWITCHES
    private boolean favSwitch;
    private boolean accidentesSwitch = true;
    private boolean obrasSwitch = true;
    private boolean meteorologiaSwitch = true;
    private boolean seguridadVialSwitch = true;
    private boolean retencionSwitch = true;
    private boolean vialidadInvernalSwitch = true;
    private boolean puertosDeMontanaSwitch = true;
    private boolean otrosSwitch = true;
    private boolean camarasSwitch = true;

    private Map<Marker, Incidence> markerIncidenceMap = new HashMap<>();
    private Map<Marker, Camera> markerCameraMap = new HashMap<>();

    private Incidence selectedIncidence;
    private Camera selectedCamera;

    DataManager dataManager = new DataManager(this);

    private void refreshMarkers() {
        for (Marker marker : markerIncidenceMap.keySet()) {

            Incidence inc = markerIncidenceMap.get(marker);

            if (inc == null) continue;

            String type = inc.getIncidenceType();

            boolean typeVisible = false;
            switch (type) {
                case "Accidente":
                    typeVisible = accidentesSwitch;
                    break;
                case "Obras":
                    typeVisible = obrasSwitch;
                    break;
                case "Meteorología":
                    typeVisible = meteorologiaSwitch;
                    break;
                case "Seguridad vial":
                    typeVisible = seguridadVialSwitch;
                    break;
                case "Retención":
                    typeVisible = retencionSwitch;
                    break;
                case "Vialidad invernal":
                    typeVisible = vialidadInvernalSwitch;
                    break;
                case "Puertos de montaña":
                    typeVisible = puertosDeMontanaSwitch;
                    break;
                case "Otros":
                    typeVisible = otrosSwitch;
                    break;
            }

            // Apply favorite filter
            boolean finalVisible;
            if (favSwitch) {
                finalVisible = typeVisible && dataManager.isFavorite(inc.getIncidenceId());
            } else {
                finalVisible = typeVisible;
            }

            marker.setVisible(finalVisible);

            if (finalVisible) {
                boolean isFav = dataManager.isFavorite(inc.getIncidenceId());
                marker.setIcon(getMarkerIconForType(inc.getIncidenceType(), isFav));
            }

        }

        for (Marker marker : markerCameraMap.keySet()) {
            marker.setVisible(camarasSwitch);
        }
    }

    private void updateFavButtonText() {
        if (selectedIncidence == null) return;

        boolean isFav = dataManager.isFavorite(selectedIncidence.getIncidenceId());

        if (isFav) {
            binding.favButton.setText(R.string.favs_remove);
            binding.favButton.setIcon(ContextCompat.getDrawable(this, R.drawable.fav_on));
        } else {
            binding.favButton.setText(R.string.favs_add);
            binding.favButton.setIcon(ContextCompat.getDrawable(this, R.drawable.fav_off));
        }
    }


    private void hideSystemBars() {
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(
                getWindow(), getWindow().getDecorView()
        );

        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }




    // TESTING
    private ArrayList<Incidence> createTestIncidences() {
        ArrayList<Incidence> testList = new ArrayList<>();

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000001,
                "",
                "",
                "Accidente",
                0,
                1,
                0.0,
                0.0,
                "",
                "",
                1000001,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000002,
                "",
                "",
                "Obras",
                0,
                2,
                0.0,
                0.0,
                "",
                "",
                1000002,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000003,
                "",
                "",
                "Meteorología",
                0,
                3,
                0.0,
                0.0,
                "",
                "",
                1000003,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000004,
                "",
                "",
                "Seguridad vial",
                0,
                4,
                0.0,
                0.0,
                "",
                "",
                1000004,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000005,
                "",
                "",
                "Retención",
                0,
                5,
                0.0,
                0.0,
                "",
                "",
                1000005,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000006,
                "",
                "",
                "Vialidad invernal",
                0,
                6,
                0.0,
                0.0,
                "",
                "",
                1000006,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000007,
                "",
                "",
                "Puertos de montaña",
                0,
                7,
                0.0,
                0.0,
                "",
                "",
                1000007,
                ""
        ));

        testList.add(new Incidence(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                1000008,
                "",
                "",
                "Otros",
                0,
                8,
                0.0,
                0.0,
                "",
                "",
                1000008,
                ""
        ));

        return testList;
    }

    Camera testCamera = new Camera(
            "",
            1000009,
            "",
            "",
            0,
            9,
            "",
            1000009,
            "https://i.redd.it/my-friend-keeps-sending-these-hamsters-as-reaction-images-v0-p95a726z385f1.jpg?width=1290&format=pjpg&auto=webp&s=d7f5203e1f9730fbe3f19a249286cf4c4da0d599"
    );




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favSwitch = false;

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideSystemBars();



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

                    favSwitch = !favSwitch;

                    binding.fav.setImageResource(
                            favSwitch ? R.drawable.fav_on : R.drawable.fav_off
                    );

                    refreshMarkers();
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
                binding.accidentes.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.accidente)));
                binding.accidentes.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.accidente)));
            } else {
                binding.accidentes.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.accidentes.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.obras.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.obras)));
                binding.obras.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.obras)));
            } else {
                binding.obras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.obras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.meteorologia)));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.meteorologia)));
            } else {
                binding.meteorologia.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.meteorologia.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.seguridadVial.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.seguridad_vial)));
                binding.seguridadVial.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.seguridad_vial)));
            } else {
                binding.seguridadVial.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.seguridadVial.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.retencion.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.retencion)));
                binding.retencion.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.retencion)));
            } else {
                binding.retencion.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.retencion.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.vialidadInvernal.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.vialidad_invernal)));
                binding.vialidadInvernal.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.vialidad_invernal)));
            } else {
                binding.vialidadInvernal.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.vialidadInvernal.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.puertosDeMontana.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.puertos_de_montana)));
                binding.puertosDeMontana.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.puertos_de_montana)));
            } else {
                binding.puertosDeMontana.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.puertosDeMontana.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.otros.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.otros)));
                binding.otros.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.otros)));
            } else {
                binding.otros.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.otros.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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
                binding.camaras.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.camaras)));
                binding.camaras.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.camaras)));
            } else {
                binding.camaras.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#828282")));
                binding.camaras.setTextColor(ColorStateList.valueOf(Color.parseColor("#828282")));
            }
            refreshMarkers();
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



        binding.favButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);

                    if (selectedIncidence == null) break;

                    boolean isFav = dataManager.isFavorite(selectedIncidence.getIncidenceId());

                    if (isFav) {
                        dataManager.removeFavorite(selectedIncidence.getIncidenceId());
                    } else {
                        dataManager.addFavorite(selectedIncidence);
                    }

                    updateFavButtonText();
                    refreshMarkers();
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
            refreshMarkers();
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

        fetchCameraPage(1);

        LatLng spain = new LatLng(40.4168, -3.7038);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spain, 6f));

        mMap.setOnMarkerClickListener(marker -> {

            selectedIncidence = markerIncidenceMap.get(marker);
            selectedCamera = markerCameraMap.get(marker);

            if (selectedIncidence != null) {
                binding.incidenceType.setText(selectedIncidence.getIncidenceType());
                binding.direccion.setText(R.string.direccion);
                binding.direction.setText(selectedIncidence.getDirection());
                binding.ciudad.setText(R.string.ciudad);
                binding.province.setText(selectedIncidence.getProvince());
                binding.fecha.setText(R.string.fecha);
                binding.startDate.setText(selectedIncidence.getStartDate());
                binding.infoCard.setBackgroundColor(Color.parseColor("#FFD9D9D9"));
                binding.favButton.setVisibility(View.VISIBLE);
                binding.direccion.setText(R.string.direccion);
                binding.cardImage.setImageURI(Uri.parse("https://freepngimg.com/thumb/winter_snow/6-2-clear-snow-png.png"));

                switch (binding.incidenceType.getText().toString()) {
                    case "Accidente":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.accidente));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.accidente));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.accidente_bg));
                        break;
                    case "Obras":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.obras));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.obras));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.obras_bg));
                        break;
                    case "Meteorología":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.meteorologia));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.meteorologia));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.meteorologia_bg));
                        break;
                    case "Seguridad vial":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.seguridad_vial));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.seguridad_vial));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.seguridad_vial_bg));
                        break;
                    case "Retención":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.retencion));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.retencion));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.retencion_bg));
                        break;
                    case "Vialidad invernal":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.vialidad_invernal));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.vialidad_invernal));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.vialidad_invernal_bg));
                        break;
                    case "Puertos de montaña":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.puertos_de_montana));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.puertos_de_montana));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.puertos_de_montana_bg));
                        break;
                    case "Otros":
                        binding.incidenceType.setTextColor(getResources().getColor(R.color.otros));
                        binding.typeBorder.setStrokeColor(getResources().getColor(R.color.otros));
                        binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.otros_bg));
                        break;
                }

                updateFavButtonText();

                AnimUtils.slideUp(binding.bottomMenu);
            }
            else if (selectedCamera != null) {
                updateFavButtonText();
                binding.incidenceType.setText(R.string.camara);
                binding.direccion.setText(R.string.carretera);
                binding.direction.setText(selectedCamera.getRoad());
                binding.ciudad.setText("");
                binding.province.setText("");
                binding.fecha.setText("");
                binding.startDate.setText("");
                binding.favButton.setVisibility(View.GONE);
                binding.cardImage.setImageURI(Uri.parse(testCamera.getUrlImage()));
                binding.infoCard.setBackgroundColor(Color.parseColor("#00000000"));
                Glide.with(this).load(selectedCamera.getUrlImage()).into(binding.cardImage);
                binding.incidenceType.setTextColor(getResources().getColor(R.color.camaras));
                binding.typeBorder.setStrokeColor(getResources().getColor(R.color.camaras));
                binding.typeBackground.setBackgroundColor(getResources().getColor(R.color.camaras_bg));
                AnimUtils.slideUp(binding.bottomMenu);
            }

            return false;
        });

        // TESTING
        ArrayList<Incidence> testIncidences = createTestIncidences();

        for (Incidence inc : testIncidences) {
            LatLng position = new LatLng(inc.getLatitude(), inc.getLongitude());
            boolean isFav = dataManager.isFavorite(inc.getIncidenceId());
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .icon(getMarkerIconForType(inc.getIncidenceType(), isFav))
            );

            markerIncidenceMap.put(marker, inc);
        }


        LatLng camTestPos = new LatLng(testCamera.getLatitude(), testCamera.getLongitude());
        Marker camTestMarker = mMap.addMarker(
                new MarkerOptions().position(camTestPos).icon(getMarkerIconForType("Cámaras", false))
        );

        markerCameraMap.put(camTestMarker, testCamera);


        refreshMarkers();
    }


    private BitmapDescriptor bitmapFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, vectorResId);
        if (vectorDrawable == null) return null;

        // Set bounds
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // Create a bitmap to draw the vector onto
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private BitmapDescriptor getMarkerIconForType(String type, boolean isFav) {
        int drawableId;

        if (isFav) {
            switch (type) {
                case "Accidente": drawableId = R.drawable.accidente_marker_fav; break;
                case "Obras": drawableId = R.drawable.obras_marker_fav; break;
                case "Meteorología": drawableId = R.drawable.meteorologia_marker_fav; break;
                case "Seguridad vial": drawableId = R.drawable.seguridad_vial_marker_fav; break;
                case "Retención": drawableId = R.drawable.retencion_marker_fav; break;
                case "Vialidad invernal": drawableId = R.drawable.vialidad_invernal_marker_fav; break;
                case "Puertos de montaña": drawableId = R.drawable.puertos_de_montana_marker_fav; break;
                case "Otros": drawableId = R.drawable.otros_marker_fav; break;
                case "Cámaras": drawableId = R.drawable.camara_marker; break;
                default: drawableId = R.drawable.default_marker; break;
            }
        }
        else {
            switch (type) {
                case "Accidente": drawableId = R.drawable.accidente_marker; break;
                case "Obras": drawableId = R.drawable.obras_marker; break;
                case "Meteorología": drawableId = R.drawable.meteorologia_marker; break;
                case "Seguridad vial": drawableId = R.drawable.seguridad_vial_marker; break;
                case "Retención": drawableId = R.drawable.retencion_marker; break;
                case "Vialidad invernal": drawableId = R.drawable.vialidad_invernal_marker; break;
                case "Puertos de montaña": drawableId = R.drawable.puertos_de_montana_marker; break;
                case "Otros": drawableId = R.drawable.otros_marker; break;
                case "Cámaras": drawableId = R.drawable.camara_marker; break;
                default: drawableId = R.drawable.default_marker; break;
            }
        }


        return bitmapFromVector(drawableId);
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

                        boolean isFav = dataManager.isFavorite(incidence.getIncidenceId());

                        Marker marker = mMap.addMarker(new MarkerOptions().position(position).icon(getMarkerIconForType(incidence.getIncidenceType(), isFav)));
                        markerIncidenceMap.put(marker, incidence);
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

    private void fetchCameraPage(final int page) {
        ApiClient.getApiService().getCameras(page).enqueue(new Callback<CameraResponse>() {
            @Override
            public void onResponse(Call<CameraResponse> call, Response<CameraResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CameraResponse cameraResponse = response.body();
                    List<Camera> cameras = cameraResponse.getItems();

                    for (Camera camera : cameras) {
                        LatLng position = new LatLng(camera.getLatitude(), camera.getLongitude());

                        Marker marker = mMap.addMarker(new MarkerOptions().position(position).icon(getMarkerIconForType("Cámaras", false)));
                        markerCameraMap.put(marker, camera);
                    }


                    if (cameraResponse.getCurrentPage() < cameraResponse.getTotalPages()) {
                        fetchCameraPage(page + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<CameraResponse> call, Throwable t) {
                Log.e("API_ERROR", "Request failed on page " + page, t);
            }
        });
    }


}