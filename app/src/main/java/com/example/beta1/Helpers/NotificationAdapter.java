package com.example.beta1.Helpers;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refUsers;
import static com.example.beta1.Helpers.Utilities.db2Dsiplay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        ImageButton btnTrash = view.findViewById(R.id.btnTrash);
        btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cancelAlarm(notificationList.get(position).getArq());
            notificationList.remove(position);
            user.setNotifications(notificationList);
            refUsers.child(user.getuId()).setValue(user);
            notifyDataSetChanged();
            }
        });
        ImageButton btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout lvAnimal = view.findViewById(R.id.llNotification);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvAnimalName = view.findViewById(R.id.tvAnimalName);
        LinearLayout lvTime = view.findViewById(R.id.llNameRep);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvRepeatition = view.findViewById(R.id.tvRepeatition);
        String notificationAnimalID = notificationList.get(position).getAnimalId();
        tvAnimalName.setText(user.getAnimal(notificationAnimalID).getName());
        tvTitle.setText(notificationList.get(position).getTitle());
        tvTime.setText(db2Dsiplay(notificationList.get(position).getTimeStamp()));
        if(notificationList.get(position).getRepetitive().equals("0000")) {
            tvRepeatition.setText("rep: none");
        }else{
            tvRepeatition.setText("rep: "+ notificationList.get(position).getRepetitive());
        }

        return view;
    }
    public void cancelAlarm(int arq) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                arq, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmIntent);
    }

}
