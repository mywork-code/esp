package com.apass.esp.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by jie.xu on 17/4/5.
 * CA配置文件路径和参数
 */
public final class CaConstant {
  private final static Logger LOGGER = LoggerFactory.getLogger(CaConstant.class);

  public final static String DEFAULT_CHARSET = "UTF-8";
  private static Properties properties;
  private static String rootClassPath = null;

  static {
    try {
      properties = new Properties();
      properties.load(CaConstant.class.getResourceAsStream("/pkcs7.properties"));
      rootClassPath = CaConstant.class.getResource("/").getPath();
    } catch (Exception e) {
      LOGGER.error("pkcs7接口初始化系统参数失败!", e);
    }
  }

  /**
   * 读取配置文件里key的值
   *
   * @param key
   *            配置文件里的key
   * @return
   * @throws java.io.IOException
   */
  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * 获取配置文件的完整路径
   * @param key
   * @return
   */
  public static String getCaFilePath(String key) {
    return rootClassPath + getProperty(key);
  }

  public static void main(String[] args) {
    final String trustsBytes = CaConstant.getProperty("PUBLIC_TRUSTS");
    String decryptPFXBytes = CaConstant.getProperty("CLIENT_DECRYPTPFX");
    System.out.println(trustsBytes);
    System.out.println(decryptPFXBytes);
  }
}
