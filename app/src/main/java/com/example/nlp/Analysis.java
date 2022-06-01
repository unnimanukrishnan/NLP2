package com.example.nlp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Analysis extends AppCompatActivity {
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        PieChart pieChart = findViewById(R.id.pieChart);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP=sh.getString("ip","");
        String url="http://"+IP+":5000/analysis";


        progress=new ProgressDialog(this);
        progress.setMessage("loading......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress.show();



        final String usid=sh.getString("user","");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


//                        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();

                        //response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs=   jsonObj.getString("status");
                            if(sucs.equalsIgnoreCase("ok"))
                            {

//                                Toast.makeText(Analysis.this, "________________________________", Toast.LENGTH_SHORT).show();
                                progress.dismiss();


                                String Toxic= jsonObj.getString("Toxic");
                                String Severe_Toxic= jsonObj.getString("Severe_Toxic");
                                String Obsene= jsonObj.getString("Obsene");
                                String Threat= jsonObj.getString("Threat");
                                String Insult= jsonObj.getString("Insult");
                                String Indentity_Hate= jsonObj.getString("Indentity_Hate");
                                String Normal= jsonObj.getString("Normal");
                                float angry = Float.parseFloat(Toxic);
                                float dis = Float.parseFloat(Severe_Toxic);
                                float fea = Float.parseFloat(Obsene);
                                float happy = Float.parseFloat(Threat);
                                float neu = Float.parseFloat(Insult);
                                float sad = Float.parseFloat(Indentity_Hate);
                                float normal = Float.parseFloat(Normal);
                                ArrayList NoOfEmp = new ArrayList();

                                NoOfEmp.add(new Entry(angry, 0));
                                NoOfEmp.add(new Entry(dis, 1));
                                NoOfEmp.add(new Entry(fea, 2));
                                NoOfEmp.add(new Entry(happy, 3));
                                NoOfEmp.add(new Entry(neu, 4));
                                NoOfEmp.add(new Entry(sad, 5));
                                NoOfEmp.add(new Entry(normal, 6));
//                NoOfEmp.add(new Entry(1487f, 5));
//                NoOfEmp.add(new Entry(1501f, 6));
//                NoOfEmp.add(new Entry(1645f, 7));
//                NoOfEmp.add(new Entry(1578f, 8));
//                NoOfEmp.add(new Entry(1695f, 9));
                                PieDataSet dataSet = new PieDataSet(NoOfEmp, "Emotion %");

                                ArrayList year = new ArrayList();

                                year.add("Flirt");
                                year.add("Threat");
                                year.add("Violence");
                                year.add("Politics");
                                year.add("Insult");
                                year.add("Hate");
                                year.add("Normal");
//                year.add("2013");
//                year.add("2014");
//                year.add("2015");
//                year.add("2016");
//                year.add("2017");
                                PieData data = new PieData(year, dataSet);
                                pieChart.setData(data);
                                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                pieChart.animateXY(5000, 5000);

                            }



                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Error"+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(),"eeeee"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String>  params = new HashMap<String, String>();



//
//                params.put("vid",sh.getString("vid",""));
                params.put("lid",sh.getString("lid",""));








//                params.put("asid",sh.getString("assid",""));
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