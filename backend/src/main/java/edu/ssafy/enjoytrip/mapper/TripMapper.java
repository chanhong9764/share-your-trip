package edu.ssafy.enjoytrip.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.ssafy.enjoytrip.dto.trip.TripDto;

@Mapper
public interface TripMapper {
	void SelectTrip(TripDto tripDto) throws Exception;
	void deleteTrip(String tripInfoId) throws Exception;
	int changeTrip(TripDto tripDto) throws Exception;
	ArrayList<TripDto> getTrip(String roomId) throws Exception;
	void updateSelectedList(TripDto tripDto) throws Exception;
}
