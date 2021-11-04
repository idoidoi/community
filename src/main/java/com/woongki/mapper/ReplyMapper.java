package com.woongki.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woongki.domain.Criteria;
import com.woongki.domain.ReplyVO;

public interface ReplyMapper {

	public int insert(ReplyVO vo);
	
	public List<ReplyVO> getList();
	
	public ReplyVO read(Long rno);
	
	public int delete (Long rno);
	
	public int update (ReplyVO vo);
	
	public List<ReplyVO> getListWithPageing(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	
	public int getCountByBno(Long bno);
}
