package com.example.headcompany;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.headcompany.api.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView mail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        mail = findViewById(R.id.correo);

        ApiClient.getApiService().getUsers().enqueue(new Callback<List<Model.Usuario>>() {
            @Override
            public void onResponse(Call<List<Model.Usuario>> call, Response<List<Model.Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Model.Usuario> users = response.body();

                    mail.setText(users.get(2).getMail());

                }
            }

            public void onFailure(Call<List<Model.Usuario>> call, Throwable t) {
                mail.setText("no funciona");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}