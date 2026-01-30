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
import com.example.headcompany.utils.PasswordAuthentication;
import com.google.android.material.button.MaterialButton;

import java.net.InetAddress;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private ImageButton back = null;
    private MaterialButton signup = null;
    private EditText mail = null;
    private EditText repeat_password = null;
    private EditText password = null;
    private TextView mailerr = null;
    private TextView passerr = null;
    private TextView repeat_passerr = null;
    private final PasswordAuthentication pa = new PasswordAuthentication();


    public interface EmailCheckCallback {
        void onResult(boolean exists);
    }

    private void checkEmailExists(
            String email,
            int page,
            EmailCheckCallback callback
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

    private void createUser(String email, String password) {

        Usuario usuario = new Usuario(email, password);

        ApiClient.getApiService().createUser(usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignupActivity.this,
                        "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
        setContentView(R.layout.activity_signup);

        hideSystemBars();

        back = findViewById(R.id.back);

        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        repeat_password = findViewById(R.id.repeat_password);
        mailerr = findViewById(R.id.mailerr);
        passerr = findViewById(R.id.passerr);
        repeat_passerr = findViewById(R.id.repeat_passerr);

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

        signup = findViewById(R.id.signup);

        signup.setOnTouchListener((v, event) -> {

            int nArrobas = 0;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AnimUtils.jiggleUp(v);

                    // SI EL CORREO ESTÁ VACÍO
                    if (!mail.getText().toString().isEmpty()) {
                        // SI EL CORREO CONTIENE ARROBA @
                        if (mail.getText().toString().contains("@")) {

                            // CUENTA LOS ARROBAS @ DEL CORREO
                            for (int i = 0; i < mail.getText().toString().length(); i++) {
                                if (mail.getText().toString().charAt(i) == '@') {
                                    nArrobas++;
                                }
                            }
                            // SI EL CORREO SOLO TIENE 1 ARROBA @
                            if (nArrobas == 1) {

                                String [] arrayMail = mail.getText().toString().split("@");
                                if (arrayMail.length == 2) {

                                    mailerr.setText("");

                                    checkEmailExists(mail.getText().toString().trim(), 1, exists -> {
                                        if (!exists) {

                                            mailerr.setText("");

                                            if (!password.getText().toString().isEmpty()) {
                                                passerr.setText("");
                                                if (password.getText().toString().equals(repeat_password.getText().toString())) {
                                                    repeat_passerr.setText("");
                                                    createUser(mail.getText().toString(), password.getText().toString());
                                                }
                                                else {
                                                    repeat_passerr.setText(R.string.password_repeat_notequal);
                                                }
                                            }
                                            else {
                                                passerr.setText(R.string.input_empty);
                                            }
                                        } else {
                                            mailerr.setText(R.string.mail_already_exists);
                                        }
                                    });
                                }
                                else {
                                    mailerr.setText(R.string.invalid_format);
                                }
                            }
                            else {
                                mailerr.setText(R.string.mail_more_than_one_at);
                            }
                        }
                        else {
                            mailerr.setText(R.string.mail_no_at);
                        }
                    }
                    else {
                        mailerr.setText(R.string.input_empty);
                    }

                    v.setPressed(true);
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