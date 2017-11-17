package com.aisino;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
public class PassWordCreate{
    private static final String ALGORITHM = "MD5";
    private static final String CHARSET = "UTF-8";
    private PassWordCreate() {}
    private static PassWordCreate passWordCheck = null;
    public static PassWordCreate getSingleton() {
        if (passWordCheck == null) {
            if (passWordCheck == null) {
                passWordCheck = new PassWordCreate();
            }
        }
        return passWordCheck;
    }
    /**
     * <passWord>10 位随机数+Base64({（10 位随机数+注册码）MD5})</passWord>
     * @param zch 注册码
     * @param randomKey 10位随机数
     * @return
     * @throws Exception
     */
    public static String passWordCreate(String zch,String randomKey)throws Exception{
        if ((zch == null) || ("".equals(zch))) {
            return "";
        }
        StringBuffer bf = new StringBuffer();
        bf.append(randomKey);
        bf.append(zch);
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(bf.toString().getBytes(CHARSET));
        return randomKey + new String(new String(new Base64().encode(md.digest())));
    }
}