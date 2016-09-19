package com.teampyroxinc.wi_fer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerActivity extends AppCompatActivity {
    public Integer b;
    public TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        display = (TextView)findViewById(R.id.display);

    }
    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;


        @Override
        public void run() {
            try {
                final byte buf[] = new byte[1024];

                ServerSocket serverSocket = new ServerSocket(SocketServerPORT);
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                b =inputStream.read(buf);

                ServerActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        display.setText(String.valueOf(Arrays.toString(buf)));

                    }
                });
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}

