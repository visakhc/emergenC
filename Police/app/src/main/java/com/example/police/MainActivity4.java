package com.example.police;

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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MainActivity4 extends AppCompatActivity {
    private static final String url=MainActivity5.url;
    private static final String user="android1";
    private static final String pass="android1";
    public static Double lo,la;
    String st="";
    TextView tv,tv1,tv2,tv3;

    Button bt,map;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        st = getIntent().getExtras().getString("value");
        testDb();
        bt= findViewById(R.id.button5);
        map=findViewById(R.id.button3);
        bt.setOnClickListener(v -> testDb1());
        map.setOnClickListener(v -> openmap());
    }



    public void testDb()
    {
        tv= findViewById(R.id.textView4);
        tv1= findViewById(R.id.textView5);
        tv2= findViewById(R.id.textView6);
        tv3= findViewById(R.id.textView7);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);


            PreparedStatement ps=(PreparedStatement)con.prepareStatement("select * from tbl_alert where alert_id='"+st+"' ");

            ResultSet rs=ps.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            if(rs.next())
            {
                tv.setText("name   :"+rs.getString(7));
                tv1.setText("address     :"+rs.getString(8));
                tv2.setText("phone   :"+rs.getString(4));
                tv3.setText("pincode :"+rs.getString(6));
                lo=rs.getDouble(12);
                la=rs.getDouble(13);
            }


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

        Intent i=new Intent(this,MainActivity6.class);
        i.putExtra("value",st);
        startActivity(i);
    }
    public void openmap() {
        Intent f = new Intent(this, MapsActivity.class);
        startActivity(f);
    }
}
