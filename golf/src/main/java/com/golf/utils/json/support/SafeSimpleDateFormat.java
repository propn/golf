package com.golf.utils.json.support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.golf.utils.VerifyUtils;

public class SafeSimpleDateFormat {

    public static final SafeSimpleDateFormat defaultDateFormat = new SafeSimpleDateFormat();
    private ThreadLocal<SimpleDateFormat> threadLocal;

    public SafeSimpleDateFormat() {
        this("");
    }

    public SafeSimpleDateFormat(final SimpleDateFormat sdf) {
        if (sdf == null) {
            throw new IllegalArgumentException("SimpleDateFormat argument is null");
        }
        this.threadLocal = new ThreadLocal<SimpleDateFormat>() {
            protected SimpleDateFormat initialValue() {
                return (SimpleDateFormat) sdf.clone();
            }
        };
    }

    public SafeSimpleDateFormat(String datePattern) {
        final String p = VerifyUtils.isEmpty(datePattern) ? "yyyy-MM-dd HH:mm:ss" : datePattern;
        this.threadLocal = new ThreadLocal<SimpleDateFormat>() {
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(p);
            }
        };
    }

    public Date parse(String dateStr) {
        try {
            return getFormat().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String format(Date date) {
        return getFormat().format(date);
    }

    private DateFormat getFormat() {
        return (DateFormat) threadLocal.get();
    }

    public static void main(String[] args) {
        SafeSimpleDateFormat sdf = new SafeSimpleDateFormat();
        Calendar last = Calendar.getInstance();
        last.setTime(sdf.parse("2010-12-08 17:26:22"));
        Calendar now = Calendar.getInstance();
        System.out.println("last:\t" + last.get(Calendar.YEAR) + "\t" + last.get(Calendar.MONTH));
        System.out.println("now:\t" + now.get(Calendar.YEAR) + "\t" + now.get(Calendar.MONTH));
    }

}
