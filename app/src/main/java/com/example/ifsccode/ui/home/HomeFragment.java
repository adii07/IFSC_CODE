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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ifsccode.Helper.Helper;
import com.example.ifsccode.R;

import org.json.JSONException;
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
    private String url,code;
    private TextView BANK_NAME,IFSC_CODE,BRANCH_NAME,ADDRESS,CONTACT_NO,CITY,RTGS,DISTRICT,STATE;
   // private ProgressDialog pd;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        submit=root.findViewById(R.id.Submit_BTN);
        ifsc=root.findViewById(R.id.IFSC_code_TXT);
        BANK_NAME=root.findViewById(R.id.bank_name);
        IFSC_CODE=root.findViewById(R.id.IFSC);
        BRANCH_NAME=root.findViewById(R.id.branch);
        ADDRESS=root.findViewById(R.id.address);
        CONTACT_NO=root.findViewById(R.id.contact_number);
        CITY=root.findViewById(R.id.city);
        RTGS=root.findViewById(R.id.rtgs);
        DISTRICT=root.findViewById(R.id.district);
        STATE=root.findViewById(R.id.state);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=ifsc.getText().toString();
                url="https://ifsc.razorpay.com/"+code;
                loadData();
            }
        });
        return root;
    }

    private void loadData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            String Bank=response.getString("BANK");
                            String Ifsc=response.getString("IFSC");
                            String Branch=response.getString("BRANCH");
                            String Address=response.getString("ADDRESS");
                            String Contact=response.getString("CONTACT");
                            String City=response.getString("CITY");
                            Boolean Rtgs=response.getBoolean("RTGS");
                            String District=response.getString("DISTRICT");
                            String State=response.getString("STATE");
                            assignText(Bank,Ifsc,Branch,Address,Contact,City,Rtgs,District,State);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Helper.Log("error message",error.toString());
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void assignText(String Bank,String Ifsc,String Branch,String Address,String Contact,String City,Boolean Rtgs,String  District,String State){
        //Bank name
        BANK_NAME.setText(Bank);
        BANK_NAME.setVisibility(View.VISIBLE);
        //IFSC code
        IFSC_CODE.setText(Ifsc);
        IFSC_CODE.setVisibility(View.VISIBLE);
        //Branch name
        BRANCH_NAME.setText(Branch);
        BRANCH_NAME.setVisibility(View.VISIBLE);
        //Address
        ADDRESS.setText(Address);
        ADDRESS.setVisibility(View.VISIBLE);
        //Contact details
        CONTACT_NO.setText(Contact);
        CONTACT_NO.setVisibility(View.VISIBLE);
        //City name
        CITY.setText(City);
        CITY.setVisibility(View.VISIBLE);
       // RTGS availability
        RTGS.setText(Rtgs.toString());
        RTGS.setVisibility(View.VISIBLE);
        //District name
        DISTRICT.setText(District);
        DISTRICT.setVisibility(View.VISIBLE);
        //State name
        STATE.setText(State);
        STATE.setVisibility(View.VISIBLE);

    }

}