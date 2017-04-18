package com.apass.esp.invoice;

/**
 * Created by jie.xu on 17/4/5.
 */
public interface InvoiceContentHandler {

  /**
   * 请求发票开具
   */
  String fapiaoKJData() throws Exception;

}
