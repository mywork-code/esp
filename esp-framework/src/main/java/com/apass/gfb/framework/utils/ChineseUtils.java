package com.apass.gfb.framework.utils;

import java.util.Random;

/**
 * *** 这里有个bug, 当第一个字符为多音字时，就不确定返回的首字母是否是预期的了   ***
 * 
 * @description 
 *
 * @author liuchao01
 * @version $Id: ChineseUtils.java, v 0.1 2017年1月11日 下午2:34:50 liuchao01 Exp ${0xD}
 */
public class ChineseUtils {

    /**
     * 返回中文字符串首个字符的首字母
     * 
     * @param   strChinese(陕西省)  
     * @param   bUpCase
     * @return  "s"
     */
    public static String getFirPYIndexStr(String strChinese, boolean bUpCase) {

        try {
            // 把中文转化成byte数组
            byte b[] = strChinese.getBytes("GBK");

            char c = ' ';
            if ((b[0] & 255) > 128) {
                int char1 = b[0] & 255;
                // 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
                char1 <<= 8;
                int chart = char1 + (b[1] & 255);
                c = getPYIndexChar((char) chart, bUpCase);
            }
            return String.valueOf(c);
        } catch (Exception e) {
            throw new RuntimeException("getFirPYIndexStr fail", e);
        }
    }
    
    /**
     * 返回中文字符串所以字符的首字母
     * 
     * @param   strChinese(陕西省)  
     * @param   bUpCase
     * @return  "sxs"
     */
    public static String getPYIndexStr(String strChinese, boolean bUpCase) {

        try {
            StringBuffer buffer = new StringBuffer();
            // 把中文转化成byte数组
            byte b[] = strChinese.getBytes("GBK");

            for (int i = 0; i < b.length; i++) {

                if ((b[i] & 255) > 128) {
                    int char1 = b[i++] & 255;
                 // 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
                    char1 <<= 8;
                    int chart = char1 + (b[i] & 255);
                    buffer.append(getPYIndexChar((char) chart, bUpCase));
                    continue;
                }

            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException("getPYIndexStr fail", e);
        }
    }
    
    /**
     * 获取首字母
     * @param strChinese
     * @param bUpCase
     * @return
     */
    private static char getPYIndexChar(char strChinese, boolean bUpCase) {
        int charGBK = strChinese;
        char result;
        
        if (charGBK >= 45217 && charGBK <= 45252){
            result = 'A';
        } else if (charGBK >= 45253 && charGBK <= 45760){
            result = 'B';
        } else if (charGBK >= 45761 && charGBK <= 46317){
            result = 'C';
        } else if (charGBK >= 46318 && charGBK <= 46825){
            result = 'D';
        } else if (charGBK >= 46826 && charGBK <= 47009){
            result = 'E';
        } else if (charGBK >= 47010 && charGBK <= 47296){
            result = 'F';
        } else if (charGBK >= 47297 && charGBK <= 47613){
            result = 'G';
        } else if (charGBK >= 47614 && charGBK <= 48118){
            result = 'H';
        } else if (charGBK >= 48119 && charGBK <= 49061){
            result = 'J';
        } else if (charGBK >= 49062 && charGBK <= 49323){
            result = 'K';
        } else if (charGBK >= 49324 && charGBK <= 49895){
            result = 'L';
        } else if (charGBK >= 49896 && charGBK <= 50370){
            result = 'M';
        } else if (charGBK >= 50371 && charGBK <= 50613){
            result = 'N';
        } else if (charGBK >= 50614 && charGBK <= 50621){
            result = 'O';
        } else if (charGBK >= 50622 && charGBK <= 50905){
            result = 'P';
        } else if (charGBK >= 50906 && charGBK <= 51386){
            result = 'Q';
        } else if (charGBK >= 51387 && charGBK <= 51445){
            result = 'R';
        } else if (charGBK >= 51446 && charGBK <= 52217){
            result = 'S';
        } else if (charGBK >= 52218 && charGBK <= 52697){
            result = 'T';
        } else if (charGBK >= 52698 && charGBK <= 52979){
            result = 'W';
        } else if (charGBK >= 52980 && charGBK <= 53688){
            result = 'X';
        } else if (charGBK >= 53689 && charGBK <= 54480){
            result = 'Y';
        } else if (charGBK >= 54481 && charGBK <= 55289){
            result = 'Z';
        } else {
            result = (char) (65 + (new Random()).nextInt(25));
        }

        if (!bUpCase){
            result = Character.toLowerCase(result); 
        }

        return result;
    }
}
