package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.bean.ArticleBean;

import java.util.List;

public class AticleViewAdapter extends ArrayAdapter<ArticleBean> {

    private int resourceId;

    public AticleViewAdapter(Context context, int textViewResourceId, List<ArticleBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ArticleBean homeArticle=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.author = (TextView) view.findViewById(R.id.author);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.label = (TextView) view.findViewById(R.id.type);
            view.setTag(viewHolder);//将viewholder存储在view中

        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();//重新获取viewholder
        }
        viewHolder.title.setText(homeArticle.getMtitle());
        viewHolder.author.setText(homeArticle.getMauthor());
        viewHolder.time.setText(homeArticle.getMreleaseTime());
        viewHolder.label.setText(homeArticle.getType());
        return view;
    }

    class ViewHolder{
        TextView title,author,time,label;
    }

}
