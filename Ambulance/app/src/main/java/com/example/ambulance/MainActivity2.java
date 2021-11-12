package com.example.ambulance;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    Button police,back;
    Button ambulance,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        police=findViewById(R.id.button4);
        police.setOnClickListener(v -> pol());

        ambulance=findViewById(R.id.button7);
        ambulance.setOnClickListener(v -> amb());

        logout=findViewById(R.id.button3);
        logout.setOnClickListener(v -> log());

        back=findViewById(R.id.bbb);
        back.setOnClickListener(v -> goback());

    }

    public void log() {
        SharedPreferences shared = getApplicationContext().getSharedPreferences("sid2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
        Intent f = new Intent(this, MainActivity8.class);
        startActivity(f);
    }

    public void pol(){
        Intent f = new Intent(this, MainActivity5.class);
        startActivity(f);
    }
    public void amb(){
        Intent f = new Intent(this, MainActivity3.class);
        startActivity(f);
    }

    private void goback() {
        Intent f = new Intent(this, MainActivity7.class);
        startActivity(f);
    }
}