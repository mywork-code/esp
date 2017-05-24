package com.apass.esp.domain.dto.picture;

import org.springframework.web.multipart.MultipartFile;

public class AddPictureEntity {
	
    private MultipartFile pictureFile;

	public MultipartFile getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
	}
}
