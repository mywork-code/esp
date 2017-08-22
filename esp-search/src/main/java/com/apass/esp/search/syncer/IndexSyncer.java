package com.apass.esp.search.syncer;

import com.apass.esp.search.entity.IdAble;
import com.apass.esp.search.entity.UpdatedObject;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.ESClientManager;
import com.apass.esp.search.utils.Esprop;
import com.apass.esp.search.utils.PropertiesUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public class IndexSyncer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexSyncer.class);

    private final String name;

    private final IndexType indexType;

    private final ExecutorService executorService;

    private static Esprop esprop = new Esprop();

    static {
        InputStream esIn = ESClientManager.class.getClassLoader().getResourceAsStream("esmapper/es.properties");
        try {
            Properties properties = PropertiesUtils.readFromText(IOUtils.toString(esIn).trim());
            esprop.setClusterName(properties.getProperty("esName"));
            esprop.setHost(properties.getProperty("esHost"));
            esprop.setIndice(properties.getProperty("esIndice"));
        } catch (IOException e) {
            LOGGER.error("get es config error ...");
        }
    }

    public IndexSyncer(IndexType indexType) {
        this.indexType = indexType;
        this.name = indexType.getDataName();
        executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "sync" + name);
                thread.setDaemon(true);
                thread.setPriority(1);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread t, Throwable e) {
                        LOGGER.error("sync_" + name + " _ " + t.getName(), e);
                    }
                });
                return thread;
            }
        });
    }

    public void start() {
        //startBatch();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                loadByUpdate();
            }
        });
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
                        IndexSyncer.this.loadInit();
                    } catch (Exception e) {
                        LOGGER.error(name + " sync error", e);
                    }
                    LOGGER.info(name + "  sync end  ....................................");
                }

            }
        });
    }

    private void loadInit() {

    }

    private void loadByUpdate() {
        while (true) {
            UpdatedObject<? extends IdAble> updated = null;
            try {
                updated = indexType.takeQueue();
            } catch (InterruptedException e) {
                LOGGER.error("index sync take queue error ...");
                continue;
            }
            try {
                updated.getType().operator(esprop.getIndice(), indexType, updated.getObject());
            } catch (Exception e) {
                LOGGER.error("in " + name + "update index error",e);
                //indexType.offerQueue(updated);
            }
        }
    }
}
