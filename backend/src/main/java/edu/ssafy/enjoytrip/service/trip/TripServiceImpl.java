package edu.ssafy.enjoytrip.service.trip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ssafy.enjoytrip.dto.trip.TripDto;
import edu.ssafy.enjoytrip.dto.user.User;
import edu.ssafy.enjoytrip.response.code.CustomResponseCode;
import edu.ssafy.enjoytrip.response.exception.RestApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ssafy.enjoytrip.dto.trip.Trip;
import edu.ssafy.enjoytrip.mapper.TripMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("TripServiceImpl")
public class TripServiceImpl implements TripService {
	private final TripMapper tripMapper;
	
	@Override
	public void insertTrip(TripDto.TripInfoDTO requestDto) {
		try {
			tripMapper.insertTrip(requestDto.toEntity());
		} catch (DataIntegrityViolationException e) {
			throw new RestApiException(CustomResponseCode.INVALID_TRIP_INSERT);
		}
	}

	@Override
	public void deleteTrip(TripDto.DeleteDto requestDto) {
		int cnt = tripMapper.deleteTrip(requestDto.getTripInfoId());
		if(cnt == 0) {
			throw new RestApiException(CustomResponseCode.TRIP_NOT_FOUND);
		}
	}
	
	@Override
	public List<TripDto.TripInfoDTO> getTrip(String roomId) {
		List<Trip> tripList = tripMapper.getTrip(roomId);
		if(tripList == null || tripList.isEmpty()) {
			throw new RestApiException(CustomResponseCode.TRIP_LIST_NOT_FOUND);
		}
		return tripList.stream()
				.map(Trip::toTripListResponse)
				.collect(Collectors.toList());
	}
	
	@Override
	public void updateSelectedList(List<TripDto.TripInfoDTO>  requestDtoList) {
		for(TripDto.TripInfoDTO t : requestDtoList) {
			int cnt = tripMapper.updateSelectedList(t.toEntity());
			if(cnt == 0) {
				throw new RestApiException(CustomResponseCode.TRIP_NOT_FOUND);
			}
		}
	}
}
