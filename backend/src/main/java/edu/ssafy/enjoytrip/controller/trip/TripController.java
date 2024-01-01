package edu.ssafy.enjoytrip.controller.trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import edu.ssafy.enjoytrip.dto.trip.TripDto;
import edu.ssafy.enjoytrip.service.trip.TripService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@Api(tags={"여행 컨트롤러 API"})
@RequiredArgsConstructor
@RequestMapping("/api/trips")
public class TripController {
	private final TripService service;
	
	@GetMapping("/{roomId}")
	private ResponseEntity<Map<String, Object>> GetTrip(@PathVariable("roomId") String roomId) {
		Map<String,Object> map= new HashMap<String,Object>();
		try {
			ArrayList<TripDto> triplist = service.getTrip(roomId);
			map.put("result", triplist);
			map.put("msg", "조회성공");
		} catch (Exception e) {
			map.put("result", "조회실패");
			map.put("msg", e.getMessage());
		}
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<>(map,HttpStatus.OK);
		return res;
	}

}
