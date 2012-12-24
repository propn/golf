package com.golf.utils.task.cron;

public interface CronTask {
    public abstract void run(Crontab entry);
}
