package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity2 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bt=findViewById(R.id.button6);
        bt.setOnClickListener(view -> goback());
        testDb();
    }

    private void goback() {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void testDb()
    {
        tv= findViewById(R.id.tw);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            PreparedStatement ps= con.prepareStatement("select * from service");
            ResultSet rs=ps.executeQuery();
            StringBuilder result= new StringBuilder();
            while(rs.next())
            {
                result.append("service id       :").append(rs.getString(1)).append("\n").append("name       :").append(rs.getString(3)).append("\n").append("location       :").append(rs.getString(2)).append("\n").append("phone       :").append(rs.getString(5)).append("\n").append("email       :").append(rs.getString(6)).append("\n").append("address       :").append(rs.getString(4)).append("\n").append("pincode       :").append(rs.getString(8)).append("\n").append("service type       :").append(rs.getString(9)).append("\n").append("status       :").append(rs.getString(7)).append("\n").append("\n").append("\n");

            }
            tv.setText(result.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("connection to the server lost")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {

                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

}