package com.example.administrator.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.src;

/**
 * Created by Administrator on 2018-12-16.
 */

public class sendmessage extends ArrayAdapter {
    private final int resourceId;
    public sendmessage(Context context, int textViewResourceId, List<message> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        message message = (message) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView fruitImage = (ImageView) view.findViewById(R.id.imageView1);//获取该布局内的图片视图
        TextView fruitName = (TextView) view.findViewById(R.id.tv_userName);//获取该布局内的文本视图
        fruitImage.setImageResource(message.getImageID());//为图片视图设置图片资源
        fruitName.setText(message.getContent());//为文本视图设置文本内容
        return view;
    }
}
