package com.example.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity6 extends AppCompatActivity {
    private static final String url=MainActivity3.url;
    private static final String user="android1";
    private static final String pass="android1";
    String st2="",st1="";
    EditText ed;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        st2 = getIntent().getExtras().getString("value");
        bt = findViewById(R.id.button2);
        bt.setOnClickListener(v -> testDb());
        ed = findViewById(R.id.response);
    }



    public void testDb(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            Statement st=con.createStatement();

            st1=ed.getText().toString();

            String ss="completed";



            String query ="update tbl_alert set response='"+st1+"',status='"+ss+"' where alert_id='"+st2+"' ";
            st.executeUpdate(query);





            Intent f = new Intent(this, MainActivity2.class);
            startActivity(f);
            Toast.makeText(this,"Successfully completed", Toast.LENGTH_LONG).show();
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