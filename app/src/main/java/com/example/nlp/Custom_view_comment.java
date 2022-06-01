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
import android.widget.Button;
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

public class Custom_view_comment extends BaseAdapter {

    String[]uname,uimg,comid,comment,date;
    private Context context;
    public Custom_view_comment(Context appcontext, String[]uname,String[]uimg,String[]comid,String[]comment,String[]date)
    {
        this.context=appcontext;
        this.uname=uname;
        this.uimg=uimg;
        this.comid=comid;
        this.comment=comment;
        this.date=date;



    }


    @Override
    public int getCount() {
        return date.length;
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
            gridView = inflator.inflate(R.layout.custom_view_commets, null);

        } else {
            gridView = (View) view;

        }
        ImageView tvimage = (ImageView) gridView.findViewById(R.id.userimg);
        TextView tvname = (TextView) gridView.findViewById(R.id.commentusername);
        TextView tvcommnet = (TextView) gridView.findViewById(R.id.comment);
        TextView tvdate = (TextView) gridView.findViewById(R.id.comdate);

        tvname.setTextColor(Color.BLACK);
        tvcommnet.setTextColor(Color.BLACK);
        tvdate.setTextColor(Color.BLACK);


        tvname.setText(uname[i]);
        tvcommnet.setText(comment[i]);
        tvdate.setText(date[i]);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");

        String url = "http://" + ip + ":5000" + uimg[i];
        Picasso.with(context).load(url).into(tvimage);

        return gridView;
    }
}
