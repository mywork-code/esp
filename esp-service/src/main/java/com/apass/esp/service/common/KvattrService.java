package com.apass.esp.service.common;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.mapper.KvattrMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class KvattrService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KvattrService.class);

    @Autowired
    private KvattrMapper kvattrMapper;

    /**
     * @param object
     */
    public void add(Object object) {
        List<Kvattr> list = convert(object);
        for (Kvattr kvattr : list
                ) {
            kvattr.setCreateDate(new Date());
            kvattr.setUpdateDate(new Date());
            kvattrMapper.insert(kvattr);
        }
    }

    /**
     * @return
     */
    public <T>T get(T t) {
        Class<?> clazz = t.getClass();
        List<Kvattr> list = kvattrMapper.getBySource(clazz.getTypeName());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (Kvattr kvattr : list) {
           // String type = kvattr.getKey().split("_")[0];
            String fieldName = kvattr.getKey();//.split("_")[1];
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields
                    ) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    try {
                        field.set(t, kvattr.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
       return t ;

    }


    private List<Kvattr> convert(Object object) {
        List<Kvattr> list = new ArrayList<>();
        Class clazz = object.getClass();
        String typeName = clazz.getTypeName();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object o = field.get(object);
                if (o != null) {
                    //String clazzName = field.getType().getSimpleName();
                    String name = field.getName();
                    String key = //clazzName + "_" +
                             name;
                    Kvattr kvattr = new Kvattr();
                    kvattr.setKey(key);
                    kvattr.setValue((String) o);
                    kvattr.setSource(typeName);
                    list.add(kvattr);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("convert typeName{} error", typeName);
            }
        }
        return list;
    }
}
