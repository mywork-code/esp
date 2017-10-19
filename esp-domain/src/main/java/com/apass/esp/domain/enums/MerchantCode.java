package com.apass.esp.domain.enums;
/**
 * LOGSTASh 日志KEY
 */
public enum MerchantCode {
    MERCHANT_VIRTUAL("虚拟供应商", ""),
    MERCHANT_JIFAN("上海季繁贸易有限公司", ""),
    MERCHANT_JUNYAO("上海均耀电子科技有限公司", ""),
    MERCHANT_BANGLI("上海邦礼网络信息技术有限公司", ""),
    MERCHANT_XIRUN("上海钿润电子商务有限公司", ""),
    MERCHANT_FULIANWANG("郑州市富连网电子科技有限公司", ""),
    MERCHANT_HANSHUN("上海涵顺工贸有限公司", ""),
    MERCHANT_HENSONG("上海很松电子商务有限公司", ""),
    MERCHANT_KAIENXI("上海凯恩西数码科技有限公司", ""),
    MERCHANT_LAIKE("莱克电气股份有限公司上海分公司", ""),
    MERCHANT_YUNYI("上海韵毅数码科技有限公司", ""),
    MERCHANT_LIKE_ELECTRONIC("上海礼客电子商务有限公司", ""),
    MERCHANT_LIKE_INDUSTRY("上海立客实业有限公司", ""),
    MERCHANT_YIMAOFENG("上海茂弈丰智能科技有限公司", ""),
    MERCHANT_MINGSUO("上海名颂科技股份有限公司", ""),
    MERCHANT_QIZHI("上海齐志建材有限公司", ""),
    MERCHANT_QIJING("琦静（上海）贸易有限公司", ""),
    MERCHANT_RUIJIU("上海芮久实业有限公司", ""),
    MERCHANT_XINGKE("上海醒客实业有限公司", ""),
    MERCHANT_XUYAN("上海旭衍贸易有限公司", ""),
    MERCHANT_YISHI("上海伊示电子商务有限公司", ""),
    MERCHANT_YIRAN("上海艺冉贸易有限公司", ""),
    MERCHANT_YIZENGFANG("上海易赠坊商贸有限公司", ""),
    MERCHANT_YOUGE("优阁（北京）科技文化有限公司", ""),
    MERCHANT_YOUWU("青岛有屋科技有限公司", ""),
    MERCHANT_YUJING("上海誉净环保科技有限公司", ""),
    MERCHANT_YUEXING("南通月星家具制造游戏公司", "");
    private String name;
    private String code;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    private MerchantCode(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
