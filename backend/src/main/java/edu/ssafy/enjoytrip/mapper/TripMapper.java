package edu.ssafy.enjoytrip.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.ssafy.enjoytrip.dto.trip.TripDto;

@Mapper
public interface TripMapper {
	void insertTrip(TripDto tripDto);
	int deleteTrip(String tripInfoId);
	ArrayList<TripDto> getTrip(String roomId);
	int updateSelectedList(TripDto tripDto);
}
