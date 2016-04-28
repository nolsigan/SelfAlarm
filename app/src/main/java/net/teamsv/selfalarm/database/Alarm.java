package net.teamsv.selfalarm.database;

import java.util.Date;

import io.realm.RealmObject;

public class Alarm extends RealmObject {

    private Date date;
    private Record record;

    public void setDate(Date in) { date = in; }
    public void setRecord(Record in) { record = in; }
    public Date getDate() { return date; }
    public Record getRecord() { return record; }
}
