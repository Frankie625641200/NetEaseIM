package com.example.administrator.myapplication;

import android.app.Application;
import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.prefs.Preferences;

/**
 * Created by Administrator on 2018-12-08.
 */

public class MyAppcation extends Application {
    public void onCreate(){
        super.onCreate();
        NIMClient.init(this,null,null);
    }


}
