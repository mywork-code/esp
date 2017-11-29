package com.apass.esp.domain.enums;
/**
 * LOGSTASh 日志KEY
 */
public enum MerchantCode {
    MERCHANT_JIFAN("上海季繁贸易有限公司", "",""),
    MERCHANT_JUNYAO("上海均耀电子科技有限公司", "","0shjs12"),
    MERCHANT_BANGLI("上海邦礼网络信息技术有限公司", "","0shqp2"),
    MERCHANT_XIRUN("上海钿润电子商务有限公司", "","0shsj16"),
    MERCHANT_HANSHUN("上海涵顺工贸有限公司", "",""),
    MERCHANT_HENSONG("上海很松电子商务有限公司", "",""),
    MERCHANT_KAIENXI("上海凯恩西数码科技有限公司", "","0shpd15"),
    MERCHANT_LAIKE("莱克电气股份有限公司上海分公司", "","0shmh14"),
    MERCHANT_YUNYI("上海韵毅数码科技有限公司", "",""),
    MERCHANT_LIKE_ELECTRONIC("上海礼客电子商务有限公司", "",""),
    MERCHANT_LIKE_INDUSTRY("上海立客实业有限公司", "","0shmh13"),
    MERCHANT_YIMAOFENG("上海茂弈丰智能科技有限公司", "100009",""),
    MERCHANT_MINGSUO("上海名颂科技股份有限公司", "100010",""),
    MERCHANT_QIZHI("上海齐志建材有限公司", "100011",""),
    MERCHANT_QIJING("琦静（上海）贸易有限公司", "100012",""),
    MERCHANT_RUIJIU("上海芮久实业有限公司", "100013","03098"),
    MERCHANT_XINGKE("上海醒客实业有限公司", "100014","0001096"),
    MERCHANT_XUYAN("上海旭衍贸易有限公司", "100015",""),
    MERCHANT_YISHI("上海伊示电子商务有限公司", "100016","0001093"),
    MERCHANT_YIRAN("上海艺冉贸易有限公司", "100017","0001095"),
    MERCHANT_YIZENGFANG("上海易赠坊商贸有限公司", "100018",""),
    MERCHANT_YOUGE("优阁（北京）科技文化有限公司", "100019","0bjfs9"),
    MERCHANT_YOUWU("青岛有屋科技有限公司", "100020","0sdqd7"),
    MERCHANT_YUJING("上海誉净环保科技有限公司", "100021","0shcm5"),
    MERCHANT_JINGDONG("北京京东世纪信息技术有限公司","400002","0000097");
    private String name;
    private String code;
    private String val; //数据库商户编码

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
    private MerchantCode(String name, String code,String val) {
        this.name = name;
        this.code = code;
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
