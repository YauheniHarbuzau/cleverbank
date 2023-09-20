package ru.clevertec.cleverbank.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Утилитарный класс для представления даты и времени в формате String
 */
public class DateTimeUtil {

    public static String getDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String getDateTime(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }
}
