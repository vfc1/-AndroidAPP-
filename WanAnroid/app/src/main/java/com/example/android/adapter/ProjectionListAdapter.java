package com.example.android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.bean.ProjectionArticleBean;
import com.example.android.presenter.ProjectionPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectionListAdapter extends ArrayAdapter<ProjectionArticleBean> {

    private int resourceId;
    private Map<Integer,Drawable> map=new HashMap<>();
    private ProjectionPresenter mPresenter;
    //j记录第几个listview
    private int j;



    public ProjectionListAdapter(Context context, int resource, List<ProjectionArticleBean> objects,ProjectionPresenter presenter,int j) {
        super(context, resource, objects);

        this.mPresenter=presenter;
        resourceId=resource;
        this.j=j;
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
        if(map.get(position)!=null){
            picture.setImageDrawable(map.get(position));
        }else{
            mPresenter.loadPhoto(articleBean.getmPictureLink(),map,position,j);
        }
        return view;
    }
}
