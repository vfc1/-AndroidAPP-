package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.bean.HomeArticle;
import com.example.android.view.HomeFragment;

import java.util.List;

public class AticleViewAdapter extends ArrayAdapter<HomeArticle> {

    private int resourceId;

    public AticleViewAdapter(Context context, int textViewResourceId, List<HomeArticle> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        HomeArticle homeArticle=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView title=null,author=null,time=null,label=null;
        title=(TextView)view.findViewById(R.id.title);
        author=(TextView)view.findViewById(R.id.author);
        time=(TextView)view.findViewById(R.id.time);
        label=(TextView)view.findViewById(R.id.type);
        title.setText(homeArticle.getMtitle());
        author.setText(homeArticle.getMauthor());
        time.setText(homeArticle.getMreleaseTime());
        label.setText(homeArticle.getType());
        return view;
    }
}
