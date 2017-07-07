package com.apass.esp.third.party.jd.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Threads {
    private static final Logger LOGGER = LoggerFactory.getLogger(Threads.class);

    /**
     *
     */
    public static Thread.UncaughtExceptionHandler LOGGER_UNCAUGHTEXCEPTIONHANDLER = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LOGGER.error(t.getName(), e);
        }
    };

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     *
     * @param name
     * @return
     */
    public static ThreadFactory newDaemonThreadFactory(final String name) {
        return newDaemonThreadFactory(name, Thread.NORM_PRIORITY);
    }

    /**
     *
     * @param name
     * @param priority
     * @return
     */
    public static ThreadFactory newDaemonThreadFactory(final String name, final int priority) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return newDaemonThread(r, name, priority);
            }
        };
    }

    /**
     *
     * @param runnable
     * @param name
     * @param priority
     * @return
     */
    public static Thread newDaemonThread(Runnable runnable, String name, int priority) {
        Thread thread = new Thread(runnable, name + "_" + ATOMIC_INTEGER.getAndIncrement());
        thread.setPriority(priority);
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(LOGGER_UNCAUGHTEXCEPTIONHANDLER);
        return thread;
    }

    /**
     *
     * @param milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     *
     * @param seconds
     */
    public static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
