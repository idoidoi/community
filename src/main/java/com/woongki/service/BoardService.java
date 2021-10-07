package com.woongki.service;

import java.util.List;

import com.woongki.domain.BoardVO;

public interface BoardService {
	
	long resister(BoardVO board);
	
	BoardVO get(Long bno);
	
	int modify(BoardVO board);
	
	int remove(Long bno);
	
	List<BoardVO> getList();
	
	
}
