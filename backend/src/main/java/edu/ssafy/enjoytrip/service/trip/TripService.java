package edu.ssafy.enjoytrip.service.trip;

import java.util.ArrayList;

import edu.ssafy.enjoytrip.dto.trip.TripDto;

public interface TripService {
	void insertTrip(TripDto tripDto);
	void deleteTrip(String tripInfoId);
	ArrayList<TripDto> getTrip(String roomId);
	void updateSelectedList(ArrayList<TripDto> tripList);
}
