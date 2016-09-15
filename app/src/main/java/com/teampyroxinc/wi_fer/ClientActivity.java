package com.teampyroxinc.wi_fer;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity  {
    private InetSocketAddress inetSocketAddress;

    private int port;
    private String host;
    private int d;
    private static TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        display=(TextView)findViewById(R.id.display);




    }

    public void send_data(View view){
        Socket socket = new Socket();

        try {
            EditText textout = (EditText)findViewById(R.id.textout);
            inetSocketAddress = new InetSocketAddress(8888);


            socket.bind(null);
            socket.connect(inetSocketAddress);


            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = null;
            byte[] buf = textout.getText().toString().getBytes();
            int len = textout.getText().length();
            outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {

                    }
                }
            }
        }

    }




}