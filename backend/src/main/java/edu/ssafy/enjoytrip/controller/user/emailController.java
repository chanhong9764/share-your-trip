package edu.ssafy.enjoytrip.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ssafy.enjoytrip.dto.user.MailDto;
import edu.ssafy.enjoytrip.service.user.MailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequestMapping("/api/email")
@RestController
@RequiredArgsConstructor
public class emailController {

    private final MailService mailService;
   
    @GetMapping
    public ResponseEntity<Map<String,Object>> MailSend(@RequestParam("receiver") String mail){
    	Map<String,Object> map = new HashMap<String,Object>();
    	System.out.println(mail);
		try {
			int number = mailService.sendMail(mail);
			map.put("resmsg", "입력성공");
			map.put("resdata", number);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "입력실패");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(map,HttpStatus.OK);
		return res;
    }
}