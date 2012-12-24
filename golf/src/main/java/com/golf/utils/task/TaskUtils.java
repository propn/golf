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

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Thunder.Hsu 2012-12-24
 */
public class TaskUtils {
    //
    private static final ConcurrentMap<Long, TaskInfo> taskInfos = new ConcurrentHashMap<Long, TaskInfo>();
    private static final ConcurrentMap<Long, ScheduledFuture<?>> futures = new ConcurrentHashMap<Long, ScheduledFuture<?>>();
    // 5个线程
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    /**
     * 启动定时任务
     * 
     * @param task
     * @throws Exception
     */
    public static void start(TaskInfo task) throws Exception {
        Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(task.getClz());
        if (Runnable.class.isAssignableFrom(cls)) {
            taskInfos.put(task.getTaskId(), task);
            ScheduledFuture<?> future = scheduler.scheduleAtFixedRate((Runnable) cls.newInstance(), 0L,
                    task.getPeriod(), TimeUnit.SECONDS);
            futures.put(task.getTaskId(), future);
        } else {
            throw new Exception("定时任务:" + cls.getName() + " 必须实现Runnable接口!");
        }
    }

    /**
     * 停止定时任务
     * 
     * @param task
     */
    public static void stop(TaskInfo task) {
        ScheduledFuture<?> future = futures.get(task.getTaskId());
        if (null != future) {
            future.cancel(false);
        }
        taskInfos.remove(task.getTaskId());
        futures.remove(task.getTaskId());
    }

    /**
     * 返回运行中的任务列表
     * 
     * @return
     */
    public static Collection<TaskInfo> getRunningTasks() {
        return taskInfos.values();
    }

}
