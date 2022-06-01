package com.example.nlp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Costume_viewreply extends BaseAdapter {

    String[] view_complaint,view_reply,date;

    private Context context;

    public Costume_viewreply(Context appcontext, String[]view_complaint, String[]view_reply, String[]date)
    {
        this.context=appcontext;
        this.view_complaint=view_complaint;
        this.view_reply=view_reply;
        this.date=date;




    }

    @Override
    public int getCount() {
        return view_complaint.length;
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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custome_viewreply,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView7);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView8);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView9);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent ij=new Intent(context,viewproduct.class);
//                ij.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(ij);
//
//            }
//        });




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);





        tv1.setText(view_complaint[i]);
        tv2.setText(view_reply[i]);
        tv3.setText(date[i]);





//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//
//        String url="http://" + ip + ":5000"+image[i];
//
//
//        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}
