package net.teamsv.selfalarm.database;

import java.util.Date;

import io.realm.RealmObject;

public class Alarm extends RealmObject {

    private Date date;
    private Record record;
    private boolean onOff;

    /* set funcs */
    public void setDate(Date in) { date = in; }
    public void setRecord(Record in) { record = in; }
    public void setOnoff(boolean in) { onOff = in; }

    /* get funcs */
    public Date getDate() { return date; }
    public Record getRecord() { return record; }
    public boolean getOnoff() { return onOff; }
}
