package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.recovery.MainActivity;
import com.example.recovery.R;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private TextView regPageQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_doctor_registration );

        regPageQuestion = findViewById ( R.id.regPageQuestion );
        regPageQuestion.setOnClickListener ( view -> {
            Intent intent = new Intent (DoctorRegistrationActivity.this, MainActivity.class );
            startActivity ( intent );
        } );
    }
}