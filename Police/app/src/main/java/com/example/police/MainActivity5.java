package com.example.police;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MainActivity5 extends AppCompatActivity {
    public static final String url="jdbc:mysql://192.168.1.10:3306/android1";
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
    TextView tv;
    EditText ed;
    String s="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        testDb();
        bt= findViewById(R.id.button2);

        ed= findViewById(R.id.alertid);

        bt.setOnClickListener(v -> testDb1());

Button home;
home=findViewById(R.id.gohome);
home.setOnClickListener(v -> go());

    }

    private void go() {
        Intent f = new Intent(this, MainActivity2.class);
        startActivity(f);
    }


    public void testDb()
    {
        tv= findViewById(R.id.textView3);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);
            String st="quick_pending";
            String t="police";
            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);

            String ph1 = shared1.getString("sid2","");
            PreparedStatement ps= con.prepareStatement("select * from tbl_alert where status='"+st+"' && target='"+t+"' && pincode='"+ph1+"' ");

            ResultSet rs=ps.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            StringBuilder result= new StringBuilder();
            while(rs.next())
            {
                result.append(rs.getString(1)).append("\n");

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
    public void testDb1()
    {
        String aid=ed.getText().toString();
        Intent i=new Intent(this,MainActivity4.class);
        i.putExtra("value",aid);
        startActivity(i);
    }

}