package edu.ssafy.enjoytrip.service.trip;

import java.util.ArrayList;

import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.trip.TripDto;
import edu.ssafy.enjoytrip.mapper.TripMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("TripServiceImpl")
public class TripServiceImpl implements TripService {
	private final TripMapper tripMapper;
	
	@Override
	public void insertTrip(TripDto tripDto) {
		try {
			tripMapper.insertTrip(tripDto);
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.INVALID_TRIP_INSERT);
		}
	}

	@Override
	public void deleteTrip(String tripInfoId) {
		int cnt = tripMapper.deleteTrip(tripInfoId);
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.TRIP_NOT_FOUND);
		}
	}
	
	@Override
	public ArrayList<TripDto> getTrip(String roomId) {
		ArrayList<TripDto> list = tripMapper.getTrip(roomId);
		if(list == null || list.isEmpty()) {
			throw new RestApiException(CustomResponseCode.TRIP_LIST_NOT_FOUND);
		}
		return list;
	}
	
	@Override
	public void updateSelectedList(ArrayList<TripDto> tripList) {
		for(TripDto t : tripList) {
			int cnt = tripMapper.updateSelectedList(t);
			if(cnt == 0) {
				throw new RestApiException(CustomResponseCode.TRIP_NOT_FOUND);
			}
		}
	}
}
