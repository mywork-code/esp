package com.apass.esp.service.common;

import com.apass.esp.domain.entity.BsdiffEntity;
import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.mapper.BsdiffInfoEntityMapper;
import com.apass.esp.utils.FileUtilsCommons;
import com.tencent.tinker.bsdiff.BSDiff;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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


	/**
	 * 思路:
	 * 1,先判断:对应ver是否已经存在，版本号和文件名是否一致
	 * 2,判断是否是第一次上传:
	 * 				是,创建verzip目录，插入数据库，把源zip包上传至该目录
	 * 				否,插入数据库，把zip包传入verzip目录，创建zip目录把，递减循环ver-1，用bsdiff方法把ver_i存储到patchzip目录中
	 * @param bsdiffEntity
	 * @param bsdiffInfoEntity
	 * @throws IOException
     */
    @Transactional
    public void bsdiffUpload(BsdiffEntity bsdiffEntity, BsdiffInfoEntity bsdiffInfoEntity) throws IOException {
        StringBuffer sb = new StringBuffer();

		//如果版本号已存在，给出提示
        String bsdiffVer = bsdiffEntity.getBsdiffVer();

		BsdiffInfoEntity entity = selectByVer(bsdiffVer);
		if(entity != null){
			throw new RuntimeException("版本号已经存在，请重新填写版本号!");
		}
//        List<BsdiffInfoEntity> bsdiffInfoEntities = listAll();
//        if(CollectionUtils.isNotEmpty(bsdiffInfoEntities)){
//            for (BsdiffInfoEntity bsEn: bsdiffInfoEntities) {
//                if(StringUtils.equals(bsdiffVer,bsEn.getBsdiffVer())){
//                    throw new RuntimeException("版本号已经存在，请重新填写版本号!");
//                }
//            }
//        }

		//判断上传文件类型，并且限制文件名和版本号要一致
        MultipartFile bsdiffFile = bsdiffEntity.getBsdiffFile();
        String[] split = bsdiffFile.getOriginalFilename().split("\\.");
        if(!StringUtils.equals("zip",split[1])){
            throw new RuntimeException("请上传zip文件 .");
        }
        if(!StringUtils.equals(bsdiffVer,split[0])){
            throw new RuntimeException("版本要与zip文件名一致.");
        }

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
				//先上传源zip包
				FileUtilsCommons.uploadFilesUtil(rootPath, nfsBsdiffPath+VERPATH+"/"+originalFilename, bsdiffFile);
				File directoryPatch = new File(rootPath+nfsBsdiffPath+PATCHPATH);
				if(!directoryPatch.exists()){//如果目录不存在创建目录
					directoryPatch.mkdirs();
				}

				//再上传增量更新包
				for(int i=Integer.valueOf(bsdiffVer)-1;i>0;i--){
					File oldFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+i+".zip");
					File newFile = new File(rootPath+nfsBsdiffPath+VERPATH+"/"+originalFilename);
					File diffFile = new File(rootPath+nfsBsdiffPath+PATCHPATH+"/"+bsdiffVer+"_"+i+".zip");

					BSDiff.bsdiff(oldFile,newFile,diffFile);
				}
			}
		}
    }

	/**
	 *  根据用版本号查询数据是否已经存在
	 * @param bsdiffVer
	 * @return
     */
	private BsdiffInfoEntity selectByVer(String bsdiffVer) {
		BsdiffInfoEntity entity = new BsdiffInfoEntity();
		entity.setBsdiffVer(bsdiffVer);
		return bsdiffInfoEntityMapper.selectByEntityQuery(entity);
	}

	/**
	 * 查询所有
	 * @return
     */
	public List<BsdiffInfoEntity> listAll(){
		return bsdiffInfoEntityMapper.selectAllBsdiff();
	}
}
