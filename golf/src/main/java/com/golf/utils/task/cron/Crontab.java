package com.golf.utils.task.cron;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Crontab implements Comparable<Object>, java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 定时任务表达式
    private int[] minutes = { -1 };
    private static int minMinute = 0;
    private static int maxMinute = 59;

    private int[] hours = { -1 };
    private static int minHour = 0;
    private static int maxHour = 23;

    private int[] daysOfMonth = { -1 };
    private static int minDayOfMonth = 1;
    // maxDayOfMonth varies by month
    private int[] months = { -1 };
    private static int minMonth = 0;
    private static int maxMonth = 11;

    private int[] daysOfWeek = { -1 };
    private static int minDayOfWeek = 1;
    private static int maxDayOfWeek = 7;
    // no support for a list of years -- must be * or specified
    private int year = -1;

    // 定时任务基本信息
    private String name;
    // used to generate names if they are null
    private static int UNIQUE = 0;
    // default: true
    private boolean ringInNewThread = true;

    private boolean isRelative;
    public boolean isRepeating;
    public long alarmTime;
    private long lastUpdateTime;

    private transient CronTask task;

    /**
     * Creates a new AlarmEntry. Fixed date format: this alarm will happen once, at the timestamp given.
     * 
     * @param date the alarm date to be added.
     * @param task the alarm listener.
     * @exception Exception if the alarm date is in the past (or less than 1 second away from the current date).
     */
    public Crontab(String _name, Date _date, CronTask _listener) throws Exception {
        setName(_name);
        this.task = _listener;
        Calendar alarm = Calendar.getInstance();
        alarm.setTime(_date);
        this.minutes = new int[] { alarm.get(Calendar.MINUTE) };
        this.hours = new int[] { alarm.get(Calendar.HOUR_OF_DAY) };
        this.daysOfMonth = new int[] { alarm.get(Calendar.DAY_OF_MONTH) };
        this.months = new int[] { alarm.get(Calendar.MONTH) };
        this.year = alarm.get(Calendar.YEAR);
        this.isRepeating = false;
        this.isRelative = false;
        this.alarmTime = _date.getTime();
        checkPeriod();
    }

    /**
     * Creates a new AlarmEntry. Delay format: this alarm will happen once or repeatedly, at increments of the number of
     * minutes given.
     * 
     * @param _name keeps the alarm unique from other alarms with the same schedule, and used for debugging.
     * @param delayMinutes the alarm delay in minutes (relative to now).
     * @param isRepetitive <code>true</code> if the alarm must be reactivated, <code>false</code> otherwise.
     * @param task the alarm listener.
     * @exception Exception if the alarm date is in the past (or less than 1 second closed to the current date).
     */
    public Crontab(String _name, int _delayMinutes, boolean _isRepeating, CronTask _listener) throws Exception {

        if (_delayMinutes < 1) {
            throw new Exception();
        }

        setName(_name);
        minutes = new int[] { _delayMinutes };
        task = _listener;
        isRepeating = _isRepeating;

        isRelative = true;
        updateAlarmTime();
    }

    /**
     * <p>
     * Creates a new AlarmEntry. Basic cron format - use each field to restrict alarms to a specific minute, hour, etc.
     * OR pass in -1 to allow all values of that field.
     * </p>
     * 
     * <p>
     * Params of (30, 13, -1, -1, 2, -1, listener) schedule an alarm for 1:30pm every Monday.
     * </p>
     * 
     * <p>
     * NOTE: if both dayOfMonth and dayOfWeek are restricted, each alarm will be scheduled for the sooner match.
     * </p>
     * 
     * @param minute minute of the alarm. Allowed values 0-59.
     * @param hour hour of the alarm. Allowed values 0-23.
     * @param dayOfMonth day of month of the alarm (-1 if every day). Allowed values 1-31.
     * @param month month of the alarm (-1 if every month). Allowed values 0-11 (0 = January, 1 = February, ...).
     *            <code>java.util.Calendar</code> constants can be used.
     * @param dayOfWeek day of week of the alarm (-1 if every day). This attribute is exclusive with
     *            <code>dayOfMonth</code>. Allowed values 1-7 (1 = Sunday, 2 = Monday, ...).
     *            <code>java.util.Calendar</code> constants can be used.
     * @param year year of the alarm. When this field is not set (i.e. -1) the alarm is repetitive (i.e. it is
     *            rescheduled when reached).
     * @param task the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than 1 second away from the current date).
     */
    public Crontab(String _name, int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek, int _year,
            CronTask _listener) throws Exception {
        this(_name, new int[] { _minute }, new int[] { _hour }, new int[] { _dayOfMonth }, new int[] { _month },
                new int[] { _dayOfWeek }, _year, _listener);
    }

    /**
     * <p>
     * Creates a new AlarmEntry. Extended cron format - supports lists of values for each field, or {-1} to allow all
     * values for that field.
     * </p>
     * 
     * <p>
     * Params of (30, 13, -1, -1, 2, -1, listener) schedule an alarm for 1:30pm every Monday.
     * </p>
     * 
     * <p>
     * NOTE: if both dayOfMonth and dayOfWeek are restricted, each alarm will be scheduled for the sooner match.
     * </p>
     * 
     * @param minutes valid minutes of the alarm. Allowed values 0-59, or {-1} for all.
     * @param hours valid hours of the alarm. Allowed values 0-23, or {-1} for all.
     * @param daysOfMonth valid days of month of the alarm. Allowed values 1-31, or {-1} for all.
     * @param months valid months of the alarm. Allowed values 0-11 (0 = January, 1 = February, ...), or {-1} for all.
     *            <code>java.util.Calendar</code> constants can be used.
     * @param daysOfWeek valid days of week of the alarm. This attribute is exclusive with <code>dayOfMonth</code>.
     *            Allowed values 1-7 (1 = Sunday, 2 = Monday, ...), or {-1} for all. <code>java.util.Calendar</code>
     *            constants can be used.
     * @param year year of the alarm. When this field is not set (i.e. -1) the alarm is repetitive (i.e. it is
     *            rescheduled when reached).
     * @param task the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than 1 second away from the current date).
     */
    public Crontab(String _name, int[] _minutes, int[] _hours, int[] _daysOfMonth, int[] _months, int[] _daysOfWeek,
            int _year, CronTask _listener) throws Exception {

        setName(_name);
        minutes = _minutes;
        hours = _hours;
        daysOfMonth = _daysOfMonth;
        months = _months;
        daysOfWeek = _daysOfWeek;
        year = _year;
        task = _listener;
        isRepeating = (_year == -1);
        isRelative = false;

        updateAlarmTime();
        checkPeriod();
    }

    /**
     * Just make sure it's not null -- and if it is, make it unique.
     * 
     * @param _name
     */
    private void setName(String _name) {
        this.name = _name;
        if (this.name == null)
            this.name = "alarm" + (UNIQUE++);
    }

    public String getName() {
        return this.name;
    }

    /**
     * By default, the AlarmListeners for all alarms will be notified in the same thread (so a long-running
     * handleAlarm() implementation will cause other alarms to wait until it completes). Call this method to notify the
     * listener to this alarm in a new Thread, so other alarms won't be delayed.
     */
    public void setRingInNewThead() {
        this.ringInNewThread = true;
    }

    public boolean isRingInNewThread() {
        return this.ringInNewThread;
    }

    /**
     * Checks that alarm is not in the past, or less than 1 second away.
     * 
     * @exception Exception if the alarm date is in the past (or less than 1 second in the future).
     */
    void checkPeriod() throws Exception {
        long delay = this.alarmTime - System.currentTimeMillis();
        if (delay <= 1000) {// 最小间隔时间是1分钟
            throw new Exception();
        }
    }

    public void run() {
        task.run(this);
    }

    /**
     * Updates this alarm entry to the next valid alarm time, AFTER the current time.
     */
    public void updateAlarmTime() {
        Calendar now = Calendar.getInstance();
        if (isRelative) {
            // relative only uses minutes field, with only a single value (NOT -1)
            alarmTime = now.getTime().getTime() + (minutes[0] * 60000);
            return;
        }
        Calendar alarm = (Calendar) now.clone();
        alarm.set(Calendar.SECOND, 0);
        //
        // the updates work in a cascade -- if next minute value is in the
        // following hour, hour is incremented. If next valid hour value is
        // in the following day, day is incremented, and so on.
        //
        // increase alarm minutes
        int current = alarm.get(Calendar.MINUTE);
        int offset = 0;
        // force increment at least to next minute
        offset = getOffsetToNext(current, minMinute, maxMinute, minutes);
        alarm.add(Calendar.MINUTE, offset);

        // update alarm hours if necessary
        current = alarm.get(Calendar.HOUR_OF_DAY); // (as updated by minute shift)
        offset = getOffsetToNextOrEqual(current, minHour, maxHour, hours);
        alarm.add(Calendar.HOUR_OF_DAY, offset);

        //
        // If days of month AND days of week are restricted, we take whichever match
        // comes sooner.
        // If only one is restricted, take the first match for that one.
        // If neither is restricted, don't do anything.
        //
        if (daysOfMonth[0] != -1 && daysOfWeek[0] != -1) {
            // BOTH are restricted - take earlier match
            Calendar dayOfWeekAlarm = (Calendar) alarm.clone();
            updateDayOfWeekAndMonth(dayOfWeekAlarm);

            Calendar dayOfMonthAlarm = (Calendar) alarm.clone();
            updateDayOfMonthAndMonth(dayOfMonthAlarm);

            // take the earlier one
            if (dayOfMonthAlarm.getTime().getTime() < dayOfWeekAlarm.getTime().getTime()) {
                alarm = dayOfMonthAlarm;
            } else {
                alarm = dayOfWeekAlarm;
            }
        } else if (daysOfWeek[0] != -1) // only dayOfWeek is restricted
        {
            // update dayInWeek and month if necessary
            updateDayOfWeekAndMonth(alarm);
        } else if (daysOfMonth[0] != -1) // only dayOfMonth is restricted
        {
            // update dayInMonth and month if necessary
            updateDayOfMonthAndMonth(alarm);
        }
        // else if neither is restricted (both[0] == -1), we don't need to do anything.
        alarmTime = alarm.getTime().getTime();
        lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * daysInMonth can't use simple offsets like the other fields, because the number of days varies per month (think of
     * an alarm that executes on every 31st). Instead we advance month and dayInMonth together until we're on a matching
     * value pair.
     */
    void updateDayOfMonthAndMonth(Calendar alarm) {
        int currentMonth = alarm.get(Calendar.MONTH);
        int currentDayOfMonth = alarm.get(Calendar.DAY_OF_MONTH);
        int offset = 0;

        // loop until we have a valid day AND month (if current is invalid)
        while (!isIn(currentMonth, months) || !isIn(currentDayOfMonth, daysOfMonth)) {
            // if current month is invalid, advance to 1st day of next valid month
            if (!isIn(currentMonth, months)) {
                offset = getOffsetToNextOrEqual(currentMonth, minMonth, maxMonth, months);
                alarm.add(Calendar.MONTH, offset);
                alarm.set(Calendar.DAY_OF_MONTH, 1);
                currentDayOfMonth = 1;
            }

            // advance to the next valid day of month, if necessary
            if (!isIn(currentDayOfMonth, daysOfMonth)) {
                int maxDayOfMonth = alarm.getActualMaximum(Calendar.DAY_OF_MONTH);
                offset = getOffsetToNextOrEqual(currentDayOfMonth, minDayOfMonth, maxDayOfMonth, daysOfMonth);
                alarm.add(Calendar.DAY_OF_MONTH, offset);
            }

            currentMonth = alarm.get(Calendar.MONTH);
            currentDayOfMonth = alarm.get(Calendar.DAY_OF_MONTH);
        }
    }

    void updateDayOfWeekAndMonth(Calendar alarm) {
        int currentMonth = alarm.get(Calendar.MONTH);
        int currentDayOfWeek = alarm.get(Calendar.DAY_OF_WEEK);
        int offset = 0;

        // loop until we have a valid day AND month (if current is invalid)
        while (!isIn(currentMonth, months) || !isIn(currentDayOfWeek, daysOfWeek)) {
            // if current month is invalid, advance to 1st day of next valid month
            if (!isIn(currentMonth, months)) {
                offset = getOffsetToNextOrEqual(currentMonth, minMonth, maxMonth, months);
                alarm.add(Calendar.MONTH, offset);
                alarm.set(Calendar.DAY_OF_MONTH, 1);
                currentDayOfWeek = alarm.get(Calendar.DAY_OF_WEEK);
            }

            // advance to the next valid day of week, if necessary
            if (!isIn(currentDayOfWeek, daysOfWeek)) {
                offset = getOffsetToNextOrEqual(currentDayOfWeek, minDayOfWeek, maxDayOfWeek, daysOfWeek);
                alarm.add(Calendar.DAY_OF_YEAR, offset);
            }

            currentDayOfWeek = alarm.get(Calendar.DAY_OF_WEEK);
            currentMonth = alarm.get(Calendar.MONTH);
        }
    }

    // ----------------------------------------------------------------------
    // General utility methods
    // ----------------------------------------------------------------------

    /**
     * if values = {-1} offset is 1 (because next value definitely matches) if current < last(values) offset is diff to
     * next valid value if current >= last(values) offset is diff to values[0], wrapping from max to min
     */
    static int getOffsetToNext(int current, int min, int max, int[] values) {
        int offset = 0;
        // find the distance to the closest valid value > current (wrapping if neccessary)
        // {-1} means * -- offset is 1 because current++ is valid value
        if (values[0] == -1) {
            offset = 1;
        } else {
            // need to wrap
            if (current >= last(values)) {
                int next = values[0];
                offset = (max - current + 1) + (next - min);
            } else // current < max(values) -- find next valid value after current
            {
                findvalue: for (int i = 0; i < values.length; i++) {
                    if (current < values[i]) {
                        offset = values[i] - current;
                        break findvalue;
                    }
                }
            } // end current < max(values)
        }
        return offset;
    }

    /**
     * if values = {-1} or current is valid offset is 0. if current < last(values) offset is diff to next valid value if
     * current >= last(values) offset is diff to values[0], wrapping from max to min
     */
    static int getOffsetToNextOrEqual(int current, int min, int max, int[] values) {
        int offset = 0;
        int[] safeValues = null;

        // find the distance to the closest valid value >= current (wrapping if necessary)
        // {-1} means * -- offset is 0 if current is valid value
        if (values[0] == -1 || isIn(current, values)) {
            offset = 0;
        } else {
            safeValues = discardValuesOverMax(values, max);

            // need to wrap
            if (current > last(safeValues)) {
                int next = safeValues[0];
                offset = (max - current + 1) + (next - min);
            } else // current <= max(values) -- find next valid value
            {
                findvalue: for (int i = 0; i < values.length; i++) {
                    if (current < safeValues[i]) {
                        offset = safeValues[i] - current;
                        break findvalue;
                    }
                }
            } // end current <= max(values)
        }

        return offset;
    }

    /**
     * handles -1 in values as * and returns true otherwise returns true iff given value is in the array
     */
    static boolean isIn(int find, int[] values) {
        if (values[0] == -1) {
            return true;
        } else {
            for (int i = 0; i < values.length; i++) {
                if (find == values[i])
                    return true;
            }
            return false;
        }
    }

    /**
     * @return the last int in the array
     */
    static int last(int[] intArray) {
        return intArray[intArray.length - 1];
    }

    /**
     * Assumes inputted values are not null, have at least one value, and are in ascending order.
     * 
     * @return copy of values without any trailing values that exceed the max
     */
    static int[] discardValuesOverMax(int[] values, int max) {
        int[] safeValues = null;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                safeValues = new int[i];
                System.arraycopy(values, 0, safeValues, 0, i);
                return safeValues;
            }
        }
        return values;
    }

    private static String arrToString(int[] intArray) {
        if (intArray == null)
            return "null";
        if (intArray.length == 0)
            return "{}";

        String s = "{";
        for (int i = 0; i < intArray.length - 1; i++) {
            s += intArray[i] + ", ";
        }
        s += intArray[intArray.length - 1] + "}";

        return s;
    }

    // ----------------------------------------------------------------------
    // Comparable interface
    // ----------------------------------------------------------------------

    /**
     * Compares this AlarmEntry with the specified AlarmEntry for order. One twist -- if the alarmTime matches, this
     * alarm will STILL place itself before the other based on the lastUpdateTime. If the other alarm has been rung more
     * recently, this one should get priority.
     * 
     * @param obj the AlarmEntry with which to compare.
     * @return a negative integer, zero, or a positive integer as this AlarmEntry is less than, equal to, or greater
     *         than the given AlarmEntry.
     * @exception ClassCastException if the specified Object's type prevents it from being compared to this AlarmEntry.
     */
    @Override
    public int compareTo(Object obj) {
        Crontab other = (Crontab) obj;
        if (alarmTime < other.alarmTime)
            return -1;
        else if (alarmTime > other.alarmTime)
            return 1;
        else // alarmTime == other.alarmTime
        {
            if (lastUpdateTime < other.lastUpdateTime)
                return -1;
            else if (lastUpdateTime > other.lastUpdateTime)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Indicates whether some other AlarmEntry is "equal to" this one. This is where the name is important, since two
     * alarms can have the exact same schedule.
     * 
     * @param obj the AlarmEntry with which to compare.
     * @return <code>true if this AlarmEntry has the same name,
     * <code>alarmTime</code> AND the same schedule as the obj argument; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        Crontab entry = null;
        if (obj == null || !(obj instanceof Crontab))
            return false;
        entry = (Crontab) obj;
        return (name.equals(entry.name) && alarmTime == entry.alarmTime && isRelative == entry.isRelative
                && isRepeating == entry.isRepeating && Arrays.equals(minutes, entry.minutes)
                && Arrays.equals(hours, entry.hours) && Arrays.equals(daysOfMonth, entry.daysOfMonth)
                && Arrays.equals(months, entry.months) && Arrays.equals(daysOfWeek, entry.daysOfWeek));
    }

    /**
     * @return a string representation of this alarm.
     */
    @Override
    public String toString() {
        if (year != -1) {
            return "Alarm (" + name + ") at " + new Date(alarmTime);
        }
        StringBuffer sb = new StringBuffer("Alarm (" + name + ") params");
        sb.append(" minute=");
        sb.append(arrToString(minutes));
        sb.append(" hour=");
        sb.append(arrToString(hours));
        sb.append(" dayOfMonth=");
        sb.append(arrToString(daysOfMonth));
        sb.append(" month=");
        sb.append(arrToString(months));
        sb.append(" dayOfWeek=");
        sb.append(arrToString(daysOfWeek));
        sb.append(" (next alarm date=" + new Date(alarmTime) + ")");
        return sb.toString();
    }

    /**
     * some unit testing...
     */
    public static void main(String[] args) {

        System.out.println("GETTING OFFSETS");

        System.out.println("getOffsetToNext(3, 0, 11, new int[]{3,5,7,9}) = "
                + getOffsetToNext(3, 0, 11, new int[] { 3, 5, 7, 9 }));
        System.out.println("getOffsetToNextOrEqual(3, 0, 11, new int[]{3,5,7,9}) = "
                + getOffsetToNextOrEqual(3, 0, 11, new int[] { 3, 5, 7, 9 }));

        System.out.println();
        System.out.println("getOffsetToNext(9, 0, 11, new int[]{3,5,7,9}) = "
                + getOffsetToNext(9, 0, 11, new int[] { 3, 5, 7, 9 }));
        System.out.println("getOffsetToNextOrEqual(9, 0, 11, new int[]{3,5,7,9}) = "
                + getOffsetToNextOrEqual(9, 0, 11, new int[] { 3, 5, 7, 9 }));

        System.out.println();
        System.out.println("getOffsetToNext(0, 0, 11, new int[]{0}) = " + getOffsetToNext(0, 0, 11, new int[] { 0 }));
        System.out.println("getOffsetToNextOrEqual(0, 0, 11, new int[]{0}) = "
                + getOffsetToNextOrEqual(0, 0, 11, new int[] { 0 }));

        System.out.println();
        System.out.println("getOffsetToNext(5, 0, 11, new int[]{5}) = " + getOffsetToNext(5, 0, 11, new int[] { 5 }));
        System.out.println("getOffsetToNextOrEqual(5, 0, 11, new int[]{5}) = "
                + getOffsetToNextOrEqual(5, 0, 11, new int[] { 5 }));

        System.out.println();
        System.out.println("getOffsetToNext(0, 0, 11, new int[]{-1}) = " + getOffsetToNext(0, 0, 11, new int[] { -1 }));
        System.out.println("getOffsetToNextOrEqual(0, 0, 11, new int[]{-1}) = "
                + getOffsetToNextOrEqual(0, 0, 11, new int[] { -1 }));

        System.out.println();

        System.out.println();
        System.out.println("discardValuesOverMax(new int[]{0,1,2,3,4,5,6}, 4)) = "
                + arrToString(discardValuesOverMax(new int[] { 0, 1, 2, 3, 4, 5, 6 }, 4)));
        System.out.println("discardValuesOverMax(new int[]{0,1,2,3,4,5,6}, 6)) = "
                + arrToString(discardValuesOverMax(new int[] { 0, 1, 2, 3, 4, 5, 6 }, 6)));
        System.out.println("discardValuesOverMax(new int[]{0,1,2,3,4,5,6}, 0)) = "
                + arrToString(discardValuesOverMax(new int[] { 0, 1, 2, 3, 4, 5, 6 }, 0)));
        System.out.println("discardValuesOverMax(new int[]{0,1,2,3,4,5,6}, 7)) = "
                + arrToString(discardValuesOverMax(new int[] { 0, 1, 2, 3, 4, 5, 6 }, 7)));
    }
}
