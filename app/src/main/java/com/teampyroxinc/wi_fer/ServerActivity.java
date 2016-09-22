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

public class ServerActivity extends AppCompatActivity {
    public Integer b;
    public TextView display;
    public String v;
    Button button;
    boolean connected = true;
    String client_address;
    ServerAsyncTask serverasynctask;
    EditText server_out;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        button = (Button) findViewById(R.id.button);
        connected = true;
        server_out =(EditText)findViewById(R.id.server_out);

        Thread socketServerThread = new Thread(new SocketServerThread());
        display = (TextView) findViewById(R.id.display);
        socketServerThread.start();
    }
    public void send_client(View view){
        byte[] buf = server_out.getText().toString().getBytes();
        int len = server_out.getText().length();
        serverasynctask = new ServerAsyncTask(client_address,buf,len);
        serverasynctask.execute();



    }

    public class ServerAsyncTask extends AsyncTask {
        String host_address;
        byte[] buf;
        int len;

        public ServerAsyncTask(String host,byte[] buf,int len) {
            this.host_address = host;
            this.buf = buf;
            this.len = len;
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            Socket socket = new Socket();
            try {
                socket.bind(null);
                socket.connect((new InetSocketAddress(host_address,8991)),5000);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(buf, 0, len);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


    }



    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;


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
                );}
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}

