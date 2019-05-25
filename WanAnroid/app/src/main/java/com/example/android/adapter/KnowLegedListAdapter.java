package com.example.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.KnowledgeBean;
import com.example.android.view.KnowLedgeDetailActivity;
import com.example.android.widget.MyViewGroup;

import java.util.ArrayList;
import java.util.List;

public class KnowLegedListAdapter extends ArrayAdapter<KnowledgeBean>  {

    private int resourceId;
    private List<String> childName;
    private List<String> ID;
    private int i;
    private List<List<Button>> mLists=new ArrayList<>();//储存加载完的按钮，下次直接使用，减少卡顿

    public KnowLegedListAdapter(Context context, int textViewResourceId, List<KnowledgeBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
        }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        KnowledgeBean knowledgeBean=getItem(position);
        ViewHolder viewHolder;
        childName=knowledgeBean.getmChildName();
        ID=knowledgeBean.getmID();
        final View view ;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)view.findViewById(R.id.kind);
            viewHolder.myViewGroup=(MyViewGroup)view.findViewById(R.id.my_viewgroup);
            viewHolder. myViewGroup.setSpacing(10,10,10,10);
            viewHolder.myViewGroup.setViewPadding(20,20,20,20);
            view.setTag(viewHolder);//将viewholder存储在view中
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();//将viewholder取出
        }
        viewHolder.textView.setText(knowledgeBean.getMfatherName());
        viewHolder.myViewGroup.removeAllViews();
        if(mLists.size()>position){
            List<Button> buttons=mLists.get(position);
            for(int i=0;i<buttons.size();i++){
                viewHolder.myViewGroup.addView(buttons.get(i));
            }
        }
        else{
            List<Button> buttons=new ArrayList<>();
            for(i=0;i<childName.size();i++){
                Button button=new Button(getContext());
                button.setText(childName.get(i));
                button.setTextSize(12);
                button.setTextColor(view.getResources().getColor(R.color.colorOrange));
                button.setMinWidth(0);//Button中的方法    改变Button(TextView)中的mMinWidth
                button.setMinHeight(0);//Button中的方法   改变Button(TextView)中的mMinHeight
                button.setMinimumHeight(0);//View中的方法 改变View中的mMinHeight
                button.setMinimumWidth(0);//View中的方法  改变View中的mMinWidth
                button.setMaxLines(1);//设置最大行数只能为一
                //为按钮设置背景图片
                Resources resources = getContext().getResources();
                Drawable drawable = resources.getDrawable(R.drawable.rectangle);
                button.setBackground(drawable);
                //防止按钮强了listview的焦点
                button.setFocusable(false);
                final Intent intent=new Intent(getContext(), KnowLedgeDetailActivity.class);
                intent.putStringArrayListExtra("childName",(ArrayList<String>)childName);
                intent.putStringArrayListExtra("ID",(ArrayList<String>) ID);
                intent.putExtra("where",i);
                intent.putExtra("title",knowledgeBean.getMfatherName());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        getContext().startActivity(intent);
                    }
                });
                viewHolder.myViewGroup.addView(button);
                buttons.add(button);
            }
            mLists.add(buttons);
        }
        return view;
    }

    class ViewHolder{
        TextView textView;
        MyViewGroup myViewGroup;
    }
}
