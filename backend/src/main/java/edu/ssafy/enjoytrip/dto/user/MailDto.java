package edu.ssafy.enjoytrip.dto.user;

import lombok.Data;

@Data
public class MailDto {
	private String receiver;
	private String title;
	private String content;
}
