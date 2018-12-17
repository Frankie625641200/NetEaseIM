package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class LoginActivity extends AppCompatActivity {
    EditText EID;
    EditText EC;
    TextView Tuser;
   Button send;
    private List<message> fruitList = new ArrayList<message>();

    private String[] data=new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       EID= (EditText) findViewById(R.id.editText3);
        EC= (EditText) findViewById(R.id.editText4);
       Tuser=(TextView)findViewById(R.id.textView8);

        initFruits(); // 初始化水果数据
        sendmessage adapter = new sendmessage(LoginActivity.this, R.layout.listviewitems, fruitList);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);








        Intent intent=getIntent();
        Tuser.setText(intent.getStringExtra("username"));
        Toast.makeText(LoginActivity.this,"恭喜您成功登录",Toast.LENGTH_SHORT).show();


        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);


        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        Log.i("tag", "User status changed to: " + status);

                    }
                }, true);

        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus status) {
                if (status == LoginSyncStatus.BEGIN_SYNC) {
                    Log.i("tag", "login sync data begin");
                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                    Log.i("tag", "login sync data completed");
                }
            }
        }, true);
        send=(Button)findViewById(R.id.button3);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message apple = new message(EC.getText().toString(), R.mipmap.ic_launcher);
                fruitList.add(apple);
                sendmessage adapter = new sendmessage(LoginActivity.this, R.layout.listviewitems, fruitList);
                ListView listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);
                IMMessage message = MessageBuilder.createTextMessage(
                        EID.getText().toString(), // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                        SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                        EC.getText().toString() // 文本内容
                );
// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
                NIMClient.getService(MsgService.class).sendMessage(message,false).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        Toast.makeText(LoginActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                        Observer<List<IMMessage>> incomingMessageObserver =
                                new Observer<List<IMMessage>>() {
                                    @Override
                                    public void onEvent(List<IMMessage> messages) {
                                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                                        for(IMMessage message:messages){
//                       Tuser.setText(message.getContent());
                                            message apple = new message(message.getContent(), R.mipmap.ic_launcher);
                                            fruitList.add(apple);
                                            sendmessage adapter = new sendmessage(LoginActivity.this, R.layout.listviewitems, fruitList);
                                            ListView listView = (ListView) findViewById(R.id.listview);
                                            listView.setAdapter(adapter);
                                        }
                                    }
                                };
                    }

                    @Override
                    public void onFailed(int code) {
                        Toast.makeText(LoginActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Toast.makeText(LoginActivity.this,"异常错误，错误代码为：" + exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        // 创建文本消息



//        NIMClient.getService(MsgService.class).sendMessage(message);
       // 消息支持扩展字段，扩展字段分为服务器扩展字段（ RemoteExtension ）和本地扩展字段（ LocalExtension ），最大长度1024字节。对于服务器扩展字段，该字段会发送到其他端，而本地扩展字段仅在本地有效。对于这两种扩展字段， SDK 都会存储在数据库中。示例如下：
//        IMMessage msg = MessageBuilder.createCustomMessage("");
//        Map<String, Object> data = new HashMap<>();
//        data.put("key1", "ext data");
//        data.put("key2", 2015);
//        msg.setRemoteExtension(data); // 设置服务器扩展字段

//        NIMClient.getService(MsgService.class).sendMessage(msg, false);
    }

    private void initFruits() {


//        message apple = new message("Apple", R.mipmap.ic_launcher);
//        fruitList.add(apple);




    }

    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                    for(IMMessage message:messages){
//                       Tuser.setText(message.getContent
                        message apple = new message(message.getContent(), R.mipmap.ic_launcher);
                        fruitList.add(apple);
                        sendmessage adapter = new sendmessage(LoginActivity.this, R.layout.listviewitems, fruitList);
                        ListView listView = (ListView) findViewById(R.id.listview);
                        listView.setAdapter(adapter);
                    }
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, false);
    }
}
