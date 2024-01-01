package edu.ssafy.enjoytrip.service.trip;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.trip.TripDto;
import edu.ssafy.enjoytrip.mapper.TripMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("TripServiceImpl")
public class TripServiceImpl implements TripService {
	private final TripMapper tripMapper;
	
	@Override
	public void SelectTrip(TripDto tripDto) throws Exception {
		tripMapper.SelectTrip(tripDto);
	}

	@Override
	public void deleteTrip(String tripInfoId) throws Exception {
		tripMapper.deleteTrip(tripInfoId);
	}

	@Override
	public int changeTrip(TripDto tripDto) throws Exception {
		return 0;
	}
	
	@Override
	public ArrayList<TripDto> getTrip(String roomId) throws Exception {
		return tripMapper.getTrip(roomId);
	}
	
	@Override
	public void updateSelectedList(ArrayList<TripDto> tripList) throws Exception {
		for(TripDto t : tripList) {
			tripMapper.updateSelectedList(t);			
		}

	}
}
