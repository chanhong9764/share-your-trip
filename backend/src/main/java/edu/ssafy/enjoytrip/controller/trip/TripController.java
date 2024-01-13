package edu.ssafy.enjoytrip.controller.trip;

import java.util.ArrayList;


import edu.ssafy.enjoytrip.response.code.SuccessCode;
import edu.ssafy.enjoytrip.response.structure.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private ResponseEntity<Object> GetTrip(@PathVariable("roomId") String roomId) {
		ArrayList<TripDto> list = service.getTrip(roomId);

		return SuccessResponse.createSuccess(SuccessCode.LOAD_LIST_TRIP_SUCCESS, list);
	}

}
