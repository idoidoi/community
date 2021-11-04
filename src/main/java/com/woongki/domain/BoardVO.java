package com.woongki.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BoardVO {
	private Long bno;
	private String title, content,writer;
	private Date regdate, updateDate;
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
}
