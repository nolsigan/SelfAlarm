package net.teamsv.selfalarm;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by Nolsigan on 16. 4. 22..
 */
public class AlarmListData {

    public String time;

    public boolean onOff;

    public static final Comparator<AlarmListData> ALPHA_COMPARATOR = new Comparator<AlarmListData>() {

        public final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AlarmListData lhs, AlarmListData rhs) {
            return sCollator.compare(lhs.time, rhs.time);
        }
    };
}
