package com.woongki.service;

import java.util.List;

import com.woongki.domain.BoardAttachVO;
import com.woongki.domain.BoardVO;
import com.woongki.domain.Criteria;

public interface BoardService {
	
	void resister(BoardVO board);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO board);
	
	boolean remove(Long bno);
	
	List<BoardVO> getList();
	
	List<BoardVO> getList(Criteria cri);
	
	int getTotal(Criteria cri);
	
	List<BoardAttachVO> getAttachList(Long bno);
}
