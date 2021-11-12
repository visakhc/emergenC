package com.example.demouser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class MainActivity extends AppCompatActivity {

        public EditText ed;
        public static final String url ="jdbc:mysql://192.168.1.10:3306/android1";
        public static final String user = "android1";
        public static final String pass = "android1";
        public static String name ="",st2 = "",ph1="";
        Button bt;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            SharedPreferences shared1= getApplicationContext().getSharedPreferences("sid3",MODE_PRIVATE);
            String s1 =shared1.getString("sid3", "");

            if(s1.length() == 0)
            {

            }else
            {
                Intent intent = new Intent(this,MainActivity3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }


            ed = findViewById(R.id.phone);
            bt = findViewById(R.id.button2);
            bt.setOnClickListener(v -> testDb());


        }


        public void testDb() {
            st2 = ed.getText().toString();
            if (st2.length() == 10) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Class.forName("com.mysql.jdbc.Driver");


                    Connection con = DriverManager.getConnection(url, user, pass);


                    PreparedStatement ps = con.prepareStatement("select * from user where phone='" + st2 + "' ");



                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        name = rs.getString(1);
                        ph1 = rs.getString(2);

                        SharedPreferences shared1 = getApplicationContext().getSharedPreferences("sid2", MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared1.edit();
                        editor.putString("sid2", ph1);
                        editor.apply();

                        SharedPreferences shar = getApplicationContext().getSharedPreferences("nm", MODE_PRIVATE);
                        SharedPreferences.Editor edi = shar.edit();
                        edi.putString("nm", name);
                        edi.apply();

                        openActivity3();

                    } else {
                        openActivity2();
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
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Invalid mobile number.")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }




        public void openActivity2() {

            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);

        }
        public void openActivity3() {
            Intent i = new Intent(this, MainActivity3.class);
            startActivity(i);

        }
    }
