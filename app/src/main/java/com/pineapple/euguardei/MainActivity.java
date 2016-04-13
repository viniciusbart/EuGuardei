package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vinicius on 12/04/16.
 */
public class MainActivity extends Activity {

    private Button btLogout;

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
    }
}
