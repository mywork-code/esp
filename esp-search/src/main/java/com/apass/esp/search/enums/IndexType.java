package com.apass.esp.search.enums;

/**
 * Created by Administrator on 2017/5/23.
 */

import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.entity.IdAble;
import com.apass.esp.search.entity.UpdatedObject;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by xianzhi.wang on 2017/5/22.
 */
public enum IndexType {
    GOODS("goods") {
        @Override
        public String getMapper() {
            return "/esmapper/goodsMapper.txt";
        }

        @Override
        public Class<? extends IdAble> getTypeClass() {
            return Goods.class;
        }
    };
    private String dataName;

    IndexType(String name) {
        this.dataName = name;
    }

    public String getDataName() {
        return dataName;
    }

    abstract public String getMapper();

    abstract public Class getTypeClass();

    private TransferQueue<UpdatedObject<? extends IdAble>> transferQueue = new LinkedTransferQueue<>();

    public UpdatedObject<? extends IdAble> takeQueue() throws InterruptedException {
        return transferQueue.take();
    }

    public boolean offerQueue(UpdatedObject<? extends IdAble> updatedObject) {
        if (transferQueue.tryTransfer(updatedObject)) {
            return true;
        }
        return transferQueue.offer(updatedObject);
    }
}
