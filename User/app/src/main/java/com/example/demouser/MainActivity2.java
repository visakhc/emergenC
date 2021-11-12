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

import static com.example.demouser.MainActivity.name;
import static com.example.demouser.MainActivity.st2;
public class MainActivity2 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt;
    TextView ph;
    EditText ed1,ed3,ed4;
   public static String st3="",st4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bt= findViewById(R.id.button);
        bt.setOnClickListener(v -> todb());
        ph=findViewById(R.id.textView13);
        ph.setText(st2);
        ed1= findViewById(R.id.name);
        ed3= findViewById(R.id.email);
        ed4= findViewById(R.id.address);


    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void todb() {
        MainActivity.name = ed1.getText().toString();
        st3 = ed3.getText().toString();
        st4 = ed4.getText().toString();

        if (name.length() == 0 || st3.length() == 0 || st4.length() == 0) {
            Toast.makeText(this, "Please fill every field.", Toast.LENGTH_LONG).show();

        } else {

            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();


                st2 = ph.getText().toString();
                String query = "insert into user(name,phone,email,address)values('" + MainActivity.name + "','" + st2 + "','" + st3 + "','" + st4 + "')";
                st.executeUpdate(query);



                SharedPreferences shared1 = getApplicationContext().getSharedPreferences("sid2", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared1.edit();
                editor.putString("sid2", st2);
                editor.apply();
                SharedPreferences shar = getApplicationContext().getSharedPreferences("nm", MODE_PRIVATE);
                SharedPreferences.Editor edi = shar.edit();
                edi.putString("nm",MainActivity.name);
                edi.apply();




                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                openActivity3();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("server connection not found")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    public void openActivity3() {

        Intent i = new Intent(this, MainActivity3.class);
        startActivity(i);
    }
}