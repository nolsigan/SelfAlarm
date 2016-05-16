package net.teamsv.selfalarm.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nolsigan on 16. 4. 28..
 */
public class Record extends RealmObject {

    @PrimaryKey
    private String name;

    public Record() {};
    public Record(String in) { name = in; }
    
    public void setName(String in) { name = in; }
    public String getName() { return name; }
}
