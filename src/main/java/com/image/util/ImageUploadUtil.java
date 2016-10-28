package com.image.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadUtil {

	@Autowired
	AmazonS3Util amazonS3Util;

	
	@Value("${aws.s3.folder}")
	public String folder;// = "product";

	public List<String> uploadImage(MultipartFile[] file) {
		List<String> keyList = new ArrayList<String>();
		for (int j = 0; j < file.length; j++) {
			if (!file[j].isEmpty()) {
				try {
					String token = UUID.randomUUID().toString();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(token.getBytes());

					byte byteData[] = md.digest();

					// convert the byte to 64 hex format
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
					}

					String key = amazonS3Util.generateKey(sb.toString());
					System.out.println("keyy="+key);
					// upload thumb nail
					if (j == 0) {
						String thumbKey = key + "_thumb.jpg";
						amazonS3Util.uploadFileToS3(thumbKey, file[j].getInputStream(), file[j].getOriginalFilename(),
								folder);
						System.out.println("thumb=="+thumbKey);
						keyList.add(thumbKey);
					}
					String keyName = key + ".jpg";
					
					// upload file to amazon
					try {
						amazonS3Util.uploadFileToS3(keyName, file[j].getInputStream(), file[j].getOriginalFilename(),
								folder);
					} catch (Exception e) {
						e.printStackTrace();
					}
					keyList.add(keyName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return keyList;
	}

}