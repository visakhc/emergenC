package com.example.ambulance;



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
    private static final String url=MainActivity3.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
    TextView tv;
    EditText ed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        testDb();
        bt=(Button)findViewById(R.id.button2);

        ed=(EditText) findViewById(R.id.alertid);

        bt.setOnClickListener(v -> testDb1());



    }




    public void testDb()
    {
        tv=(TextView)findViewById(R.id.textView3);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");

            Connection con= DriverManager.getConnection(url, user, pass);
            String st="quick_pending";
            String t="ambulance";
            String sid2="";
            SharedPreferences shared1 = getSharedPreferences(sid2, MODE_PRIVATE);

            String ph1 = shared1.getString("sid2","");
            PreparedStatement ps=(PreparedStatement)con.prepareStatement("select *from tbl_alert where status='"+st+"' && target='"+t+"' && pincode='"+ph1+"' ");

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
            builder.setMessage("Invalid Username or ServiceId")
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