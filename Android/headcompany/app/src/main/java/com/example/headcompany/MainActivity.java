package com.example.headcompany;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton login = null;
    private MaterialButton signup = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_UP:
                    AnimUtils.jiggleUp(v);
                    v.setPressed(true);
                    Intent gotoLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(gotoLoginActivity);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
            return true;
        });

        signup.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AnimUtils.pressDown(v);
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_UP:
                    AnimUtils.jiggleUp(v);
                    v.setPressed(true);
                    Intent gotoSignupActivity = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(gotoSignupActivity);
                    break;
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