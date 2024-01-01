package edu.ssafy.enjoytrip.service.trip;

import java.util.ArrayList;

import edu.ssafy.enjoytrip.dto.trip.TripDto;

public interface TripService {
	void SelectTrip(TripDto tripDto) throws Exception;
	void deleteTrip(String tripInfoId) throws Exception;
	int changeTrip(TripDto tripDto) throws Exception;
	ArrayList<TripDto> getTrip(String roomId) throws Exception;
	void updateSelectedList(ArrayList<TripDto> tripList) throws Exception;
}
