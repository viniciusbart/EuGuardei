package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinicius on 12/04/16.
 */
public class MainActivity extends Activity {

    SharedPreferences sharedPreferences;
    private static final String MyPrefs = "SharedPreferences";
    private Button btLogout, btAdd, btGrupo;
    private ListView listView;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.105:80/EuGuardei/getall_itens.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogout = (Button) findViewById(R.id.buttonLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        //listView.setOnItemClickListener(this);

        sendRequest();
    }

    private void sendRequest(){
        sharedPreferences = getSharedPreferences(MyPrefs,MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("list")){
                        showJSON(jsonObject.toString());
                    }else if(jsonObject.names().get(0).equals("exists")) {
                        Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("exists"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("id_user",sharedPreferences.getString("id_user","NULL SHARED PREF"));
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this,ParseJSON.descs,ParseJSON.dates);
        listView.setAdapter(cl);
    }
}
