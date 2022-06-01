package com.example.nlp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText edname,edemail,edphone,edplace,epassw,ecpassw,edjob;
    RadioButton m,f,o ;
    Button b1;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edname=(EditText)findViewById(R.id.name);
        edemail=(EditText)findViewById(R.id.email);
        edphone=(EditText)findViewById(R.id.phone);
        edjob=(EditText)findViewById(R.id.dob);
        edplace=(EditText)findViewById(R.id.place);
        epassw=(EditText)findViewById(R.id.password);
        ecpassw=(EditText)findViewById(R.id.cpassword);
        m=(RadioButton)findViewById(R.id.male);
        f=(RadioButton)findViewById(R.id.female);
        img=(ImageView) findViewById(R.id.imageView);

        b1=(Button)findViewById(R.id.button3);

        b1.setOnClickListener(this);
        img.setOnClickListener(this);


    }
    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;
    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ////
                Uri uri = data.getData();

                try {
                    path = FileUtils.getPath(this, uri);

                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);


                    fname = path.substring(path.lastIndexOf("/") + 1);
//                    ed15.setText(fname);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {

                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img.setImageBitmap(myBitmap);

                    }


                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");

                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;


                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                ///


            }
        }
    }
    String gen="";

    @Override
    public void onClick(View view) {


        if(view==img){
            showfilechooser(1);

        }else if(view==b1){
            Toast.makeText(this, "enterrrrrrrrrrrrrrrrrrrrrr", Toast.LENGTH_SHORT).show();

            final String name=edname.getText().toString();
            final String email=edemail.getText().toString();
            final String phone=edphone.getText().toString();
            final String place=edplace.getText().toString();
            final String job=edjob.getText().toString();

            String passw=epassw.getText().toString();
            final String cpassw=ecpassw.getText().toString();

//
            if(m.isChecked()==true)
            {
                gen="Male";
            }else if(f.isChecked()==true)
            {
                gen="Female";
            }


//            Toast.makeText(this, "kkkkkkkkkkkkkkkkkkkkkkkkkkk", Toast.LENGTH_SHORT).show();
            if(name.length()==0){
                edname.setError("Missing");
            }else if(email.length()==0){
                edemail.setError("Missing");

            }else if(phone.length()==0){
                edphone.setError("Missing");
            }else if(phone.length()!=10){
                edphone.setError("Must be 10");
            }else if(place.length()==0){
                edplace.setError("Missing");

            }else if(passw.length()==0){
                epassw.setError("Missing");
            }else if(cpassw.length()==0){
                ecpassw.setError("Missing");
            }else if(!passw.equalsIgnoreCase(cpassw)){
                epassw.setError("Password Mismatch");
            }else {


                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/and_user_reg";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(),Login.class));









                                    }

                                    // }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }    catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();
//                        ``,``,``,``,``,``,``,``,``,``
                        params.put("name",name);
                        params.put("email",email);
                        params.put("gender",gen);
                        params.put("phone",phone);
                        params.put("place",place);
                        params.put("photo",attach);
                        params.put("dob",job);

                        params.put("passw",cpassw);


                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);
            }



        }
        else {
            Toast.makeText(this, "errorr", Toast.LENGTH_SHORT).show();
        }



    }
}