package com.golf.utils.task.cron;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CronManager {

    private Waiter waiter;
    private SortedSet /* of AlarmEntry */<Crontab> queue;

    /**
     * Creates a new AlarmManager. The waiter thread will be started only when the first alarm listener will be added.
     * 
     * @param isDaemon true if the waiter thread should run as a daemon.
     * @param threadName the name of the waiter thread
     */
    public CronManager(boolean isDaemon, String threadName) {
        queue = new TreeSet<Crontab>();
        waiter = new Waiter(this, isDaemon, threadName);
    }

    /**
     * Creates a new AlarmManager. The waiter thread will be started only when the first alarm listener will be added.
     * The waiter thread will <i>not</i> run as a daemon.
     */
    public CronManager() {
        this(false, "AlarmManager");
    }

    /**
     * Adds an alarm for a specified date.
     * 
     * @param date the alarm date to be added.
     * @param listener the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past or less than 1 second closed to the current date).
     */
    public synchronized Crontab add(String _name, Date _date, CronTask _listener) throws Exception {
        Crontab entry = new Crontab(_name, _date, _listener);
        add(entry);
        return entry;
    }

    /**
     * Adds an alarm for a specified delay.
     * 
     * @param delay the alarm delay in minute (relative to now).
     * @param isRepeating <code>true</code> if the alarm must be reactivated, <code>false</code> otherwise.
     * @param listener the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than 1 second closed to the current date).
     */
    public synchronized Crontab add(String _name, int _delay, boolean _isRepeating, CronTask _listener)
            throws Exception {
        Crontab entry = new Crontab(_name, _delay, _isRepeating, _listener);
        add(entry);
        return entry;
    }

    /**
     * Adds an alarm for a specified date.
     * 
     * @param minute minute of the alarm. Allowed values 0-59, or -1 for all.
     * @param hour hour of the alarm. Allowed values 0-23, or -1 for all.
     * @param dayOfMonth day of month of the alarm. Allowed values 1-7 (1 = Sunday, 2 = Monday, ...), or -1 for all.
     *            <code>java.util.Calendar</code> constants can be used.
     * @param month month of the alarm. Allowed values 0-11 (0 = January, 1 = February, ...), or -1 for all.
     *            <code>java.util.Calendar</code> constants can be used.
     * @param dayOfWeek day of week of the alarm. Allowed values 1-31, or -1 for all.
     * @param year year of the alarm. When this field is not set (i.e. -1) the alarm is repetitive (i.e. it is
     *            rescheduled when reached).
     * @param listener the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than 1 second away from the current date).
     */
    public synchronized Crontab add(String _name, int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek,
            int _year, CronTask _listener) throws Exception {

        Crontab entry = new Crontab(_name, _minute, _hour, _dayOfMonth, _month, _dayOfWeek, _year, _listener);
        add(entry);
        return entry;
    }

    /**
     * Adds an alarm for a specified date or matching dates (for unrestricted fields).
     * 
     * @param minutes minutes of the alarm. Allowed values 0-59, or -1 for all.
     * @param hours hours of the alarm. Allowed values 0-23, or -1 for all.
     * @param daysOfMonth days of month of the alarm. Allowed values 1-7 (1 = Sunday, 2 = Monday, ...), or -1 for all.
     *            <code>java.util.Calendar</code> constants can be used.
     * @param months months of the alarm. Allowed values 0-11 (0 = January, 1 = February, ...), or -1 for all.
     *            <code>java.util.Calendar</code> constants can be used.
     * @param daysOfWeek days of week of the alarm. Allowed values 1-31, or -1 for all.
     * @param year year of the alarm. When this field is not set (i.e. -1) the alarm is repetitive (i.e. it is
     *            rescheduled when reached).
     * @param listener the alarm listener.
     * @return the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than 1 second away from the current date).
     */
    public synchronized Crontab add(String _name, int[] _minutes, int[] _hours, int[] _daysOfMonth, int[] _months,
            int[] _daysOfWeek, int _year, CronTask _listener) throws Exception {

        Crontab entry = new Crontab(_name, _minutes, _hours, _daysOfMonth, _months, _daysOfWeek, _year, _listener);
        add(entry);
        return entry;
    }

    /**
     * Adds an alarm for a specified AlarmEntry
     * 
     * @param entry the AlarmEntry.
     * @exception Exception if the alarm date is in the past (or less than one second away from the current date).
     */
    public synchronized void add(Crontab _entry) throws Exception {
        queue.add(_entry);
        if (queue.first().equals(_entry)) {
            waiter.update(_entry.alarmTime);
        }
    }

    /**
     * Removes the specified AlarmEntry.
     * 
     * @param entry the AlarmEntry that needs to be removed.
     * @return <code>true</code> if there was an alarm for this date, <code>false</code> otherwise.
     */
    public synchronized boolean remove(Crontab _entry) {
        boolean found = false;
        if (!queue.isEmpty()) {
            Crontab was_first = queue.first();
            found = queue.remove(_entry);
            // update the queue if it's not now empty, and the first alarm has changed
            if (!queue.isEmpty() && _entry.equals(was_first)) {
                waiter.update(queue.first().alarmTime);
            }
        }
        return found;
    } // removeAlarm()

    /**
     * Removes all the alarms. No more alarms, even newly added ones, will be fired.
     */
    public synchronized void removeAll() {
        queue.clear();
    }

    /**
     * Removes all the alarms. No more alarms, even newly added ones, will be fired.
     */
    public synchronized void removeAllAndStop() {
        waiter.stop();
        waiter = null;
        queue.clear();
    }

    public boolean isStopped() {
        return (waiter == null);
    }

    /**
     * Tests whether the supplied AlarmEntry is in the manager.
     * 
     * @param Crontab
     * @return boolean whether AlarmEntry is contained within the manager
     */
    public synchronized boolean contains(Crontab _alarmEntry) {
        return queue.contains(_alarmEntry);
    }

    /**
     * Returns a copy of all alarms in the manager.
     */
    public synchronized List<Crontab> getAll() {
        List<Crontab> result = new ArrayList<Crontab>();
        Iterator<Crontab> iterator = queue.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * <pre>
     * This is method is called when an alarm date is reached. 
     * It is only be called by the the AlarmWaiter or by itself
     * (if the next alarm is less than 1 second away).
     * </pre>
     */
    protected synchronized void runNext() {
        // if the queue is empty, there's nothing to do
        if (queue.isEmpty()) {
            return;
        }
        // Removes this alarm and notifies the listener
        Crontab entry = queue.first();
        queue.remove(entry);

        // NOTE: if the entry is still running when its next alarm time comes up,
        // that execution of the entry will be skipped.
        if (entry.isRingInNewThread()) {
            new Thread(new RunnableTasker(entry)).start();
        } else {
            // ring in same thread, sequentially.. can delay other alarms
            try {
                entry.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Reactivates the alarm if it is repetitive
        if (entry.isRepeating) {
            entry.updateAlarmTime();
            queue.add(entry);
        }

        // Notifies the AlarmWaiter thread for the next alarm
        if (queue.isEmpty()) {
        } else {
            long alarmTime = queue.first().alarmTime;
            if (alarmTime - System.currentTimeMillis() < 1000) {
                runNext();
            } else {
                waiter.restart(alarmTime);
            }
        }
    } // notifyListeners()

    /**
     * Stops the waiter thread before ending.
     */
    @Override
    public void finalize() {
        if (waiter != null)
            waiter.stop();
    }

    /**
     * Used to ring an AlarmEntry in a new Thread.
     * 
     * @see com.golf.utils.task.cron.Crontab#setRingInNewThread()
     */
    private class RunnableTasker implements Runnable {
        Crontab entry = null;

        RunnableTasker(Crontab _entry) {
            entry = _entry;
        }

        @Override
        public void run() {
            try {
                entry.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
