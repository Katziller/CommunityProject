package com.example.recovery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Education;
import com.Finance;
import com.HealthSector;

public class MainActivity extends AppCompatActivity {

    private TextView back;
    private Button education, finance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        Button health_Sector = findViewById ( R.id.Health_Sector );
        Button education = findViewById ( R.id.Education );
        Button finance = findViewById ( R.id.Finance );

        health_Sector.setOnClickListener ( v -> {
            Intent HealthSector = new Intent ( MainActivity.this, com.HealthSector.class );
            startActivity ( HealthSector );
        } );

        education.setOnClickListener ( v -> {
            Intent Education = new Intent ( MainActivity.this, com.Education.class );
            startActivity ( Education );
        } );

        finance.setOnClickListener ( v -> {
            Intent Finance = new Intent ( MainActivity.this, com.Finance.class );
            startActivity ( Finance );
        } );
    }
}