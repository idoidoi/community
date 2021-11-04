package com.woongki.service;

import java.util.List;

import com.woongki.domain.Criteria;
import com.woongki.domain.ReplyPageDTO;
import com.woongki.domain.ReplyVO;

public interface ReplyService {
	
	public int register(ReplyVO vo);
	
	public ReplyVO get(Long rno);
	
	public int remove (Long rno);
	
	public int modify (ReplyVO vo);
	
	public List<ReplyVO> getList(Criteria cri, Long bno);
	
	public ReplyPageDTO getListPage(Criteria cri,Long bno);
}
