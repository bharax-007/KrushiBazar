package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update extends AppCompatActivity {
    public   static final String URl="https://krushibazaarofficial1.000webhostapp.com/UpdateProduct.php";

    public static final String ProName_URL="https://krushibazaarofficial1.000webhostapp.com/ProductName.php";
    EditText desc,price,stat,qunt;
    private Button update;
    Spinner spinner;
    String email;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.update);
       spinner=findViewById(R.id.SpinnerName);
        desc=findViewById(R.id.etDesc);
        price=findViewById(R.id.etprice);
        stat=findViewById(R.id.etStatus);
        qunt=findViewById(R.id.etQuntity);
        update=findViewById(R.id.Update);


        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        ListNameProducts(email);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d=desc.getText().toString();
                String p=price.getText().toString();
                String s=stat.getText().toString() ;
                String q=qunt.getText().toString();

                if(TextUtils.isEmpty(d)||TextUtils.isEmpty(p)||TextUtils.isEmpty(s)||TextUtils.isEmpty(q))
                {
                    Toast.makeText(Update.this, "Enter Above Filleds....", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdatProduct();
                }
            }
        });
    }

    private void ListNameProducts(final String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ProName_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String[] str=response.split(",");

                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,str);
                spinner.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        requestQueue.add(request);

    }



    private void UpdatProduct() {
        final RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, URl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Update.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",spinner.getSelectedItem().toString());
                params.put("desc",desc.getText().toString());
                params.put("price",price.getText().toString());
                params.put("stat",stat.getText().toString());
                params.put("qunt",qunt.getText().toString());
                return params;
            }
        };
        queue.add(request);
    }


}
