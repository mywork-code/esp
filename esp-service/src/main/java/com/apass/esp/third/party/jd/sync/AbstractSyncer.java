package com.apass.esp.third.party.jd.sync;

import com.apass.esp.third.party.jd.utils.Threads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public abstract class AbstractSyncer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSyncer.class);

    protected abstract String getName();

    protected abstract int getIntervalSeconds();

    protected void init() {
    }

    @PostConstruct
    public final void start() {
        init();
        ExecutorService cyclesExecutorService = Executors.newSingleThreadExecutor(Threads.newDaemonThreadFactory("cycle_" + getName(),
                Thread.MIN_PRIORITY));
        cyclesExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        AbstractSyncer.this.run();
                    } catch (Exception e) {
                        LOGGER.error(getName(), e);
                    }
                    Threads.sleepSeconds((long) (getIntervalSeconds()));

                }
            }
        });
    }
}
