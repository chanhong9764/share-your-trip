package edu.ssafy.enjoytrip.service.trip;

import java.util.List;

import edu.ssafy.enjoytrip.dto.trip.Trip;
import edu.ssafy.enjoytrip.dto.trip.TripDto;

public interface TripService {
	void insertTrip(TripDto.TripInfoDTO requestDto);
	void deleteTrip(TripDto.DeleteDto requestDto);
	List<TripDto.TripInfoDTO> getTrip(String roomId);
	void updateSelectedList(List<TripDto.TripInfoDTO> requestDtoList);
}
