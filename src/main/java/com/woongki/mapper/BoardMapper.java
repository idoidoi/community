package com.woongki.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.woongki.domain.BoardVO;
import com.woongki.domain.Criteria;

public interface BoardMapper {
	
	
	List<BoardVO> getList();
	
	void insertSelectKey(BoardVO board);
	
	int update(BoardVO board);
	
	int delete(Long bno);
	
	BoardVO read(Long bno);
	
	List<BoardVO> getListWithPaging(Criteria cri);
	
	int getTotalCount(Criteria cri); //검색조건 추가 이유
	
	List<BoardVO> searchTest(Map<String, Map<String,String>> map);
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	
}
