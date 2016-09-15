package com.teampyroxinc.wi_fer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerActivity extends AppCompatActivity {
    private  static  TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        display = (TextView)findViewById(R.id.display);
        FileServerAsyncTask asyncTask = new FileServerAsyncTask(8080);
        asyncTask.execute();

    }
    public static class FileServerAsyncTask extends AsyncTask {


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
                showoutput(b);
                serverSocket.close();


            } catch (IOException e) {

            }
            return null;
        }
    }

    public static void showoutput(int a){
        display.setText(String.valueOf(a));

    }
}
