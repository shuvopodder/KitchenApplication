package com.example.kitchenapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Application;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    String st;
    public static final String table="";
    Button b1,b2,b3,b4,b5,b6;

    TextView t;

    BottomNavigationView bottomNavigationView;


    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Table tb = new Table();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.txt);
        b1 = (Button) findViewById(R.id.table1);
        b2 = (Button) findViewById(R.id.table2);
        b3 = (Button) findViewById(R.id.table3);
        b4 = (Button) findViewById(R.id.table4);
        b5 = (Button) findViewById(R.id.table5);
        b6 = (Button) findViewById(R.id.table6);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,Table_Confirm.class);
                a.putExtra(table, "1");
                startActivity(a);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,Table_Confirm.class);
                a.putExtra(table, "2");
                startActivity(a);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,Table_Confirm.class);
                a.putExtra(table, "3");
                startActivity(a);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,Table_Confirm.class);
                a.putExtra(table, "4");
                startActivity(a);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,Table_Confirm.class);
                a.putExtra(table, "5");
                startActivity(a);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("6".getBytes());
                        b6.setText("Notified");
                        tb.setList("Table no 6 requested for taking food!!");
                    }
                    catch (IOException e)
                    {
                        // msg("Error");
                        b6.setText("Error");
                    }
                }
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Table_Confirm.class));
            }
        });

        bottomNavigationView = findViewById(R.id.btm);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch(item.getItemId())
        {
            case R.id.share:
                ApplicationInfo api =getApplicationContext().getApplicationInfo();
                String str = api.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent,"ShareVia"));
                break;

            case R.id.about:
                startActivity(new Intent(MainActivity.this,About_Us.class));
                break;
            case R.id.Home:
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.st:
                startActivity(new Intent(MainActivity.this,Completed_Order.class));
                break;
            case R.id.ct:
                startActivity(new Intent(MainActivity.this,Bluetooth_Confiq.class));
                break;
        }
        return true;
    }


    private class ConnectBt extends AsyncTask<Void,Void,Void>
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
               // startActivity(new Intent(MainActivity.this,Bluetooth_Confiq.class));
                //finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void msg(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataStore d = new DataStore();
        if(d.getAddress()==""){
            //startActivity(new Intent(MainActivity.this,Bluetooth_Confiq.class));
            Toast.makeText(MainActivity.this,"Please Configure your Device to continue!!!",Toast.LENGTH_LONG).show();
        }else{
            address = d.getAddress();
            new ConnectBt().execute();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(btSocket!=null){
            try {
                btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finish();


    }
}