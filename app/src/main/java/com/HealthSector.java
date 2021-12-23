package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.RegistrationActivity;
import com.example.recovery.R;

public class HealthSector extends AppCompatActivity {

    private TextView loginPageQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_health_sector );

        loginPageQuestion = findViewById ( R.id.loginPageQuestion );

        loginPageQuestion.setOnClickListener ( v -> {
            Intent intent = new Intent (HealthSector.this, RegistrationActivity.class);
            startActivity ( intent );
        } );
    }
}