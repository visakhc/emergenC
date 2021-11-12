package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    Button bt,bt1,bt2;
    public static final String url="jdbc:mysql://192.168.1.10:3306/android1";
    private static final String user="android1";
    private static final String pass="android1";
    int result=0;
    String sid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Permissions granted (SEND_SMS).
        bt= findViewById(R.id.button);
        bt1= findViewById(R.id.button2);
        bt2=findViewById(R.id.button3);
        bt.setOnClickListener(v -> testDb());

        bt1.setOnClickListener(v -> testDb1());
bt2.setOnClickListener(view -> sh());
    }

    private void sh() {
        Intent i=new Intent(this,MainActivity2.class);
        startActivity(i);
    }


    public void testDb() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);
            PreparedStatement ps= con.prepareStatement("select * from param");

            ResultSet rs=ps.executeQuery();

            while(rs.next())
            {
                result += rs.getInt(1) ;

            }
            result=result+1;
            sid=String.valueOf(result);


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


        Intent i=new Intent(this,MainActivity3.class);
        i.putExtra("value",sid);
        startActivity(i);


    }

    public void testDb1() {


        Intent i=new Intent(this,MainActivity4.class);
        startActivity(i);


    }


}
