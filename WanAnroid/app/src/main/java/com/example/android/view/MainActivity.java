package com.example.android.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.example.android.net.LoginVerification;
import com.example.android.net.Logout;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private ProjectionFragment mProjectionFragment;
    private FragmentManager mFragmentManager;

    private TextView TextView,mTextView, mUserName,mLogin;
    private DrawerLayout mDrawerLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mCollection;
        ImageButton mib_Menu;
        ImageButton mib_Search;
        RadioButton mProjection;
        RadioButton mHome;
        RadioGroup mGroup;
        RadioButton mKnowladge;
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mGroup = (RadioGroup) findViewById(R.id.ra_group);
        mHome = (RadioButton) findViewById(R.id.home);
        mKnowladge = (RadioButton) findViewById(R.id.knowledge);
        mProjection = (RadioButton) findViewById(R.id.projection);
        mib_Search=(ImageButton)findViewById(R.id.title_search);
        mib_Menu=(ImageButton)findViewById(R.id.title_menu);
        mLogin=(TextView)findViewById(R.id.login_button);
        mCollection=(TextView)findViewById(R.id.collect_button);
        mUserName=(TextView)findViewById(R.id.user);
        mLogin.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mGroup.setOnCheckedChangeListener(this);
        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        mHomeFragment=new HomeFragment();
        fragmentTransaction.add(R.id.top_fragment,mHomeFragment);
        fragmentTransaction.commit();
        autoLogin();
        //搜索按钮的监视器
        mib_Search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开搜索的界面
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //菜单的监视器，打开菜单
        mib_Menu.setOnClickListener(new Button.OnClickListener(){
                   @Override
                   public void onClick(View v){
                       mDrawerLayout.openDrawer(GravityCompat.START);
           }
       });
    //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    //底部导航栏的监视器
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId){

        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        mTextView=findViewById(R.id.title_text);
        switch (checkedId){
            case R.id.home:
                if(mHomeFragment==null){
                    mHomeFragment=new HomeFragment();
                    fragmentTransaction.add(R.id.top_fragment,mHomeFragment);
                } else{
                    fragmentTransaction.show(mHomeFragment);
                }
                mTextView.setText("首页");
                break;
            case R.id.knowledge:
                if(mKnowledgeFragment==null){
                    mKnowledgeFragment=new KnowledgeFragment();
                    fragmentTransaction.add(R.id.top_fragment,mKnowledgeFragment);
                }else{
                    fragmentTransaction.show(mKnowledgeFragment);
                }
                mTextView.setText("体系");
                break;
            case R.id.projection:
                if(mProjectionFragment==null){
                    mProjectionFragment=new ProjectionFragment();
                    fragmentTransaction.add(R.id.top_fragment,mProjectionFragment);
                }else{
                    fragmentTransaction.show(mProjectionFragment);
                }
                mTextView.setText("项目");
                break;
        }
        fragmentTransaction.commit();
    }
    //把所有的碎片隐藏起来
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

    //菜单界面按钮的监视器
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                if(mUserName.getText().toString().equals("请先登录")){
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,1);
                }
                else{
                    new Logout(this).layout();
                    mUserName.setText("请先登录");
                    mLogin.setText("登录");
                }
                break;
            case R.id.collect_button:
                if(mUserName.getText().toString().equals("请先登录")){
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,1);
                }else{
                    Intent intent1=new Intent(MainActivity.this,CollectionActivity.class);
                    startActivity(intent1);
                }
                break;
             default:
                 break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!(data.getStringExtra("userName").equals(""))){
            loginSuccess(data.getStringExtra("userName"));
        }
    }

    public void loginSuccess(String s){
        mUserName.setText(s);
        mLogin.setText("退出登录");
    }

    //自动登录
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void autoLogin(){
        SharedPreferences pref=getSharedPreferences("password",MODE_PRIVATE);
        String username=pref.getString("username",null);
        String password=pref.getString("password",null);
        if(!(username==null||password==null)){
            new LoginVerification(this).login(username,password);
        }
    }

}
