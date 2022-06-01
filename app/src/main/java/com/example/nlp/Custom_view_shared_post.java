package com.example.nlp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Custom_view_shared_post extends BaseAdapter {
    String[]postid;
    String[]post;
    String[]date,name,image;
    private Context context;
    public Custom_view_shared_post(Context appcontext, String[]postid,String[]post,String[]date,String[]name,String[]image)
    {
        this.context=appcontext;
        this.postid=postid;
        this.post=post;
        this.date=date;
        this.name=name;
        this.image=image;
    }


    @Override
    public int getCount() {
        return postid.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_all_post, null);

        } else {
            gridView = (View) view;

        }
        TextView tvpost = (TextView) gridView.findViewById(R.id.tvpost);
        TextView tvpostdate = (TextView) gridView.findViewById(R.id.tvpostdate);

        TextView tvpostuname= (TextView) gridView.findViewById(R.id.postuname);


        ImageView uimg = (ImageView) gridView.findViewById(R.id.postuimg);
        ImageView imglike = (ImageView) gridView.findViewById(R.id.imglike);
        ImageView imgcomment = (ImageView) gridView.findViewById(R.id.imgcomment);
        ImageView imgsett = (ImageView) gridView.findViewById(R.id.imgsettings);
        imgsett.setTag(i);
        imgsett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();


                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("postid",postid[pos]);
                ed.commit();

                Intent i = new Intent(context, popup.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        imglike.setTag(i);
        imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/and_send_post_like";


                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(context, View_shared_all_post.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);

                                    }if (jsonObj.getString("status").equalsIgnoreCase("exist")) {

                                        Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(context, View_shared_all_post.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);

                                    }

                                    // }
                                    else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();

//                        params.put("lid", sh.getString("lid", ""));
                        params.put("postid", postid[pos]);
                        params.put("lid", sh.getString("lid", ""));

//
                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);


            }
        });


        imgcomment.setTag(i);
        imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("postid", postid[i]);
                ed.commit();

                Intent i = new Intent(context, comment.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


        tvpost.setTextColor(Color.BLACK);
        tvpostdate.setTextColor(Color.BLACK);


        tvpost.setText(post[i]);
        tvpostdate.setText(date[i]);
        tvpostuname.setText(name[i]);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[i];


        Picasso.with(context).load(url). into(uimg);


        return gridView;
    }

}
