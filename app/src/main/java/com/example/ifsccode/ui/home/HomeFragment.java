package com.example.ifsccode.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;

import com.example.ifsccode.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button submit;
    private EditText ifsc;
   // private ProgressDialog pd;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        submit=root.findViewById(R.id.Submit_BTN);
        ifsc=root.findViewById(R.id.IFSC_code_TXT);

        String code=ifsc.getText().toString();
        String url="https://ifsc.razorpay.com/"+code;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getDetails().execute(url);
            }
        });



        return root;
    }
    private class getDetails extends AsyncTask<String,String,String> {

//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pd = new ProgressDialog(MainActivity.this);
//            pd.setMessage("Please wait");
//            pd.setCancelable(false);
//            pd.show();
//        }
        @Override
        protected String doInBackground(String... params) {


            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer stringBuffer=new StringBuffer();
                String line="";

                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line+"\n");
                    Log.d("BANK: ", "> " + line);   //here we get whole response

                }

                return stringBuffer.toString();

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;


        }
    }
}