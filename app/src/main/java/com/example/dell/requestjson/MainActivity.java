package com.example.dell.requestjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ListView lstItem;
    ArrayAdapter adapter;

    private String name = "name";
    private String email = "email";
    private String address = "address";
    private String phone = "phone";
    private String website = "website";
    private String street = "street";
    private String url = "https://jsonplaceholder.typicode.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnObj = findViewById(R.id.btnObj);
        Button btnArray = findViewById(R.id.btnArray);
        lstItem = findViewById(R.id.lstItem);


        btnArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJsonArray();
            }
        });
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lstItem.setAdapter(adapter);

    }

    public void loadJsonArray() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String result = object.getString(name) + "\n" +
                                object.getString(email) + "\n" +
                                object.getJSONObject(address).getString(street) + "\n" +
                                object.getString(phone)+"\n"+
                               object.getString(website);
                        adapter.add(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.wtf(error.getMessage());
            }
        });

        RequestQueue queue=Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }
}
