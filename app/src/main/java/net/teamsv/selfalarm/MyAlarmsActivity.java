package net.teamsv.selfalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.teamsv.selfalarm.database.Alarm;

import java.util.Calendar;
import java.util.Date;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

public class MyAlarmsActivity extends Activity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set View */
        setContentView(R.layout.activity_my_alarms);

        /* realm setting */

        realm = Realm.getInstance(this);
        RealmResults<Alarm> alarms = realm
                .where(Alarm.class)
                .findAllSorted("id", Sort.ASCENDING);

        RealmAdapter realmAdapter = new RealmAdapter(this, alarms, true, true);
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(realmAdapter);

        /* Button Click Listeners */
        ImageButton recordButton = (ImageButton) findViewById(R.id.btn_record);
        ImageButton newButton = (ImageButton) findViewById(R.id.btn_new);

        recordButton.setOnClickListener(mOnClickListener);
        newButton.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    public class RealmAdapter extends RealmBasedRecyclerViewAdapter<Alarm, RealmAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {

            public TextView alarmTime;
            public ToggleButton tButton;

            public ViewHolder(FrameLayout container) {
                super(container);
                this.alarmTime = (TextView) container.findViewById(R.id.list_time);
                this.tButton = (ToggleButton) container.findViewById(R.id.list_switch);
            }

        }

        public RealmAdapter(
                Context context,
                RealmResults<Alarm> realmResults,
                boolean automaticUpdate,
                boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.list_alarms, viewGroup, false);
            ViewHolder vh = new ViewHolder((FrameLayout) v);

            return vh;
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int pos) {
            final Alarm alarm = realmResults.get(pos);
            final String strDate = alarm.getTime();
            final ToggleButton tButton = viewHolder.tButton;

            viewHolder.alarmTime.setText(strDate);
            viewHolder.tButton.setChecked(alarm.getOnoff());


            viewHolder.tButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if (tButton.isChecked()) {

                        realm.beginTransaction();
                        alarm.setOnoff(true);
                        realm.commitTransaction();

                        setAlarm(alarm.getHour(), alarm.getMinute(), alarm.getId());
                    } else {

                        realm.beginTransaction();
                        alarm.setOnoff(false);
                        realm.commitTransaction();

                        cancelAlarm(alarm.getId());
                    }
                }
            });
        }

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_new: {
                    Toast.makeText(getApplicationContext(), "fuck!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyAlarmsActivity.this, NewAlarmActivity.class);

                    startActivity(intent);
                    break;
                }
                case R.id.btn_record: {
                    Intent intent = new Intent(MyAlarmsActivity.this, NewRecordActivity.class);

                    startActivity(intent);
                    break;
                }
            }
        }
    };

    /* set alarm */
    private void setAlarm(int hour, int minute, int alarm_id) {

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

    /* reset alarm */
    private void cancelAlarm(int alarm_id) {

        AlarmManager aManage = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        aManage.cancel(pendingIntent(alarm_id));
    }

    /* set pending intent */
    private PendingIntent pendingIntent(int alarm_id) {

        Intent i = new Intent(getApplicationContext(), MyAlarmsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, alarm_id, i, PendingIntent.FLAG_UPDATE_CURRENT);

        return pi;
    }

}