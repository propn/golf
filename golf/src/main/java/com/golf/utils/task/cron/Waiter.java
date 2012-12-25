package com.golf.utils.task.cron;

public class Waiter implements Runnable {

    protected CronUtils mgr;
    protected Thread thread;
    private long sleepUntil = -1;
    private boolean shutdown = false;

    /**
     * Creates a new AlarmWaiter.
     * 
     * @param isDaemon true if the waiter thread should run as a daemon.
     * @param threadName the name of the waiter thread
     */
    public Waiter(CronUtils mgr, boolean isDaemon, String waiterName) {
        this.mgr = mgr;
        // start the thread
        thread = new Thread(this, waiterName);
        thread.setPriority(1);
        thread.setDaemon(isDaemon);
        thread.start();
    }

    /**
     * Updates the time to sleep.
     * 
     * @param _sleep_until the new time to sleep until.
     */
    public synchronized void update(long _sleep_until) {
        this.sleepUntil = _sleep_until;
        notify();
    }

    /**
     * Restarts the thread for a new time to sleep until.
     * 
     * @param _sleep_until the new time to sleep until.
     */
    public synchronized void restart(long _sleep_until) {
        this.sleepUntil = _sleep_until;
        notify();
    }

    /**
     * Stops (destroy) the thread.
     */
    public synchronized void stop() {
        shutdown = true;
        notify();
    }

    @Override
    public synchronized void run() {
        while (!shutdown) {
            try {
                // check if there's an alarm scheduled
                if (sleepUntil <= 0) {
                    // no alarm. Wait for a new alarm to come along.
                    wait();
                } // if
                else {
                    // Found alarm, set timeout based on alarm time
                    long timeout = sleepUntil - System.currentTimeMillis();
                    if (timeout > 0) {
                        wait(timeout);
                    }
                }
                // now that we've awakened again, check if an alarm is due (within 1 second or already past)
                if (sleepUntil >= 0 && (sleepUntil - System.currentTimeMillis() < 1000)) {
                    // yes, an alarm is ready (or already past). Notify the manager to ring it.
                    sleepUntil = -1;
                    mgr.runNext();
                }
            } catch (InterruptedException e) {
            }
        }
    }

}
