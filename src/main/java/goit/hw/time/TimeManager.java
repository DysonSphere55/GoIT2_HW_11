package goit.hw.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

public class TimeManager {
    public static String getDateTime(String timeZone) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
        String date = zonedDateTime.toString().substring(0, 10);
        String time = zonedDateTime.toString().substring(11, 19);
        return date + " " + time + " " + timeZone;
    }

    public static boolean isTimeZoneValid(String timeZone) {
        try {
            ZoneId.of(timeZone);
        } catch (ZoneRulesException e) {
            return false;
        }
        return true;
    }
}
