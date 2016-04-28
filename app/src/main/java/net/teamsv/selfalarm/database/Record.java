package net.teamsv.selfalarm.database;

import io.realm.RealmObject;

/**
 * Created by Nolsigan on 16. 4. 28..
 */
public class Record extends RealmObject {

    private String name;
    
    public void setName(String in) { name = in; }
    public String getName() { return name; }
}
