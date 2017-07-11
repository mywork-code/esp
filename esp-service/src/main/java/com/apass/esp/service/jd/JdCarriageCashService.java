package com.apass.esp.service.jd;

import java.math.BigDecimal;

import com.apass.esp.mapper.JdCategoryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author xiaohai
 *
 */
@Service
public class JdCarriageCashService {

  @Autowired
  private JdCategoryMapper jdCategoryMapper;
  
  public BigDecimal getJdCarryCash(){
	  
	  return null;
  }


}
