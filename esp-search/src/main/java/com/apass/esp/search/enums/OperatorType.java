package com.apass.esp.search.enums;

import com.apass.esp.search.entity.IdAble;
import com.apass.esp.search.manager.IndexManager;

/**
 * type: enum
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public enum OperatorType {
    ADD("add") {
        @Override
        public <T extends IdAble> void operator(String index, IndexType type, T data) {
            IndexManager.addDocument(index, type, data);
        }

    },
    UPDATE("update") {
        @Override
        public <T extends IdAble> void operator(String index, IndexType type, T data) {
            IndexManager.updateDocument(index, type, data);
        }
    },
    DELETE("delete") {
        @Override
        public <T extends IdAble> void operator(String index, IndexType type, T data) {
            IndexManager.deleteDocument(index, type, data.getId());
        }
    };

    private String name;

    OperatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public <T extends IdAble> void operator(String index, IndexType type, T data);

}
