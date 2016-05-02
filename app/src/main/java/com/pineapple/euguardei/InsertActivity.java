package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinicius on 24/04/16.
 */
public class InsertActivity extends Activity {

    SharedPreferences sharedPreferences;
    private static final String MyPrefs = "SharedPreferences";
    private EditText desc, local, dat;
    private Button enviar;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.103:80/EuGuardei/add_item.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        desc = (EditText) findViewById(R.id.editTextDesc);
        local = (EditText) findViewById(R.id.editTextLocal);
        dat = (EditText) findViewById(R.id.editTextData);
        enviar = (Button) findViewById(R.id.buttonEnviar);

        sharedPreferences = getSharedPreferences(MyPrefs,MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy @ HH:mm.ss");
        String currentDateandTime = sdf.format(new Date());
        dat.setText(currentDateandTime);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                            Toast.makeText(InsertActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
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
                        hashMap.put("desc",desc.getText().toString());
                        hashMap.put("local",local.getText().toString());
                        hashMap.put("date",dat.getText().toString());
                        hashMap.put("id_user",sharedPreferences.getString("id_user","NULL SHARED PREF"));
                        hashMap.put("id_group","NULL");
                        return hashMap;
                    }
                };
                requestQueue.add(request);

            }
        });
    }
}
