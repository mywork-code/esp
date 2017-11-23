package com.apass.esp.domain.entity.invoice;
public class InvoiceDetails {
    private String id;//发票主键
    private String orderId;//订单号
    private String content;//发票内容
    private String orderAmt;//发票金额  订单实付
    private String invoiceType;//发票类型  电票
    private String status;//发票状态   申请中 申请成功。。
    private String date;//开票日期
    private String invoiceNum;//发票号  开票 成功该字段非空
    private String pdfUrl;//发票链接 开票成功该字段非空
    
    private String AParty;//甲方  or 开票方
    private String headType;//抬头类型 1 2 
    private String invoiceHead;//抬头 个人抬头 or 公司名
    private String taxesNum;//纳税人识别号 非 个人抬头  才非空
    
    private String name;//用户姓名
    private String picture;//用户头像
    private String telphone;//用户手机号
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getInvoiceType() {
        return invoiceType;
    }
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    public String getHeadType() {
        return headType;
    }
    public void setHeadType(String headType) {
        this.headType = headType;
    }
    public String getInvoiceHead() {
        return invoiceHead;
    }
    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAParty() {
        return AParty;
    }
    public void setAParty(String aParty) {
        AParty = aParty;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getInvoiceNum() {
        return invoiceNum;
    }
    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getPdfUrl() {
        return pdfUrl;
    }
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
    public String getTaxesNum() {
        return taxesNum;
    }
    public void setTaxesNum(String taxesNum) {
        this.taxesNum = taxesNum;
    }
}