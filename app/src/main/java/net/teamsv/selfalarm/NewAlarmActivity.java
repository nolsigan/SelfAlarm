package net.teamsv.selfalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import net.teamsv.selfalarm.database.Alarm;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class NewAlarmActivity extends Activity {

    private Realm realm;
    private TimePicker tp;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);


        /* set realm instance */
        realm = Realm.getInstance(this);


        /* set onClickListeners */
        TextView regButton = (TextView) findViewById(R.id.regButton);
        TextView cancelButton = (TextView) findViewById(R.id.regCancelButton);

        regButton.setOnClickListener(mOnClickListener);
        cancelButton.setOnClickListener(mOnClickListener);
        tp = (TimePicker) findViewById(R.id.timePicker);


        /* set timepicker listener */

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int m) {

                hour = hourOfDay;
                minute = m;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.regButton :

                    realm.beginTransaction();
                    Alarm newAlarm = realm.createObject(Alarm.class);
                    newAlarm.setTime(hour, minute);
                    newAlarm.setOnoff(true);
                    newAlarm.setId((int) System.currentTimeMillis());
                    realm.commitTransaction();

                    setAlarm(newAlarm.getId());

                    finish();

                    break;

                case R.id.regCancelButton :

                    finish();
                    break;
            }
        }
    };


    /* set alarm */
    private void setAlarm(int alarm_id) {

        /* calculate time diff */

        Calendar c = Calendar.getInstance();
        Date curDate = c.getTime();

        int curH = curDate.getHours();
        int curM = curDate.getMinutes();
        int calM = (hour - curH) * 60 + minute - curM;


        if (curH * 60 + curM > hour * 60 + minute) calM = 24 * 60 - calM;


        /* set alarm */

        AlarmManager aManage = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        aManage.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + calM * 60 * 1000, pendingIntent(alarm_id));
    }


    /* set pending intent */
    private PendingIntent pendingIntent(int alarm_id) {

        Intent i = new Intent(getApplicationContext(), MyAlarmsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, alarm_id, i, PendingIntent.FLAG_UPDATE_CURRENT);

        return pi;
    }


}
