package edu.ssafy.enjoytrip.dto.trip;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("tripdto")
public class TripDto {
	private int trip_info_id;
	private String y;
	private String x;
	private String road_address_name;
	private int sequense;
	private String category;
	private String createdAt;
	private int roomId;
	private String place_name;
	private String place_url;
	private String phone;
}


