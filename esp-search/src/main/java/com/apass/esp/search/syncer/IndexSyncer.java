package com.apass.esp.search.syncer;

import com.apass.esp.domain.enums.IndexType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class IndexSyncer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexSyncer.class);

    private final String name;

    private final IndexType indexType;

    public IndexSyncer(IndexType indexType) {
        this.indexType = indexType;
        this.name = indexType.getDataName();
    }

    public void start() {
        startBatch();
    }

    private void startBatch() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    LOGGER.info(name + "  sync start....................................");
                    try {
                        IndexSyncer.this.loadByModifiedTime();
                    } catch (Exception e) {
                        LOGGER.error(name + " sync error", e);
                    }
                    LOGGER.info(name + "  sync end  ....................................");
                }

            }
        });
    }

    private void loadByModifiedTime() {


    }
}
