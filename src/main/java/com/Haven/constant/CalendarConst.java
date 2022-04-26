package com.Haven.constant;

import lombok.Data;

import java.util.Calendar;

/**
 * 日历半常量 CalendarConst
 *
 * @author HavenJust
 * @date 19:19 周二 26 四月 2022年
 */

@Data
public class CalendarConst {

    private Calendar start;
    private Calendar end;

    public CalendarConst() {

        start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        start.set(Calendar.HOUR_OF_DAY, 12);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);

        end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.add(Calendar.DATE, 7);
    }
}
