package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.RegistrationActivity;
import com.example.recovery.R;

public class Finance extends AppCompatActivity {

    private TextView loginPageQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_finance );

    }

}