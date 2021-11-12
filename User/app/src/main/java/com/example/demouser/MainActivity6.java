package com.example.demouser;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity6 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
    TextView ed1;
    EditText ed2,ed3,ed4,ed5;
    String st1="";
    String st2="";
    String st3="";
    String st4="";
    String st5="";
    String t="";
    String status="";

    String stt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        bt= findViewById(R.id.button4);
        ed1= findViewById(R.id.editText4);
        stt = getIntent().getExtras().getString("value");
        ed1.setText("ALERT"+stt);
        bt.setOnClickListener(v -> todb());
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void todb(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            Statement st=con.createStatement();
            ed1= findViewById(R.id.editText4);
            ed2= findViewById(R.id.editText5);
            ed3= findViewById(R.id.editText7);
            ed4= findViewById(R.id.editText8);
            ed5= findViewById(R.id.editText9);
            st1=ed1.getText().toString();
            st2=ed2.getText().toString();
            st3=ed3.getText().toString();
            st4=ed4.getText().toString();
            st5=ed5.getText().toString();



            String sid2="";
            SharedPreferences shared1 = getSharedPreferences(sid2, MODE_PRIVATE);

            String ph1 = shared1.getString("sid2","");
            t="ambulance";
            status="pending";
            st1="ALERT"+stt;

            String query ="insert into tbl_alert(alert_id,pincode,name,address,phone,contact_no,target,status)values('"+st1+"','"+st5+"','"+st2+"','"+ st3+"','"+ph1+"','"+st4+"','"+t+"','"+status+"')";
            st.executeUpdate(query);

            //Statement s=con.createStatement();
            String q1="update param set aid='"+stt+"' ";
            st.executeUpdate(q1);

            Toast.makeText(this,"Successfully Registered", Toast.LENGTH_LONG).show();
            Intent f = new Intent(this, MainActivity3.class);
            //finish();
            startActivity(f);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("query error found")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}