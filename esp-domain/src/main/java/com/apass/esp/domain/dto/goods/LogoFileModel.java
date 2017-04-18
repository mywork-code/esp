package com.apass.esp.domain.dto.goods;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传logo
 * @author zhanwendong
 *
 */
public class LogoFileModel {
    private Long editLogogoodsId;//商品Id
    private Long editStockinfoIdInForm;//库存Id
    private MultipartFile editGoodsLogoFile;//logo
    private String editStockLogoUrl;//库存logourl
    
    public Long getEditStockinfoIdInForm() {
        return editStockinfoIdInForm;
    }

    public void setEditStockinfoIdInForm(Long editStockinfoIdInForm) {
        this.editStockinfoIdInForm = editStockinfoIdInForm;
    }

    public MultipartFile getEditGoodsLogoFile() {
		return editGoodsLogoFile;
	}

	public void setEditGoodsLogoFile(MultipartFile editGoodsLogoFile) {
		this.editGoodsLogoFile = editGoodsLogoFile;
	}
	 
	public Long getEditLogogoodsId() {
		return editLogogoodsId;
	}

	public void setEditLogogoodsId(Long editLogogoodsId) {
		this.editLogogoodsId = editLogogoodsId;
	}

	public String getEditStockLogoUrl() {
		return editStockLogoUrl;
	}

	public void setEditStockLogoUrl(String editStockLogoUrl) {
		this.editStockLogoUrl = editStockLogoUrl;
	}
	
}
