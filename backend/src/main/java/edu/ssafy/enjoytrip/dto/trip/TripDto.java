package edu.ssafy.enjoytrip.dto.trip;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("trip")
public class TripDto {
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
}


