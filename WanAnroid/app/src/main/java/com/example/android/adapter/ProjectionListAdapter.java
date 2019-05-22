package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.bean.ProjectionArticleBean;

import java.util.List;

public class ProjectionListAdapter extends ArrayAdapter<ProjectionArticleBean> {

    private int resourceId;

    public ProjectionListAdapter( Context context, int resource,  List<ProjectionArticleBean> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ProjectionArticleBean articleBean=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView title=(TextView) view.findViewById(R.id.title);
        TextView content=(TextView)view.findViewById(R.id.content);
        TextView author=(TextView)view.findViewById(R.id.author);
        TextView time=(TextView)view.findViewById(R.id.time);
        ImageView picture=(ImageView)view.findViewById(R.id.picture);
        title.setText(articleBean.getmTitle());
        content.setText(articleBean.getmContent());
        author.setText(articleBean.getmAuthor());
        time.setText(articleBean.getmTime());
        return view;
    }
}
