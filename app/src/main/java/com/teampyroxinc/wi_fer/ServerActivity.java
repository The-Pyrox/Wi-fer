package com.teampyroxinc.wi_fer;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerActivity extends AppCompatActivity {
    public Integer b;
    public TextView display;
    public String v;
    Button button;
    boolean connected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        button = (Button) findViewById(R.id.button);
        connected = true;

        Thread socketServerThread = new Thread(new SocketServerThread());
        display = (TextView) findViewById(R.id.display);
        socketServerThread.start();
    }
    public void server_open(){
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();

    }

    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;


        @Override
        public void run() {
            try {
                final byte buf[] = new byte[1024];
                ServerSocket serverSocket = new ServerSocket(SocketServerPORT);
                while (connected == true) {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    b = inputStream.read(buf);
                    v = new String(buf, "UTF-8");
                    connected = true;



                ServerActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        display.setText(v);
                        server_open();
                    }
                }
                );}
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}

