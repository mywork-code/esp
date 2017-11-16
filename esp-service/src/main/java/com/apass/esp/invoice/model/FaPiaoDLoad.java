package com.apass.esp.invoice.model;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * Created by jie.xu on 17/3/29.
 * 发票下载
 */
@XStreamAlias("REQUEST_FPXXXZ_NEW")
public class FaPiaoDLoad {
    @XStreamAlias("FPQQLSH")
    private String fpqqlsh;
    @XStreamAlias("DSPTBM")
    private String dsptbm;
    @XStreamAlias("NSRSBH")
    private String nsrsbh;
    @XStreamAlias("DDH")
    private String ddh;
    @XStreamAlias("PDF_XZFS")
    private String pdfXzfs;
    public String getFpqqlsh() {
        return fpqqlsh;
    }
    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }
    public String getDsptbm() {
        return dsptbm;
    }
    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm;
    }
    public String getNsrsbh() {
        return nsrsbh;
    }
    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }
    public String getDdh() {
        return ddh;
    }
    public void setDdh(String ddh) {
        this.ddh = ddh;
    }
    public String getPdfXzfs() {
        return pdfXzfs;
    }
    public void setPdfXzfs(String pdfXzfs) {
        this.pdfXzfs = pdfXzfs;
    }
}