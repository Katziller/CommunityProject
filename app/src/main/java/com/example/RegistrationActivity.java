package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.HealthSector;
import com.example.recovery.R;

public class RegistrationActivity extends AppCompatActivity {

    private TextView back;
    private Button doctorReg, patientRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.health_registration );

        back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (RegistrationActivity.this, HealthSector.class );
                startActivity ( intent );
            }
        } );

        patientRegistration = findViewById ( R.id.patientReg );
        doctorReg = findViewById ( R.id.doctorReg );

        patientRegistration.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RegistrationActivity.this, PatientRegistrationActivity.class);
                startActivity ( intent );
            }
        } );

        doctorReg.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RegistrationActivity.this, DoctorRegistrationActivity.class);
                startActivity ( intent );
            }
        } );

    }
}