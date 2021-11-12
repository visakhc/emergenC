package com.example.police;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    public EditText ed,s;
    public static final String url =MainActivity5.url;
    public static final String user = "android1";
    public static final String pass = "android1";
    public static String name ="",nme = "",ph1="",si="";
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        SharedPreferences shared1= getApplicationContext().getSharedPreferences("sid2",MODE_PRIVATE);
        String s1 =shared1.getString("sid2", "");

        if(s1.length() == 0)
        {

        }else
        {
            Intent intent = new Intent(this,MainActivity7.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        s=findViewById(R.id.serid);
        ed = findViewById(R.id.name);






        getBaseContext();
        ConnectivityManager connec = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

        } else if(
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            nodata();
        }








        bt = findViewById(R.id.button6);
        bt.setOnClickListener(v -> testDb());
    }
    public void testDb() {
        nme = ed.getText().toString();
        si = s.getText().toString();
        if(nme.isEmpty() || si.isEmpty())
        {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("com.mysql.jdbc.Driver");


                Connection con = DriverManager.getConnection(url, user, pass);


                PreparedStatement ps = con.prepareStatement("select * from service where name='" + nme + "' AND service_id='" + si + "' AND target='police'");


                String sid2 = "";

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString(1);

                    ph1 = rs.getString(2);

                    SharedPreferences shared1 = getApplicationContext().getSharedPreferences(sid2, MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared1.edit();
                    editor.putString("sid2", ph1);
                    editor.apply();
                    openActivity();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Invalid Username or ServiceId")
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> {

                            });
                    AlertDialog alert = builder.create();
                    alert.show();
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
    }



    public void openActivity() {

        Intent i = new Intent(this, MainActivity7.class);
        startActivity(i);

    }
    private void nodata() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("No internet connection")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {

                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }
}