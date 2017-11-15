package com.apass.esp.service.common;

import com.apass.esp.domain.entity.BsdiffEntity;
import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.mapper.BsdiffInfoEntityMapper;
import com.apass.esp.utils.FileUtilsCommons;
import com.tencent.tinker.bsdiff.BSDiff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BsdiffinfoService {
	@Autowired
	private BsdiffInfoEntityMapper bsdiffInfoEntityMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(BsdiffinfoService.class);
	@Value("${nfs.rootPath}")
	private String rootPath;
	@Value("${nfs.bsdiff}")
	private String nfsBsdiffPath;

	private static final String VERPATH = "/verzip";
	private static final String PATCHPATH = "/patchzip";


	@Transactional
	public void bsdiffUpload(BsdiffEntity bsdiffEntity, BsdiffInfoEntity bsdiffInfoEntity) throws IOException {
		StringBuffer sb = new StringBuffer();

		String bsdiffVer = bsdiffEntity.getBsdiffVer();
		MultipartFile bsdiffFile = bsdiffEntity.getBsdiffFile();
		String originalFilename = bsdiffFile.getOriginalFilename();

		bsdiffInfoEntity.setBsdiffVer(bsdiffVer);

		//判断服务器端是否有更早版本的文件，如果没有 直接上传，如果有 增量拆分
		File directory = new File(rootPath+nfsBsdiffPath+VERPATH);
		if(!directory.exists()){//如果目录不存在创建目录
			directory.mkdirs();
		}
		if(!(directory.listFiles().length > 0)){//第一次上传
			int count = bsdiffInfoEntityMapper.insertSelective(bsdiffInfoEntity);//先操作数据库，再上传文件。
			//没有增量拆分操作
			if(count == 1){
				FileUtilsCommons.uploadFilesUtil(rootPath, nfsBsdiffPath+VERPATH+"/"+originalFilename, bsdiffFile);
			}
		}else{//增量拆分
			for(int i=Integer.valueOf(bsdiffVer)-1;i>0;i--){
				if(i>1){
					sb.append(bsdiffVer+"_"+i+",");
				}else {
					sb.append(bsdiffVer+"_"+i);
				}
			}
			bsdiffInfoEntity.setPatchName(sb.toString());
			int count = bsdiffInfoEntityMapper.insertSelective(bsdiffInfoEntity);//先操作数据库，再上传文件。
			if(count == 1){
				FileUtilsCommons.uploadFilesUtil(rootPath, nfsBsdiffPath+VERPATH+"/"+originalFilename, bsdiffFile);
				File directoryPatch = new File(rootPath+nfsBsdiffPath+PATCHPATH);
				if(!directoryPatch.exists()){//如果目录不存在创建目录
					directoryPatch.mkdirs();
				}
				for(int i=Integer.valueOf(bsdiffVer)-1;i>0;i--){
					File oldFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+i+".zip");
					File newFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+originalFilename);
					File diffFile = new File(rootPath+nfsBsdiffPath+PATCHPATH+"/"+bsdiffVer+"_"+i+".zip");

					BSDiff.bsdiff(oldFile,newFile,diffFile);
				}
			}
		}

	}

	public List<BsdiffInfoEntity> listAll(){
		return bsdiffInfoEntityMapper.selectAllBsdiff();
	}
}
