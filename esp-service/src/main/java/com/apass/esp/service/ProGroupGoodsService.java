package com.apass.esp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jie.xu on 17/9/26.
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class ProGroupGoodsService {
}
