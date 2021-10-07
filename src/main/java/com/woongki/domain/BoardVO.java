package com.woongki.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BoardVO {
	private Long bno;
	private String title, content,writer;
	private Date regdate, updateDate;
}
