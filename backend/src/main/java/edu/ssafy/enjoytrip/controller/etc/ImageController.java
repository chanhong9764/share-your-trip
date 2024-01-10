package edu.ssafy.enjoytrip.controller.etc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Api(tags = { "이미지 컨트롤러 API" })
public class ImageController {
	@Value("${file.path}")
	private String uploadPath;
	
	@GetMapping("/{date}/{imagepath}")
	public ResponseEntity<byte[]> getImageFile(@PathVariable("imagepath") String imagepath, @PathVariable("date") String date) {
		byte[] imageByteArray = null;
		try {
			InputStream imageStream = new FileInputStream(uploadPath + "/" + date + "/" + imagepath);
			imageByteArray = IOUtils.toByteArray(imageStream);
			imageStream.close();
		} catch (IOException e) {
			throw new RestApiException(CustomResponseCode.IMAGE_NOT_FOUND);
		}
		return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
		// 테스트하고 코드 수정
		//return SuccessResponse.createSuccess(SuccessCode.LOAD_IMAGE_SUCCESS, imageByteArray);
	}
}


