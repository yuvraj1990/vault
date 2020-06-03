package com.proz.vault.helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yubraj.singh
 */

public class DateTimeHelper {
    static SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public  static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }

    public  static String formateDate(Date date) {
      return oldFormat.format(date);
    }
}
