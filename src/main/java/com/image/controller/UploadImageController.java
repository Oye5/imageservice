package com.image.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.image.dto.response.GenericResponse;
import com.image.dto.response.ProductGenericResponse;
import com.image.util.ImageUploadUtil;

@RestController
public class UploadImageController {

	@Autowired
	private ImageUploadUtil imageUploadUtil;

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile[] file) {
		GenericResponse response = new GenericResponse();
		ProductGenericResponse productGenericResponse = new ProductGenericResponse();
		try {
			List<String> keyList = null;
			if (file.length != 0) {
				keyList = imageUploadUtil.uploadImage(file);

				productGenericResponse.setImagekeyList(keyList);
			}
			return new ResponseEntity<ProductGenericResponse>(productGenericResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("E001");
			response.setMessage(e.getMessage());
			return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
	}
}
