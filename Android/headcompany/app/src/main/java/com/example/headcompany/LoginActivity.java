package com.example.headcompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.headcompany.api.ApiClient;
import com.example.headcompany.model.UsersResponse;
import com.example.headcompany.model.Usuario;
import com.example.headcompany.utils.AnimUtils;
import com.example.headcompany.utils.PasswordAuthentication;
import com.google.android.material.button.MaterialButton;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton login = null;
    private ImageButton back = null;
    private EditText mail = null;
    private EditText password = null;
    private TextView mailerr = null;
    private TextView passerr = null;


    public interface EmailCheckCallback {
        void onResult(boolean exists);
    }

    private void checkEmailExists(
            String email,
            int page,
            SignupActivity.EmailCheckCallback callback
    ) {
        ApiClient.getApiService().getUsers(page).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    callback.onResult(false);
                    return;
                }

                UsersResponse usersResponse = response.body();

                for (Usuario usuario : usersResponse.getItems()) {
                    if (usuario.getMail().equalsIgnoreCase(email)) {
                        callback.onResult(true);
                        return;
                    }
                }

                if (usersResponse.getCurrentPage() < usersResponse.getTotalPages()) {
                    checkEmailExists(email, page + 1, callback);
                } else {
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                callback.onResult(false);
            }
        });
    }

    private void checkEmailAndPassword(
            String mail,
            String plainPassword,
            int page,
            Consumer<Boolean> callback
    ) {
        ApiClient.getApiService().getUsers(page).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.accept(false);
                    return;
                }

                PasswordAuthentication pa = new PasswordAuthentication();

                for (Usuario user : response.body().getItems()) {

                    if (user.getMail().equalsIgnoreCase(mail)) {

                        boolean ok = pa.authenticate(
                                plainPassword.toCharArray(),
                                user.getContra()   // 🔑 HASHED TOKEN FROM API
                        );

                        callback.accept(ok);
                        return;
                    }
                }

                // Check next page if exists
                if (response.body().getCurrentPage() < response.body().getTotalPages()) {
                    checkEmailAndPassword(mail, plainPassword, page + 1, callback);
                } else {
                    callback.accept(false);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                callback.accept(false);
            }
        });
    }




    // Callback interface
    private interface CredentialsCallback {
        void onResult(boolean valid);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        hideSystemBars();

        login = findViewById(R.id.login);
        back = findViewById(R.id.back);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        mailerr = findViewById(R.id.mailerr);
        passerr = findViewById(R.id.passerr);


        back.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.jiggleDown(v);
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    this.finish();
                    break;
            }
            return true;
        });

        login.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_UP:
                    AnimUtils.jiggleUp(v);
                    v.setPressed(true);

                    String mailStr = mail.getText().toString().trim();
                    String passwordStr = password.getText().toString().trim();

                    if (!mailStr.isEmpty() && !passwordStr.isEmpty()) {
                        ApiClient.getApiService().loginUser(new Usuario(mailStr, passwordStr))
                                .enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful() && response.body() != null && response.body()) {
                                            Intent gotoMapsActivity = new Intent(LoginActivity.this, MapsActivity.class);
                                            startActivity(gotoMapsActivity);
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
            return true;
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}