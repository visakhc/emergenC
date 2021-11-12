package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity3 extends AppCompatActivity {
    Button bt;
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";

    TextView tv;
    EditText ed7,ed8,ed9,ed10,ed,ed99;
    Spinner sp1;
    String sid="",name="",address="",phone="",email="",location="",status="",pin="",target="";
    String stt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv=findViewById(R.id.textView8);
        bt=findViewById(R.id.but);
        stt = getIntent().getExtras().getString("value");
        tv.setText("SERVICE"+stt);
        bt.setOnClickListener(v -> testDb1());

    }




    public void testDb1()
    {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            Statement st=con.createStatement();

            tv= findViewById(R.id.textView8);
            ed7= findViewById(R.id.editText7);
            ed8= findViewById(R.id.editText8);
            ed9= findViewById(R.id.editText9);
            ed10= findViewById(R.id.editText10);
            ed= findViewById(R.id.editText);
            ed99=findViewById(R.id.editTe);
            sp1= findViewById(R.id.spinner1);
            sid=tv.getText().toString();
            name=ed7.getText().toString();
            address=ed8.getText().toString();
            phone=ed9.getText().toString();
            email=ed10.getText().toString();
            location=sp1.getSelectedItem().toString();
            status="active";
            pin=ed.getText().toString();
            target=ed99.getText().toString();

            String query ="insert into service(service_id,location,name,address,phone,email,status,pincode,target)values('"+sid+"','"+location+"','"+name+"','"+address+"','"+phone+"','"+email+"','"+status+"','"+pin+"','"+target+"')";
            st.executeUpdate(query);
            String q1="update param set sid='"+stt+"' ";
            st.executeUpdate(q1);

            Toast.makeText(this, "Added successfully!",Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);

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