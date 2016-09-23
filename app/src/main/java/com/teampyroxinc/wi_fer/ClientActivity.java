package com.teampyroxinc.wi_fer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientActivity extends AppCompatActivity  {

    private String host;
    public Bundle bundle;
    public  EditText client_out;
    public TextView display;
    SocketSendThread socketsendthread;
    boolean connected = true;
    public Integer b;
    String v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        bundle = getIntent().getExtras();
        host = bundle.getString("Host");
        client_out = (EditText)findViewById(R.id.client_out);
        display = (TextView)findViewById(R.id.display);
        Thread socketreceiveThread = new Thread(new SocketReceiveThread());
        socketreceiveThread.start();
    }


    public void send_server(View view){
        byte[] buf = client_out.getText().toString().getBytes();
        int len = client_out.getText().length();
        socketsendthread = new SocketSendThread(host,buf,len);
        socketsendthread.start();
    }

    private class SocketReceiveThread extends Thread {

        static final int SocketServerPORT = 8080;

        @Override
        public void run() {
            try {

                ServerSocket serverSocket = new ServerSocket(SocketServerPORT);
                while (connected == true) {
                    final byte buf[] = new byte[1024];
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    b = inputStream.read(buf);
                    v = new String(buf, "UTF-8");
                    connected = true;
                    ClientActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            display.setText(v);
                        }
                    }
                    );}
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public class SocketSendThread extends Thread{
        String host_address;
        byte[] buf;
        int len;

        public SocketSendThread(String host,byte[] buf,int len) {
            this.host_address = host;
            this.buf = buf;
            this.len = len;
        }

        @Override
        public void run() {
            Socket socket_send = new Socket();
            try {
                socket_send.bind(null);
                socket_send.connect((new InetSocketAddress(host_address,8888)),5000);
                OutputStream outputStream = socket_send.getOutputStream();
                outputStream.write(buf, 0, len);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




}



