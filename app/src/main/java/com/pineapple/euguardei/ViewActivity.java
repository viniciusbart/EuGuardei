package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinicius on 25/04/16.
 */
public class ViewActivity extends Activity {

    SharedPreferences sharedPreferences;
    private static final String MyPrefs = "SharedPreferences";
    private EditText desc, local, data;
    private Button btAtualizar, btApagar;
    private RequestQueue requestQueue;
    private static final String URL_UPDATE = "http://192.168.1.103:80/EuGuardei/update_item.php";
    private static final String URL_DELETE = "http://192.168.1.103:80/EuGuardei/delete_item.php";
    private StringRequest request;
    private String kDesc;
    private String kLocal;
    private String kData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        desc = (EditText) findViewById(R.id.editTextDesc);
        local = (EditText) findViewById(R.id.editTextLocal);
        data = (EditText) findViewById(R.id.editTextDate);
        btApagar = (Button) findViewById(R.id.buttonApagar);
        btAtualizar = (Button) findViewById(R.id.buttonAtualizar);

        Bundle extras = getIntent().getExtras();

        desc.setText(extras.getString("K_DESC"));
        local.setText(extras.getString("K_LOCAL"));
        data.setText(extras.getString("K_DATA"));

        sharedPreferences = getSharedPreferences(MyPrefs,MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        btAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else if(jsonObject.names().get(0).equals("error")) {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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
                        hashMap.put("id_item",sharedPreferences.getString("id_item","NULL SHARED PREF"));
                        hashMap.put("desc_item",desc.getText().toString());
                        hashMap.put("local_item",local.getText().toString());
                        hashMap.put("date_item",data.getText().toString());
                        //hashMap.put("id_user",sharedPreferences.getString("id_user","NULL SHARED PREF"));
                        //hashMap.put("id_group","NULL");
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });

        btApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL_DELETE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else if(jsonObject.names().get(0).equals("error")) {
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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
                        hashMap.put("id_item",sharedPreferences.getString("id_item","NULL SHARED PREF"));
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
    }
}
