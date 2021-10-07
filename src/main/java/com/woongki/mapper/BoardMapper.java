package com.woongki.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.woongki.domain.BoardVO;

public interface BoardMapper {
	

	List<BoardVO> getList();
	
	void insertSelectKey(BoardVO board);
	
	int update(BoardVO board);
	
	int delete(Long bno);
	
	BoardVO read(Long bno);
}
