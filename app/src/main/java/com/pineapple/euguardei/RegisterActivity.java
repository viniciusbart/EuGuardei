package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinicius on 12/04/16.
 */
public class RegisterActivity extends Activity {

    private EditText nome, email, senha;
    private Button enviar;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.105:80/EuGuardei/register_control.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nome = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        senha = (EditText) findViewById(R.id.editTextSenha);
        enviar = (Button) findViewById(R.id.buttonEnviar);

        requestQueue = Volley.newRequestQueue(this);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(), "SUCCESS "+jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else if(jsonObject.names().get(0).equals("exists")){
                                Toast.makeText(getApplicationContext(), "Error: "+jsonObject.getString("exists"), Toast.LENGTH_SHORT).show();
                            }else if(jsonObject.names().get(0).equals("error")){
                                Toast.makeText(getApplicationContext(), "Error: "+jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }else if(jsonObject.names().get(0).equals("both")){
                                Toast.makeText(getApplicationContext(), "Error: "+jsonObject.getString("both"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        hashMap.put("email",email.getText().toString());
                        hashMap.put("password",senha.getText().toString());
                        hashMap.put("nome",nome.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(request);

            }
        });

    }
}
