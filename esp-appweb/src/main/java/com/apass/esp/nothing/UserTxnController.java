package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.service.TxnInfoService;
import com.apass.gfb.framework.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by jie.xu on 17/11/5.
 */
@Path("/txn")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserTxnController {

  @Autowired
  private TxnInfoService txnInfoService;

  /**
   * 获取用户总的信用支付笔数
   * @param paramMap
   * @return
   */
  @POST
  @Path("/creditpaynum")
  public Response getUserTxnFlow(@RequestBody Map<String, Object> paramMap){
    String userId = CommonUtils.getValue(paramMap, "userId");
    return Response.successResponse(txnInfoService.getTotalCreditPayNum(Long.valueOf(userId)));

  }
}
