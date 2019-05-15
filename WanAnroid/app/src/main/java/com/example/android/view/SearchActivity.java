package com.example.android.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private ImageButton ib_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        ib_back=findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        ib_search=findViewById(R.id.title_search);
        ib_search.setOnClickListener(this);
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
            case R.id.title_search:
        }
    }
}
