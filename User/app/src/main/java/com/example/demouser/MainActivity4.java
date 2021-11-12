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

public class MainActivity4 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
   public TextView ed1;
    EditText ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed12;
    String st1="";
    String st3="";
    String st4="";
    String st6="";
    String st2="";
    String st7="";
    String st9="";
    String st8="";
    String st12="";
    String t="";
    String status="";
    String stt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
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
            ed3= findViewById(R.id.editText5);
            ed4= findViewById(R.id.editText7);
            ed6= findViewById(R.id.editText9);
            ed7= findViewById(R.id.editText10);
            ed8= findViewById(R.id.editText8);
            ed9= findViewById(R.id.editText);
            ed12= findViewById(R.id.editText12);
            st1=ed1.getText().toString();
            st2=ed3.getText().toString();
            st6=ed6.getText().toString();
            st3=ed3.getText().toString();
            st4=ed4.getText().toString();
            st7=ed7.getText().toString();
            st9=ed9.getText().toString();
            st8=ed8.getText().toString();
            st12=ed12.getText().toString();




            SharedPreferences shared1 = getSharedPreferences("sid2", MODE_PRIVATE);
            String ph1 = shared1.getString("sid2","");

            t="police";
            status="pending";

            st1="ALERT"+stt;

            String query ="insert into tbl_alert(alert_id,type_of_incident,description,phone,location,pincode,name,address,contact_no,target,status)values('"+st1+"','"+st2+"','"+ st4+"','"+ph1+"','"+st6+"','"+st9+"','"+st8+"','"+st12+"','"+st7+"','"+t+"','"+status+"')";
            st.executeUpdate(query);

           // Statement st11=con.createStatement();
            String q1="update param set aid='"+stt+"' ";
            st.executeUpdate(q1);


            Intent f = new Intent(this, MainActivity3.class);
           //finish();
            startActivity(f);
            Toast.makeText(this,"Successfully Registered", Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("connection to server lost")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}