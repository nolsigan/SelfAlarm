package net.teamsv.selfalarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import net.teamsv.selfalarm.database.Alarm;

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
                    newAlarm.setId(System.currentTimeMillis());
                    realm.commitTransaction();

                    finish();

                    break;

                case R.id.regCancelButton :

                    finish();
                    break;
            }
        }
    };
}
