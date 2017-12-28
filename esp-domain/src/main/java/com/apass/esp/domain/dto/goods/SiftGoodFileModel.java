package com.apass.esp.domain.dto.goods;
import org.springframework.web.multipart.MultipartFile;
/**
 * 说明：上传精选商品
 * @author xiaohai
 * @version 1.0
 * @date 2016年12月30日
 */
public class SiftGoodFileModel {
    private Long siftGoodsId;// 商品Id
    private String siftSort;// 商品精选排序
    private MultipartFile siftGoodsFile;// sift文件 精选商品上传图片
    public Long getSiftGoodsId() {
        return siftGoodsId;
    }
    public void setSiftGoodsId(Long siftGoodsId) {
        this.siftGoodsId = siftGoodsId;
    }
    public String getSiftSort() {
        return siftSort;
    }
    public void setSiftSort(String siftSort) {
        this.siftSort = siftSort;
    }
    public MultipartFile getSiftGoodsFile() {
        return siftGoodsFile;
    }
    public void setSiftGoodsFile(MultipartFile siftGoodsFile) {
        this.siftGoodsFile = siftGoodsFile;
    }
}