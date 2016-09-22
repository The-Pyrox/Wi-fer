package com.teampyroxinc.wi_fer;

import android.os.AsyncTask;
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
    ClientAsyncTask clientAsyncTask;
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
        Thread socketClientThread = new Thread(new SocketClientThread());
        socketClientThread.start();



    }
    private class SocketClientThread extends Thread {

        static final int SocketServerPORT = 8991;


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



    public void send_client(View view){
        byte[] buf = client_out.getText().toString().getBytes();
        int len = client_out.getText().length();
        clientAsyncTask = new ClientAsyncTask(host,buf,len);
        clientAsyncTask.execute();



    }
    public class ClientAsyncTask extends AsyncTask{
        String host_address;
        byte[] buf;
        int len;

        public ClientAsyncTask(String host,byte[] buf,int len) {
            this.host_address = host;
            this.buf = buf;
            this.len = len;
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            Socket socket = new Socket();
            try {
                socket.bind(null);
                socket.connect((new InetSocketAddress(host_address,8080)),5000);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(buf, 0, len);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


    }




}