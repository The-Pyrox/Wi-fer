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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
    }

    public void send_data(View view){
        Socket socket = new Socket();

        try {
            EditText textout = (EditText)findViewById(R.id.textout);
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);

            /**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = null;
            byte[] buf = textout.getText().toString().getBytes();
            int len = textout.getText().length();
            outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            //catch logic
        } catch (IOException e) {
            //catch logic
        }

/**
 * Clean up any open sockets when done
 * transferring or if an exception occurred.
 */
        finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
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
            port = inetSocketAddress.getPort();
            host = inetSocketAddress.getHostName();
        }
    }

    public static class FileServerAsyncTask extends AsyncTask{
        private Context context;
        private TextView statusText;
        public FileServerAsyncTask(Context context, View statusText) {
            this.context = context;
            this.statusText = (TextView) statusText;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            Socket socket = null;
            byte buf[]  = new byte[1024];
            return null;
        }
    }
}