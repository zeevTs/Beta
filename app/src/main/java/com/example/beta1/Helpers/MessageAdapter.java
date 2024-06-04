package com.example.beta1.Helpers;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.Utilities.db2Dsiplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beta1.Objs.Message;
import com.example.beta1.Objs.User;
import com.example.beta1.R;


import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Message> messageList;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.forum_custom_layout,null);
        RelativeLayout rlPost = view.findViewById(R.id.rlPost);
        TextView tvPostTitle = view.findViewById(R.id.tvPostTitle);
        TextView tvPostText = view.findViewById(R.id.tvPostText);
        TextView tvPostTime = view.findViewById(R.id.tvPostTime);
        TextView tvPostUserName = view.findViewById(R.id.tvPostUserName);
        tvPostTitle.setText(messageList.get(position).getTitle());
        tvPostText.setText(messageList.get(position).getData());
        tvPostTime.setText(db2Dsiplay(messageList.get(position).getTime()));
        tvPostUserName.setText(user.getName());
        return view;
    }
}
