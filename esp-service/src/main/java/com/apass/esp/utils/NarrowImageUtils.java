package com.apass.esp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apass.gfb.framework.exception.BusinessException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片压缩公共类
 * 
 * @author admin
 *
 */
public class NarrowImageUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(NarrowImageUtils.class);

	/**
	 * 压缩图片
	 * 
	 * @param is
	 * @return
	 * @throws BusinessException
	 */
	private static byte[] zoomImage(InputStream is) throws BusinessException {
		try {

			ByteArrayOutputStream os = new ByteArrayOutputStream();

			Thumbnails.of(is).size(300, 300).toOutputStream(os);
			byte[] scaleResult = os.toByteArray();

			return scaleResult;
		} catch (Exception e) {
			LOGGER.error("图片压缩创建缩略图发生异常" + e.getMessage(), e);
			throw new BusinessException("创建缩略图发生异常");
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param src
	 * @return
	 * @throws BusinessException
	 */
	private static byte[] zoomImageSrc(String src) throws BusinessException {
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				LOGGER.error("图片压缩原图片文件不存在");
				throw new BusinessException("图片压缩原图片文件不存在");
			}
			InputStream in = new FileInputStream(srcfile);
			return zoomImage(in);
		} catch (Exception e) {
			LOGGER.error("图片压缩创建缩略图发生异常" + e.getMessage(), e);
			throw new BusinessException("图片压缩创建缩略图发生异常");
		}
	}

	/**
	 * 文件保存
	 * 
	 * @param imgByte
	 * @param fileFullPath
	 * @return
	 */
	public static boolean writeHighQuality(byte[] imgByte, String fileFullPath) {
		try {
			/* 输出到文件流 */
			File newimage = new File(fileFullPath);

			FileUtils.writeByteArrayToFile(newimage, imgByte);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据原文件路径压缩为新路径的文件
	 * 
	 * @param inputFoler
	 * @param outputFolder
	 * @return
	 */
	public static boolean saveAndNarrowImage(String inputFoler, String outputFolder) {
		try {
			writeHighQuality(zoomImageSrc(inputFoler), outputFolder);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据输入流压缩图片
	 * 
	 * @param is
	 * @param outputFolder
	 * @return
	 */
	public static boolean saveAndNarrowImageByIs(InputStream is, String outputFolder) {
		try {
			writeHighQuality(zoomImage(is), outputFolder);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}
}
