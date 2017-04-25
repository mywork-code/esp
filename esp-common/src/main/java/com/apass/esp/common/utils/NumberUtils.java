package com.apass.esp.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by jie.xu on 17/4/24.
 */
public class NumberUtils {
  public static DecimalFormat integerFormat = new DecimalFormat("###,###");
  public static DecimalFormat decimalFormat = new DecimalFormat("0.0000");

  public static BigDecimal divide100(BigDecimal num) {
    if (num == null) {
      return null;
    }
    return num.divide(new BigDecimal(100));
  }

  public static String splitNum(Object obj) {
    if (obj == null) {
      return null;
    }
    return integerFormat.format(obj);
  }

  public static String formatFloat(Float f1, Float f2) {
    return decimalFormat.format((float) f1 / f2);
  }

  public static String formatPercentageThreeDecimal(BigDecimal num) {
    return new DecimalFormat("#0.000%").format(num);
  }

  public static String formatPercentageFourDecimal(BigDecimal num) {
    return new DecimalFormat("#0.0000%").format(num);
  }

  public static String formatMoneyWithDecimal(BigDecimal num) {
    String ret = new DecimalFormat("###,###,##0.00").format(num);
    if (ret.equals("-0.00")) {
      ret = "0.00";
    }
    return ret;
  }
}
