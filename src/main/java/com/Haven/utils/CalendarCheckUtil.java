package com.Haven.utils;

import com.Haven.constant.CalendarConst;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

/**
 * 检查最后时间 CalendarCheckUtil
 *
 * @author HavenJust
 * @date 20:42 周二 26 四月 2022年
 */

public class CalendarCheckUtil {

    public static boolean checkTime(LocalDateTime time) {

        Calendar curCalendar = Calendar.getInstance(Locale.CHINA);

        curCalendar.setTime(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));

        return curCalendar.after(new CalendarConst().getStart()) && curCalendar.before(new CalendarConst().getEnd());
    }
}
