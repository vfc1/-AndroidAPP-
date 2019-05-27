package com.example.android.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.net.LoginVerification;
import com.example.android.net.Register;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserNamw,mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mLogin,mRegister;
        mUserNamw=(EditText)findViewById(R.id.user_name);
        mPassword=(EditText)findViewById(R.id.editTextPassWord);
        mLogin=(Button)findViewById(R.id.login);
        mRegister=(Button)findViewById(R.id.register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String userName,password;
                if((userName=mUserNamw.getText().toString()).equals("")|(password=mPassword.getText().toString()).equals("")){
                    Toast.makeText(this,"请输入后再登录",Toast.LENGTH_LONG).show();
                }else{
                    new LoginVerification(this).login(userName,password);
                }
                break;
            case R.id.register:
                showAddDialog();
                break;
        }

    }

    //弹出注册的框
    protected void showAddDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.dialog, null);
        final EditText editTextName = (EditText) textEntryView.findViewById(R.id.editTextName);
        final EditText editTextPass = (EditText)textEntryView.findViewById(R.id.editTextNum);
        final EditText editTextAgain=(EditText)textEntryView.findViewById(R.id.again) ;
        AlertDialog.Builder ad1 = new AlertDialog.Builder(LoginActivity.this);
        ad1.setTitle("注册");
        ad1.setView(textEntryView);
        ad1.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                String userName=editTextName.getText().toString();
                String passWord=editTextPass.getText().toString();
                String rePassWord=editTextAgain.getText().toString();
                if(userName.equals("")||passWord.equals("")||rePassWord.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入后再操作",Toast.LENGTH_SHORT).show();
                }else{new Register(LoginActivity.this,userName,passWord,rePassWord);
                }
            }
        });
        ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) { }
        });
        ad1.show();// 显示对话框
    }

    public void toastShow(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    //显示用户名
    public void setText(String s){
        Intent intent=new Intent();
        intent.putExtra("userName",s);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override

    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("userName","");
        setResult(RESULT_OK,intent);
        finish();
    }

}
