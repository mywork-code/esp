package com.apass.esp.domain.enums;
/**
 * LOGSTASh 日志KEY
 */
public enum MerchantCode {
    MERCHANT_QISOU("上海奇搜网络科技有限公司", "100094",""),
    MERCHANT_ZHIWEI("致维科技（北京）有限公司", "100188",""),
    MERCHANT_YITUO("一拓文化传媒（上海）有限公司", "100029",""),
    MERCHANT_TAIYI("泰邑广告（上海）有限公司", "100189",""),
    MERCHANT_KAIMEI("凯美广告（上海）有限公司", "100025",""),
    MERCHANT_WEIJUN("上海文军信息技术有限公司", "100190",""),
    MERCHANT_JINGZAN("晶赞广告（上海）有限公司", "",""),
    MERCHANT_WEICHAO("上海威超广告有限公司", "100191",""),
    MERCHANT_JIFAN("上海季繁贸易有限公司", "100213","0001092"),
    MERCHANT_JUNYAO("上海均耀电子科技有限公司", "100192","0shjs12"),
    MERCHANT_BANGLI("上海邦礼网络信息技术有限公司", "100214","0shqp2"),
    MERCHANT_XIRUN("上海钿润电子商务有限公司", "100215","0shsj16"),
    MERCHANT_FULIAN("郑州市富连网电子科技有限公司", "",""),
    MERCHANT_HANSHUN("上海涵顺工贸有限公司", "",""),
    MERCHANT_HENSONG("上海很松电子商务有限公司", "",""),
    MERCHANT_KAIENXI("上海凯恩西数码科技有限公司", "100216","0shpd15"),
    MERCHANT_LAIKE("莱克电气股份有限公司上海分公司", "100222","0shmh14"),
    MERCHANT_YUNYI("上海韵毅数码科技有限公司", "",""),
    MERCHANT_YIMAOFENG("上海茂弈丰智能科技有限公司", "100009",""),
    MERCHANT_MINGSUO("上海名颂科技股份有限公司", "100010",""),
    MERCHANT_QIZHI("上海齐志建材有限公司", "100011","0000091"),
    MERCHANT_QIJING("琦静（上海）贸易有限公司", "100012","0001094"),
    MERCHANT_RUIJIU("上海芮久实业有限公司", "100013","03098"),
    MERCHANT_XINGKE("上海醒客实业有限公司", "100014","0001096"),
    MERCHANT_XUYAN("上海旭衍贸易有限公司", "100015","0001090"),
    MERCHANT_YISHI("上海伊示电子商务有限公司", "100016","0001093"),
    MERCHANT_YIRAN("上海艺冉贸易有限公司", "100017","0001095"),
    MERCHANT_YIZENGFANG("上海易赠坊商贸有限公司", "100018","0shsj1"),
    MERCHANT_YOUGE("优阁（北京）科技文化有限公司", "100019","0bjfs9"),
    MERCHANT_YOUWU("青岛有屋科技有限公司", "100020","0sdqd7"),
    MERCHANT_YUJING("上海誉净环保科技有限公司", "100021","0shcm5"),
    MERCHANT_LIKE_YUEXIN("南通月星家具制造有限公司", "","0jsnt6"),
    MERCHANT_LIKE_CHANGLI("上海昌礼实业有限公司", "100217","06099"),
    MERCHANT_LIKE_JUANJIN("上海隽劲实业有限公司", "100223","02100"),
    MERCHANT_LIKE_JUhefu("聚合付（深圳）信息科技有限公司", "400016",""),
    MERCHANT_LIKE_ELECTRONIC("上海礼客电子商务有限公司", "",""),
    MERCHANT_LIKE_INDUSTRY("上海立客实业有限公司", "","0shmh13"),
    MERCHANT_JINGDONG("北京京东世纪信息技术有限公司","400002","0000097"),
    MERCHANT_WEIZHI("微知（上海）服务外包有限公司","400685","05103");
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
