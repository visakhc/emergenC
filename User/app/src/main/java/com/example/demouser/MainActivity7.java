
package com.example.demouser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity7 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        testDb();


    }




    public void testDb()
    {
        tv= findViewById(R.id.textView9);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);


            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);
            String status="completed";
            String ph1 = shared1.getString("sid2","");

            PreparedStatement ps= con.prepareStatement("select *from tbl_alert where status='"+status+"' && phone='"+ph1+"' ");
            ResultSet rs=ps.executeQuery();
            StringBuilder result= new StringBuilder();
            while(rs.next())
            {
                result.append("alert id       :").append(rs.getString(1)).append("\n").append("response           :").append(rs.getString(12)).append("\n").append("address          :").append(rs.getString(8)).append("\n").append("\n");

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