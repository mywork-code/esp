package com.apass.esp.service.common;

import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.BsdiffQuery;
import com.apass.esp.domain.entity.BsdiffVo;
import com.apass.esp.mapper.BsdiffInfoEntityMapper;
import com.apass.esp.utils.FileUtilsCommons;
import com.apass.esp.utils.ZipUtil;
import com.apass.lib.utils.BsdiffUtils;
import org.apache.commons.lang3.StringUtils;
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
    public void bsdiffUpload(BsdiffVo bsdiffVo, BsdiffInfoEntity bsdiffInfoEntity) throws IOException {
		//如果版本号已存在，给出提示
        String bsdiffVer = bsdiffVo.getBsdiffVer();

		BsdiffQuery query = new BsdiffQuery();
		query.setLineId(bsdiffVo.getLineId());
		query.setBsdiffVer(bsdiffVo.getBsdiffVer());
		BsdiffInfoEntity entity = selectBsdiffInfoByVo(query);
		if(entity == null){
			throw new RuntimeException("版本号已经存在，请重新填写版本号!");
		}

        MultipartFile bsdiffFile = bsdiffVo.getBsdiffFile();
        String[] split = bsdiffFile.getOriginalFilename().split("\\.");
        if(!StringUtils.equals("zip",split[1])){
            throw new RuntimeException("请上传zip文件 .");
        }
        if(!StringUtils.equals(bsdiffVer,split[0])){
            throw new RuntimeException("版本要与zip文件名一致.");
        }

		String zipName = bsdiffFile.getOriginalFilename();
		String zipPath = nfsBsdiffPath+VERPATH+"/"+bsdiffVo.getLineId()+"/"+bsdiffVo.getBsdiffVer()+"/";

		bsdiffInfoEntity.setLineId(bsdiffVo.getLineId());
		bsdiffInfoEntity.setBsdiffVer(bsdiffVer);

		//判断服务器端是否有更早版本的文件，如果没有 直接上传，如果有 增量拆分
		File directory = new File(rootPath+zipPath);
		if(!directory.exists()){//如果目录不存在创建目录
			directory.mkdirs();
		}

		//先上传zip文件，方便后续解压
		FileUtilsCommons.uploadFilesUtil(rootPath,zipPath+zipName,bsdiffFile);
		if(!(directory.listFiles().length > 0)){//第一次上传
			//解压缩,并重成文件清单
//			ZipUtil.unZipFiles(rootPath+zipPath+zipName,rootPath+zipPath);
			new ZipUtil().unZipFiles(rootPath,zipPath,zipName);

			//合并并生成文件清单



			/*int count = bsdiffInfoEntityMapper.insertSelective(bsdiffInfoEntity);//先操作数据库，再上传文件。
			//没有增量拆分操作
			if(count == 1){
				FileUtilsCommons.uploadFilesUtil(rootPath, nfsBsdiffPath+VERPATH+"/"+originalFilename, bsdiffFile);
			}*/
		}else{//增量拆分
			int count = bsdiffInfoEntityMapper.insertSelective(bsdiffInfoEntity);//先操作数据库，再上传文件。
			if(count == 1){
				FileUtilsCommons.uploadFilesUtil(rootPath, nfsBsdiffPath+VERPATH+"/", bsdiffFile);
				File directoryPatch = new File(rootPath+nfsBsdiffPath+PATCHPATH);
				if(!directoryPatch.exists()){//如果目录不存在创建目录
					directoryPatch.mkdirs();
				}
				for(int i=Integer.valueOf(bsdiffVer)-1;i>0;i--){
//					File oldFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+i+".zip");
//					File newFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+originalFilename);
//					File diffFile = new File(rootPath+nfsBsdiffPath+PATCHPATH+"/"+bsdiffVer+"_"+i+".zip");

					BsdiffUtils.getInstance().bsdiff(rootPath+nfsBsdiffPath+VERPATH+"/"+i+".zip",rootPath+nfsBsdiffPath+VERPATH+"/",rootPath+nfsBsdiffPath+PATCHPATH+"/"+bsdiffVer+"_"+i+".zip");
				}
			}
		}
    }

	private BsdiffInfoEntity selectBsdiffInfoByVo(BsdiffQuery query) {
		return  bsdiffInfoEntityMapper.selectBsdiffInfoByVo(query);
	}

	public List<BsdiffInfoEntity> listAll(){
		return bsdiffInfoEntityMapper.selectAllBsdiff();
	}
}
