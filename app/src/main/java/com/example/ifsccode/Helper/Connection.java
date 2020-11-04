package com.example.ifsccode.Helper;

import android.util.Log;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.Helper;

import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    public static HttpURLConnection getConnection(String code){
        HttpURLConnection connection=null;
        String path="https://ifsc.razorpay.com/"+code;
        try {

            URL url=new URL(path);
            connection=(HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

        }catch (Exception e){
            Log.d("error",e+"");
        }
return connection;
    }
}
