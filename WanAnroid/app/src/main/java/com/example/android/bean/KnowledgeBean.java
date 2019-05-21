package com.example.android.bean;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeBean {

    private List<String> mID=new ArrayList<>();
    private String mFatherName;
    private List<String> mChildName=new ArrayList<>();

    public void setMfatherName(String mfatherName) {
        this.mFatherName = mfatherName;
    }

    public String getMfatherName() {
        return mFatherName;
    }

    public void setmChildName(String mChildName) {
        this.mChildName.add(mChildName);
    }

    public List<String> getmChildName() {
        return mChildName;
    }

    public void setmID(String mID) {
        this.mID.add(mID);
    }

    public List<String> getmID() {
        return mID;
    }

}
