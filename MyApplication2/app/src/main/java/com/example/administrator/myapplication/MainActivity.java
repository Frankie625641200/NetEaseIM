package com.example.administrator.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{
    EditText username;
    EditText password;
    TextView check;
    Button login;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();

        //NIMClient.init(this,null,null);

        // ... your codes
//        if (NIMUtil.isMainProcess(this)) {
//            // 注意：以下操作必须在主进程中进行
//            // 1、UI相关初始化操作
//            // 2、相关Service调用
//        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    private void findview() {
        username= (EditText) findViewById(R.id.editText);
        password= (EditText) findViewById(R.id.editText2);
        login= (Button) findViewById(R.id.button);
        reg= (Button) findViewById(R.id.button2);
        check= (TextView) findViewById(R.id.textView4);
    }

    public void doLogin() {
        LoginInfo info = new LoginInfo(username.getText().toString(),password.getText().toString()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        i.putExtra("username", username.getText().toString());
                        startActivity(i);
                    }

                    @Override
                    public void onFailed(int code) {
                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(MainActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }


}
