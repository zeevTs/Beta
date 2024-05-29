package com.example.beta1.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beta1.Objs.Note;
import com.example.beta1.Objs.Notification;
import com.example.beta1.R;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Notification> notificationList;
    private LayoutInflater inflater;
    public NotificationAdapter(Context context , ArrayList<Notification> notificationList){
        this.context = context;
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return  notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_notification_layout,null);
        LinearLayout lvAnimal = view.findViewById(R.id.llNotification);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvAnimalName = view.findViewById(R.id.tvAnimalName);
        LinearLayout lvTime = view.findViewById(R.id.llTime);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvRepeatition = view.findViewById(R.id.tvRepeatition);
        tvTitle.setText(notificationList.get(position).getTitle());
        tvTitle.setText("for "+notificationList.get(position).getTitle());
        tvTime.setText(notificationList.get(position).getTimeStamp());
        if(notificationList.get(position).getRepetitive().equals("0000")) {
            tvRepeatition.setText("rep: none");
        }else{
            tvRepeatition.setText("rep: ");
        }
        return view;
    }

}
