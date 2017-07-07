package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ProductDetail implements Serializable{
	
	private Product product;
	
	private List<ProductImage> productImages;
	
	private ProductComment productComment;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public ProductComment getProductComment() {
		return productComment;
	}

	public void setProductComment(ProductComment productComment) {
		this.productComment = productComment;
	}
	

}
