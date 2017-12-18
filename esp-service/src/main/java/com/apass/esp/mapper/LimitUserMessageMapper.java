package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.LimitUserMessage;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitUserMessageMapper extends GenericMapper<LimitUserMessage,Long> {
    /**
     * getLimitUserMessageList
     * @param entity
     * @return
     */
    public List<LimitUserMessage> getLimitUserMessageList(LimitUserMessage entity);
}