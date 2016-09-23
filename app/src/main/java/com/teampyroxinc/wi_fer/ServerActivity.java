package com.teampyroxinc.wi_fer;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ServerActivity extends AppCompatActivity {
    public Integer b;
    public TextView display;
    public String v;
    Button button;
    boolean connected = true;

    public String getClient_address() {
        return client_address;
    }

    String client_address;
    SocketSendThread socketsendthread;
    String correct_address;

    EditText server_out;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        button = (Button) findViewById(R.id.button);
        connected = true;
        server_out =(EditText)findViewById(R.id.server_out);
        Thread socketreceiveThread = new Thread(new SocketReceiveThread());
        socketreceiveThread.start();


        display = (TextView) findViewById(R.id.display);

    }

    public void send_client(View view){
        byte[] buf = server_out.getText().toString().getBytes();
        int len = server_out.getText().length();
        correct_address = getClient_address().substring(1);
        socketsendthread = new SocketSendThread(correct_address,buf,len);
        socketsendthread.start();





    }


    private class SocketReceiveThread extends Thread {

        static final int SocketServerPORT = 8888;


        @Override
        public void run() {
            try {

                ServerSocket serverSocket = new ServerSocket(SocketServerPORT);
                while (connected == true) {
                    final byte buf[] = new byte[1024];
                    Socket socket = serverSocket.accept();
                    client_address = socket.getInetAddress().toString();
                    InputStream inputStream = socket.getInputStream();
                    b = inputStream.read(buf);
                    v = new String(buf, "UTF-8");
                    connected = true;
                    ServerActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        display.setText(v);
                    }
                }
                );
                }
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



                socket_send.connect((new InetSocketAddress(host_address,8080)),5000);



                OutputStream outputStream = socket_send.getOutputStream();
                outputStream.write(buf, 0, len);

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}

