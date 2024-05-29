package com.example.beta1.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        String title = intent.getStringExtra("title");
        NotificationHelper.showNotificationBtn(context,msg,title);
    }
}