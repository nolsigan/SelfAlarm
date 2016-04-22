package net.teamsv.selfalarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MyAlarmsActivity extends Activity {

    private ListView mListView = null;
    private ListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /* Set View */

        setContentView(R.layout.activity_my_alarms);

        mListView = (ListView) findViewById(R.id.alarm_list);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);


        /* Set click listener */

        ImageButton addButton = (ImageButton) findViewById(R.id.btn_new);
        ImageButton recordButton = (ImageButton) findViewById(R.id.btn_alarm);

        addButton.setOnClickListener(mOnClickListener);
        addButton.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_new :
                    // TODO : Intent to new alarm activity
                    break;

                case R.id.btn_alarm :
                    // TODO : Intent to new record activity
                    break;
            }
        }
    };

    private class ViewHolder {

        public TextView time;
        public ToggleButton onOff;
    }


    private class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<AlarmListData> mListData = new ArrayList<AlarmListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_alarms, null);

                holder.time = (TextView) convertView.findViewById(R.id.list_time);
                holder.onOff = (ToggleButton) convertView.findViewById(R.id.list_switch);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AlarmListData mData = mListData.get(position);

            holder.time.setText(mData.time);
            holder.onOff.setChecked(mData.onOff);

            return convertView;
        }

    }
}