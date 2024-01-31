package edu.ssafy.enjoytrip.dto.trip;

import edu.ssafy.enjoytrip.dto.user.UserDto;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Alias("trip")
public class Trip {
	private int tripInfoId;
	private String y;
	private String x;
	private String roadAddressName;
	private int sequence;
	private String category;
	private String createdAt;
	private int roomId;
	private String placeName;
	private String placeUrl;
	private String phone;

	@Builder
	public Trip(int tripInfoId, String y, String x, String roadAddressName, int sequence, String category, String createdAt, int roomId, String placeName, String placeUrl, String phone) {
		this.tripInfoId = tripInfoId;
		this.y = y;
		this.x = x;
		this.roadAddressName = roadAddressName;
		this.sequence = sequence;
		this.category = category;
		this.createdAt = createdAt;
		this.roomId = roomId;
		this.placeName = placeName;
		this.placeUrl = placeUrl;
		this.phone = phone;
	}

	public TripDto.TripInfoDTO toTripListResponse() {
		return TripDto.TripInfoDTO.builder()
				.tripInfoId(tripInfoId)
				.y(y)
				.x(x)
				.roadAddressName(roadAddressName)
				.sequence(sequence)
				.category(category)
				.createdAt(createdAt)
				.roomId(roomId)
				.placeName(placeName)
				.phone(phone)
				.build();
	}
}


