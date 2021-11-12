package com.example.myapplication;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity4 extends AppCompatActivity {
    private static final String url=MainActivity.url;
    private static final String user="android1";
    private static final String pass="android1";
    Button bt1;
    TextView tt;
    EditText ed1;
    String s="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        bt1= findViewById(R.id.button5);
        ed1= findViewById(R.id.editText4);
tes();
    //    bt.setOnClickListener(v -> testDb());


        bt1.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Are you sure to delete\n \n"+ed1.getText().toString());
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> testDb1());
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }





    public void testDb1()
    {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            s=ed1.getText().toString();
            Statement st1=con.createStatement();
            String q1="delete from service where service_id='"+s+"' ";
            st1.executeUpdate(q1);
            Toast.makeText(this, "Deleted succesfully!",
                    Toast.LENGTH_LONG).show();
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
    public void tes()
    {
        tt= findViewById(R.id.tx);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, pass);
            PreparedStatement ps= con.prepareStatement("select * from service");
            ResultSet rs=ps.executeQuery();
            StringBuilder result= new StringBuilder();
            while(rs.next())
            {
                result.append("service id :--").append(rs.getString(1)).append("\n").append("name:--").append(rs.getString(3)).append("\n").append("service type:--").append(rs.getString(9)).append("\n").append("status:--").append(rs.getString(7)).append("\n").append("\n").append("\n");
            }
            tt.setText(result.toString());
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