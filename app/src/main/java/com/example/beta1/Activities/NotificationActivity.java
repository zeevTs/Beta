package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refUsers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beta1.Helpers.AlarmReceiver;
import com.example.beta1.Objs.Notification;
import com.example.beta1.R;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {
    private ArrayAdapter<String > adpRep;
    private Calendar calSet, calNow;
    private int ALARM_RQST_CODE = 1;
    private int value = 0;
    private TextView tvRep;
    private LinearLayout lnRep;
    private String rep = "0000";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Intent gI;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initViews();

    }

    private void initViews() {
        gI = getIntent();
        lnRep = findViewById(R.id.lnRep);
        calNow = Calendar.getInstance();
        calSet = (Calendar) calNow.clone();
        tvRep = findViewById(R.id.tvRep);
        Spinner spRep = findViewById(R.id.spRep);
        CheckBox cB = findViewById(R.id.cBrepetitive);
        cB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                   lnRep.setVisibility(View.VISIBLE);
                   String[] listRep = {"choose repetition" ,"Hours" ,"Days","weeks","months"};
//                    ArrayList<String> listRep = new ArrayList<>();
//                    listRep.add("choose repetition");
//                    listRep.add("Hours");
//                    listRep.add("Days");
//                    listRep.add("weeks");
//                    listRep.add("months");
                    adpRep = new ArrayAdapter<String>(NotificationActivity.this, android.R.layout.simple_spinner_item,listRep);
                    adpRep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRep.setAdapter(adpRep);
                    spRep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position != 0){
                                pos = position-1;
                            }else{
                                Toast.makeText(NotificationActivity.this, "you must select repetitive rate", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else {
                    lnRep.setVisibility(View.GONE);
                }
            }
        });

    }

    private void openDatePicker(){

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calSet.set(year,month,day);
            }
        }, 2024,0,0);
        dialog.setTitle("Choose Date");
        dialog.show();
    }
    private void openTimePicker(boolean is24){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calSet.get(Calendar.HOUR_OF_DAY),
                calSet.get(Calendar.MINUTE),is24);
        timePickerDialog.setTitle("Choose Time");
        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        /**
         * onTimeSet method
         * <p> Return the time of day picked by the user
         * </p>
         *
         * @param view the time picker view that triggered the method
         * @param hourOfDay the hour the user picked
         * @param minute the minute the user picked
         */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0 ) {
                calSet.add(Calendar.DATE, 1);
            }

        }
    };

    public void setNotiDate(View view) {
        openDatePicker();
    }

    public void setNotiTime(View view) {
         openTimePicker(true);
    }

    public void setNotification(View view) {

        String data = getIntent().getExtras().getString("Data","");
        String title = getIntent().getExtras().getString("Title","");
        String animalID = getIntent().getExtras().getString("AnimalId","");
        String notiId = getIntent().getExtras().getString("NotiId","");
        rep= rep.substring(0,pos)+ value + rep.substring(pos+1);
        String timeStamp = ""+calSet.getTime();
        Notification notification = new Notification(title,data,animalID,notiId,rep,timeStamp);
        ArrayList<Notification> notifications= user.getNotifications();
        notifications.add(notification);
        user.setNotifications(notifications);
        refUsers.child(user.getuId()).setValue(user);

        if(rep.equals("0000")){
            ALARM_RQST_CODE++;
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("msg",data);
            intent.putExtra("title",title);
            alarmIntent = PendingIntent.getBroadcast(this,
                    ALARM_RQST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.set(AlarmManager.RTC_WAKEUP,
                    calSet.getTimeInMillis(), alarmIntent);

        }else {
            ALARM_RQST_CODE++;
            Intent intent = new Intent(this, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(this,
                    ALARM_RQST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
            alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calSet.getTimeInMillis(), (rep.charAt(3)-48)*1000*60*60 + (rep.charAt(2)-48)*1000*60*60*24 + (rep.charAt(1)-48)*1000*60*60*24*7 + (rep.charAt(0)-48)*1000*60*60*24*30 , alarmIntent);
        }
        Toast.makeText(this, "Alarm in " + String.valueOf(calSet.getTime()), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK,gI);
        finish();
    }

    public void plus(View view) {
         if(value<9) {
             value++;
             tvRep.setText("" + value);
         }else{
             Toast.makeText(this, "the max amount is 9", Toast.LENGTH_SHORT).show();
         }
    }

    public void minus(View view) {
         if(value>0) {
             value--;
             tvRep.setText("" + value);
         }else{
             Toast.makeText(this, "the min amount is 0", Toast.LENGTH_SHORT).show();
         }
    }
}