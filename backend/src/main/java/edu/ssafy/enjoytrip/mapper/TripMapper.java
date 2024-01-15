package edu.ssafy.enjoytrip.mapper;

import java.util.ArrayList;
import java.util.List;

import edu.ssafy.enjoytrip.dto.trip.TripDto;
import org.apache.ibatis.annotations.Mapper;

import edu.ssafy.enjoytrip.dto.trip.Trip;

@Mapper
public interface TripMapper {
	void insertTrip(Trip trip);
	int deleteTrip(int tripInfoId);
	List<Trip> getTrip(String roomId);
	int updateSelectedList(Trip trip);
}
