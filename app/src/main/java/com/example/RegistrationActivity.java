package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.HealthSector;
import com.example.recovery.R;

public class RegistrationActivity extends AppCompatActivity {

    private TextView back;

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

    }
}