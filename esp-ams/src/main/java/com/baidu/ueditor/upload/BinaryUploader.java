package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.apass.esp.domain.Response;
import com.apass.esp.utils.ImageTools;
import com.apass.esp.utils.NarrowImageUtils;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

public class BinaryUploader {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BinaryUploader.class);

	private BinaryUploader() {
	}


	public static final State save(HttpServletRequest request, Map<String, Object> conf,String serverIp) {

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		try {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			MultipartFile file = req.getFile("upfile");
			  /**
             * 图片校验
             */
            boolean checkGoodBannerImgSize = ImageTools.checkWidth480And1540(file);// 尺寸
            boolean checkImgType = ImageTools.checkImgType(file);// 类型
            int size = file.getInputStream().available();
            if (size > 1024 * 512) {
                file.getInputStream().close();
                return new BaseState(false, "文件请在500kb以内!");
            }else if (!(checkGoodBannerImgSize && checkImgType)) {
                file.getInputStream().close();
                return new BaseState(false, "文件尺寸不符,上传图片尺寸宽度480-1242px高度小于等于1546px,格式：.jpg,.png");
            }  
			
            

			String savePath = (String) conf.get("savePath");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			// String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = file.getInputStream();
//			boolean storageFlagt=NarrowImageUtils.saveAndNarrowImageByIs(is, "/data/nfs/gfb/" + savePath);
			
//			State storageState=new BaseState(true);
//			
//			if(!storageFlagt){
//				return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
//			}
			State storageState = StorageManager.saveFileByInputStream(is, "/data/nfs/gfb/" + savePath, maxSize);
			is.close();
			String urlPic = null;
			if(serverIp.contains("10")){
			    urlPic = request.getScheme() + "://" + serverIp + ":" + request.getServerPort();
			}else {
			    urlPic = request.getScheme() + "://" + serverIp;
            }
			
			if (storageState.isSuccess()) {
				storageState.putInfo("url", urlPic + "/ams/webapp/ueditor/loadPoto?picUrl=" + savePath);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (IOException e) {
			LOGGER.error("未找到文件", e);
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (Exception e) {
			LOGGER.error("失败", e);
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
