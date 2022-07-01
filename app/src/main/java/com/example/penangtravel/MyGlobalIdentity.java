package com.example.penangtravel;

import android.app.Application;

public class MyGlobalIdentity extends Application {
    public String myIdentity;

    @Override
    public void onCreate() {
        super.onCreate();
        myIdentity = "user";
    }

    public String getMyIdentity(){
        return myIdentity;
    }

    public void setMyIdentity(String myIdentity1){
        myIdentity = myIdentity1;
    }
}
