package com.teampyroxinc.wi_fer;




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
import java.net.Socket;


public class ClientActivity extends AppCompatActivity  {

    private String host;
    public Bundle bundle;
    public  EditText textout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        bundle = getIntent().getExtras();
        host = bundle.getString("Host");
        textout = (EditText)findViewById(R.id.textout);


    }

    public void send_data(View view){
        byte[] buf = textout.getText().toString().getBytes();
        int len = textout.getText().length();
        ClientAsyncTask clientAsyncTask = new ClientAsyncTask(host,buf,len);
        clientAsyncTask.execute();


    }
    public static class ClientAsyncTask extends AsyncTask{
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
                socket.connect(new InetSocketAddress(host_address,8080),5000);
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