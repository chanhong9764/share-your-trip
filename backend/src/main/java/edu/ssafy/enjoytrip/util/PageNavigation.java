package edu.ssafy.enjoytrip.util;

import lombok.Data;

@Data
public class PageNavigation {
	
	private boolean startRange; // 현재 페이지가 이전이 눌려지지 않는 범위의 페이지 체크
	private boolean endRange; // 현재 페이지가 다음이 눌려지지 않는 범위의 페이지 체크
	private int totalCount; // 총 게시글 갯수
	private int newCount; // 새글 갯수
	private int totalPageCount; // 총 페이지 갯수
	private int currentPage; // 현재 페이지 번호
	private int naviSize; // 네비게이션 사이즈
	private int countPerPage; // 페이지당 글 갯수
	private String navigator;


	public void makeNavigator() {
		int startPage = currentPage - 5;
		int endPage = startPage + naviSize - 1;
		if (startPage < 1) {
			startPage = 1;
			endPage = startPage + naviSize - 1;
		}
		if(totalPageCount < endPage) {
			endPage = totalPageCount;			
			startPage -= (endPage - totalPageCount);
			if (startPage <1) startPage = 1;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("		<ul class=\"pagination  justify-content-center\"> \n");
		builder.append("			<li class=\"page-item\" data-pg=\"1\"> \n");
		builder.append("				<a href=\"#\" class=\"page-link\">최신</a> \n");
		builder.append("			</li> \n");
		builder.append("			<li class=\"page-item\" data-pg=\"" + ((this.currentPage - naviSize) < 1? 1 : (this.currentPage - naviSize))+ "\"> \n");
		builder.append("				<a href=\"#\" class=\"page-link\">이전</a> \n");
		builder.append("			</li> \n");
		for(int i=startPage;i<=endPage;i++) {
			builder.append("			<li class=\"" + (currentPage == i ? "page-item active" : "page-item") + "\" data-pg=\"" + i + "\"><a href=\"#\" class=\"page-link\">" + i + "</a></li> \n");
		}
		builder.append("			<li class=\"page-item\" data-pg=\"" + ((this.currentPage + naviSize) > totalPageCount? totalPageCount : (this.currentPage + naviSize))+ "\"> \n");
		builder.append("				<a href=\"#\" class=\"page-link\">다음</a> \n");
		builder.append("			</li> \n");
		builder.append("			<li class=\"page-item\" data-pg=\"" + totalPageCount + "\"> \n");
		builder.append("				<a href=\"#\" class=\"page-link\">마지막</a> \n");
		builder.append("			</li> \n");
		builder.append("		</ul> \n");
		this.navigator = builder.toString();
	}

}
