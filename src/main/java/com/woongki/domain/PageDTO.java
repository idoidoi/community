package com.woongki.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PageDTO {

	private int startPage,endPage;
	private boolean prev, next;
	
	private int total; //총 페이지수
	private Criteria cri; 
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
			
		this.endPage = (int)(Math.ceil(cri.getPageNum()/ 10.0)) * 10;
							// 13/10.0 =>1.3 => 2 *  10 => endPage = 20
							// 3/10.0 => 0.3 => 1 * 10 => endpage = 10
		this.startPage = endPage - 9;
							//20 - 9 =>11
		this.prev = this.startPage > 1;
							//11 > 1 true라서 prev 생성
		int realEnd = (int)(Math.ceil((total * 1.0) / cri.getAmount())); 
							//145 * 1.0 => 145.0 / 10 => 14.5 => 15
		this.endPage = realEnd <= endPage? realEnd : endPage; //true == realend  false == endPage
						//15 <= 20 
		this.next = this.endPage < realEnd;
		
	}
}
