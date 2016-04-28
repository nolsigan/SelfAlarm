package net.teamsv.selfalarm.database;

import io.realm.RealmObject;

public class Alarm extends RealmObject {

    private int hour;
    private int minute;
    private Record record;
    private boolean onOff;

    /* set funcs */
    public void setTime(int h, int m) { hour = h; minute = m; }
    public void setRecord(Record in) { record = in; }
    //public void setRecord() { } // Select random alarm
    public void setOnoff(boolean in) { onOff = in; }

    /* get funcs */
    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public String getTime() {

        String ret = "";

        if(hour < 10) ret += " ";
        ret += String.valueOf(hour) + " : ";

        if(minute < 10) ret += " ";
        ret += String.valueOf(minute);

        return ret;
    }
    public Record getRecord() { return record; }
    public boolean getOnoff() { return onOff; }
}
