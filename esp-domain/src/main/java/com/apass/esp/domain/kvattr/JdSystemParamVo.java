package com.apass.esp.domain.kvattr;

/**
 * Created by xiaohai on 2017/8/28.
 */
public class JdSystemParamVo {
    /**
     * 协议价(99-500)比例系数
     */
    private String protocolPrice1;

    /**
     * 协议价(500-2000)比例系数
     */
    private String protocolPrice2;

    /**
     * 协议价(2000及以上)比例系数
     */
    private String protocolPrice3;

    public String getProtocolPrice1() {
        return protocolPrice1;
    }

    public void setProtocolPrice1(String protocolPrice1) {
        this.protocolPrice1 = protocolPrice1;
    }

    public String getProtocolPrice2() {
        return protocolPrice2;
    }

    public void setProtocolPrice2(String protocolPrice2) {
        this.protocolPrice2 = protocolPrice2;
    }

    public String getProtocolPrice3() {
        return protocolPrice3;
    }

    public void setProtocolPrice3(String protocolPrice3) {
        this.protocolPrice3 = protocolPrice3;
    }
}
