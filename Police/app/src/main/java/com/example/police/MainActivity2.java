package com.example.police;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    Button police,logout;
    Button ambulance,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        police=findViewById(R.id.button4);
        police.setOnClickListener(v -> qui());
        ambulance=findViewById(R.id.button7);
        ambulance.setOnClickListener(v -> norm());
        back=findViewById(R.id.button);
        back.setOnClickListener(v -> goback());
        logout=findViewById(R.id.button8);
        logout.setOnClickListener(v -> log());
    }

    public void log() {
        SharedPreferences shared = getApplicationContext().getSharedPreferences("sid2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
        Intent f = new Intent(this, MainActivity.class);
        startActivity(f);
    }

    private void goback() {
        Intent f = new Intent(this, MainActivity7.class);
        startActivity(f);
    }

    public void qui(){
        Intent f = new Intent(this, MainActivity5.class);
        startActivity(f);
    }
    public void norm(){
        Intent f = new Intent(this, MainActivity3.class);
        startActivity(f);
    }
}