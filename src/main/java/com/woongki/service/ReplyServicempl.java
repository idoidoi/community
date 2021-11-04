package com.woongki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woongki.domain.Criteria;
import com.woongki.domain.ReplyPageDTO;
import com.woongki.domain.ReplyVO;
import com.woongki.mapper.BoardMapper;
import com.woongki.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServicempl implements ReplyService {
	

	@Autowired
	private BoardMapper boardMapper;
	
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper; 
	
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}
	@Transactional
	@Override
	public int remove(Long rno) {
		
		ReplyVO vo = mapper.read(rno);
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		return mapper.getListWithPageing(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO( mapper.getCountByBno(bno),mapper.getListWithPageing(cri, bno)); // 댓글수, 해당 bno값의 list
	}

}
