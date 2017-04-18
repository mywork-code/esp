package com.apass.gfb.framework.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.apass.gfb.framework.utils.base64.Base64;

/**
 * 
 * @description
 *
 * @author listening
 * @version $Id: ImageUtils.java, v 0.1 2015年10月29日 下午2:04:33 listening Exp $
 */
public class ImageUtils {

	/**
	 * Generate Random Image Byte[]
	 */
	public static byte[] getRandomImgage(String value) {
		ByteArrayOutputStream output = null;
		try {
			// 在内存中创建图象
			int charLen = value.length();
			int width = charLen * 25, height = 25;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			// 生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(220, 250));
			g.fillRect(0, 0, width, height);
			// 设定字体
			g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			// 画边框
			g.draw3DRect(0, 0, width - 1, height - 1, true);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			char[] charArray = value.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(String.valueOf(charArray[i]), 25 * i + 5, 20);
			}
			g.drawOval(0, 12, 60, 11);
			g.dispose();

			output = new ByteArrayOutputStream();
			ImageIO.write(image, "JPEG", output);
			return output.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("write random fail", e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("close resource fail", e);
			}
		}
	}

	/**
	 * 生成随机颜色
	 */
	private static final Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * scale image to the request size
	 * 
	 * @param data
	 * @param width
	 * @param height
	 * @return byte[]
	 */
	public static byte[] scale(byte[] data, int width, int height) {
		ByteArrayOutputStream output = null;
		ByteArrayInputStream input = null;
		try {
			input = new ByteArrayInputStream(data);
			output = new ByteArrayOutputStream();
			Thumbnails.of(input).size(width, height).toOutputStream(output);
			return output.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("scale image fail", e);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * Scale Size 200 * 200
	 * 
	 * @param data
	 * @return byte[]
	 */
	public static byte[] scale_200(byte[] data) {
		ByteArrayOutputStream output = null;
		ByteArrayInputStream input = null;
		try {
			input = new ByteArrayInputStream(data);
			output = new ByteArrayOutputStream();
			Thumbnails.of(input).size(200, 200).toOutputStream(output);
			return output.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("scale image fail", e);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 图片转base64
	 * 
	 * @param fileName
	 * @return
	 */
	public static String imageToBase64(String fileName) throws Exception {
		String imageEncoded = "";
		if(StringUtils.isBlank(fileName)){
			return imageEncoded;
		}
		try {
			File imgFile = new File(fileName);
			if (imgFile.isFile()) {// 判断文件的存在性
				byte[] content = FileUtils.readFileToByteArray(imgFile);
				imageEncoded = new String(Base64.encode(content));
			}

		} catch (IOException e) {
			throw new Exception("图片转base64操作发生错误===》", e);
		}
		return imageEncoded;
	}
}
