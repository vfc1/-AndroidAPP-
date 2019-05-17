package com.example.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private ProjectionFragment mProjectionFragment;
    private FragmentManager mFragmentManager;

    private RadioGroup mGroup;
    private RadioButton mHome;
    private RadioButton mKnowladge;
    private RadioButton mProjection;
    private ImageButton mib_Search;
    private ImageButton mib_Menu;
    private TextView textView;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mGroup = (RadioGroup) findViewById(R.id.ra_group);
        mHome = (RadioButton) findViewById(R.id.home);
        mKnowladge = (RadioButton) findViewById(R.id.knowledge);
        mProjection = (RadioButton) findViewById(R.id.projection);
        mib_Search=(ImageButton)findViewById(R.id.title_search);
        mib_Menu=(ImageButton)findViewById(R.id.title_menu);

        mGroup.setOnCheckedChangeListener(this);
        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        mHomeFragment=new HomeFragment();
        fragmentTransaction.add(R.id.top_fragment,mHomeFragment);
        fragmentTransaction.commit();

        mib_Search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mib_Menu.setOnClickListener(new Button.OnClickListener(){
                   @Override
                   public void onClick(View v){
                       mDrawerLayout.openDrawer(GravityCompat.START);
           }
       });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId){

        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        textView=findViewById(R.id.title_text);
        switch (checkedId){
            case R.id.home:
                if(mHomeFragment==null){
                    mHomeFragment=new HomeFragment();
                    fragmentTransaction.add(R.id.top_fragment,mHomeFragment);
                } else{
                    fragmentTransaction.show(mHomeFragment);
                }
                textView.setText("首页");
                break;
            case R.id.knowledge:
                if(mKnowledgeFragment==null){
                    mKnowledgeFragment=new KnowledgeFragment();
                    fragmentTransaction.add(R.id.top_fragment,mKnowledgeFragment);
                }else{
                    fragmentTransaction.show(mKnowledgeFragment);
                }
                textView.setText("体系");
                break;
            case R.id.projection:
                if(mProjectionFragment==null){
                    mProjectionFragment=new ProjectionFragment();
                    fragmentTransaction.add(R.id.top_fragment,mProjectionFragment);
                }else{
                    fragmentTransaction.show(mProjectionFragment);
                }
                textView.setText("项目");
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(mHomeFragment!=null){
            fragmentTransaction.hide(mHomeFragment);
        }
        if(mKnowledgeFragment!=null){
            fragmentTransaction.hide(mKnowledgeFragment);
        }
        if(mProjectionFragment!=null){
            fragmentTransaction.hide(mProjectionFragment);
        }
    }
}
