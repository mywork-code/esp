package com.apass.esp.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 获取图片的宽和高
 * 
 * @author admin
 */
public class ImageTools {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageTools.class);

	private static final String[] imgTypeArray = { "jpg", "png" };

	private ImageTools() {
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int getImgWidth(InputStream is) {
		int ret = -1;
		try {
			BufferedImage image = ImageIO.read(is);
			ret = image.getWidth(); // 得到源图宽
		} catch (Exception e) {
			LOGGER.info("获取图片宽度失败！", e);
		}
		return ret;
	}

	/**
	 * 获取图片高度
	 * 
	 * @param file
	 *            图片文件
	 * @return 高度
	 */
	public static int getImgHeight(InputStream is) {
		int ret = -1;
		try {
			BufferedImage image = ImageIO.read(is);
			ret = image.getHeight(); // 得到源图宽
		} catch (Exception e) {
			LOGGER.info("获取图片宽度失败！", e);
		}
		return ret;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param file
	 *            图片文件
	 * @return String
	 */
	public static String getImgType(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		String[] imgTypeArray = originalFilename.split("\\.");
		return imgTypeArray[imgTypeArray.length-1];
	}

	/**
	 * 文件类型验证
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkImgType(MultipartFile file) {
		String imgType = getImgType(file);
		for (String imgTypeTemp : imgTypeArray) {
			if (imgTypeTemp.equals(imgType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文件宽度高度验证:首页banner图
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkHomePageBannerImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 750 && height == 248) {
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片大小校验失败！", e);
		}
		return false;
	}
	/**
	 * 文件宽度高度验证:精选推荐banner图 
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkHomePageBannerImgSizeForSift(MultipartFile file) {
	    try {
	        int width = getImgWidth(file.getInputStream());
	        int height = getImgHeight(file.getInputStream());
	        
	        if (width == 750 && height == 230) {
	            return true;
	        }
	    } catch (IOException e) {
	        LOGGER.info("图片大小校验失败！", e);
	    }
	    return false;
	}

	/**
	 * 文件宽度高度验证:精选商品图
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkSiftGoodsImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 320 && height == 254) {// 320px*254px
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽度高度校验失败！", e);
		}
		return false;
	}
	
	/**
	 * 文件宽度高度验证:三级类目图标
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkCategoryLevel3ImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 100 && height == 100) {// 100*100
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽度高度校验失败！", e);
		}
		return false;
	}

	/**
	 * 文件宽度高度验证:logo图
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkLogoImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 320 && height == 254) {// 320*254
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽度高度校验失败！", e);
		}
		return false;
	}

	/**
	 * 文件宽度高度验证:商品大图
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkGoodBannerImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 750 && height == 672) {// 750*672px;大小：≤500kb
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽度高度校验失败！", e);
		}
		return false;
	}

	/**
	 * 文件宽度高度验证:缩略图logo
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkGoodsLogoImgSize(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());

			if (width == 130 && height == 130) {// 130*130px;;大小：≤500kb
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽度高度校验失败！", e);
		}
		return false;
	}
	
	/**
	 * 文件宽度高度验证:商品大图
	 * 
	 * @param file
	 *            图片文件
	 * @return boolean
	 */
	public static boolean checkWidth480And1540(MultipartFile file) {
		try {
			int width = getImgWidth(file.getInputStream());
			int height = getImgHeight(file.getInputStream());
			if (width > 480&& width<1242 && height <= 1546) {//480-1242px * 1546px
				return true;
			}
		} catch (IOException e) {
			LOGGER.info("图片宽高度校验失败！", e);
		}
		return false;
	}

}
