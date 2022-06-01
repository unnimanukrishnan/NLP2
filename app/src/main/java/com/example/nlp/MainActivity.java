package com.example.nlp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ed1;
    Button btn1;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText) findViewById(R.id.editTextTextPersonName);
        btn1=(Button) findViewById(R.id.button);
        ed1.setText("192.168.43.134");

        btn1.setOnClickListener(this);
//        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String edit=ed1.getText().toString();
        if(edit.length()==0){
            ed1.setError("Missing");
        }
        else{
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed=sh.edit();
            ed.putString("ip",edit);
            ed.commit();

            startActivity(new Intent(getApplicationContext(),Login.class));

        }


    }
}