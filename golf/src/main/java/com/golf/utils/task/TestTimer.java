/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.utils.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 * Timer的主要方法有：
 * // 安排在指定的时间执行
 * void schedule(TimerTask task, Date time)
 * // 安排在指定的时间开始以重复的延时执行
 * void schedule(TimerTask task, Date firstTime, long period)
 * // 安排在指定的延迟后执行
 * void schedule(TimerTask task, long delay)
 * // 安排在指定的延迟后开始以重复的延时执行
 * void schedule(TimerTask task, long delay, long period)
 * // 安排在指定的时间开始以重复的速率执行
 * void scheduleAtFixedRate(TimerTask task, Date firstTime, long period)
 * // 安排在指定的延迟后开始以重复的速率执行
 * void scheduleAtFixedRate(TimerTask task, long delay, long period)
 * 注：重复的延时和重复的速率的区别在于，前者是在前一个任务的执行结束后间隔period时间再开始下一次执行；
 * 而scheduleAtFixedRate则是会尽量按照任务的初始时间来按照间隔period时间执行。
 * 如果一次任务执行由于某些原因被延迟了，用schedule()调度的后续任务同样也会被延迟，而用scheduleAtFixedRate()
 * 则会快速的开始两次或者多次执行，是后续任务的执行时间能够赶上来。
 * @author Thunder.Hsu 2012-12-24
 * 
 * <pre>
 */
public class TestTimer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TestTimerTask();
        timer.schedule(task, 500L, 1000L);

    }

}

class TestTimerTask extends TimerTask {
    /**
     * 此计时器任务要执行的操作。
     */
    public void run() {
        Date executeTime = new Date(this.scheduledExecutionTime());
        System.out.println("本次任务执行的时间是" + executeTime);
    }
}