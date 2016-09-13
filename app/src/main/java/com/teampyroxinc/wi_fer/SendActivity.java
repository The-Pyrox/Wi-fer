package com.teampyroxinc.wi_fer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendActivity extends AppCompatActivity implements WifiP2pManager.ConnectionInfoListener {
    private InetSocketAddress inetSocketAddress;

    private int port;
    private String host;
    private int d;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        display=(TextView)findViewById(R.id.display);
        FileServerAsyncTask aynctask = new FileServerAsyncTask(port);


    }

    public void send_data(View view){
        Socket socket = new Socket();

        try {
            EditText textout = (EditText)findViewById(R.id.textout);

            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);


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


    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
            port = inetSocketAddress.getPort();
            host = inetSocketAddress.getHostName();
        }
        else if (wifiP2pInfo.groupFormed) {
            ((TextView) findViewById(R.id.textView2)).setText("Client");


        }
    }

    public static class FileServerAsyncTask extends AsyncTask{


        private int port;


        public FileServerAsyncTask(int port) {

            this.port = port;



        }

        @Override
        protected Object doInBackground(Object[] objects) {
            int b = 0;
            try {
                
                byte buf[] = new byte[1024];

                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                b =inputStream.read(buf);
                serverSocket.close();


            } catch (IOException e) {

            }
            return b;
        }
    }
}