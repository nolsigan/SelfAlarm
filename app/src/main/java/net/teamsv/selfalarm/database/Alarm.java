package net.teamsv.selfalarm.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Nolsigan on 16. 4. 21..
 */

@Table(name = "Alarms")
public class Alarm extends Model{

    /* attributes */

    @Column(name = "Name", unique = true)
    public String name;

    @Column(name = "Path")
    public String path;

    @Column(name = "Created_at", index = true)
    public Date created_at;


    /* constructors */

    public Alarm() {
        super();
    }

    public Alarm(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }

}
