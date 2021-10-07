package com.woongki.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woongki.domain.BoardVO;
import com.woongki.mapper.BoardMapper;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service //컨트롤러 같은녀석
@Log4j
@RequiredArgsConstructor //final로 하면 알아서 주입을 해준다
@ToString
public class BoardServicempl implements BoardService {
	
	private final BoardMapper boardmapper;

	@Override
	public long resister(BoardVO board) {
		boardmapper.insertSelectKey(board);
		return board.getBno();
	}

	@Override
	public BoardVO get(Long bno) {
		return boardmapper.read(bno);
	}

	@Override
	public int modify(BoardVO board) {
		return boardmapper.update(board);
	}

	@Override
	public int remove(Long bno) {
		return boardmapper.delete(bno);
	}

	@Override
	public List<BoardVO> getList() {
		return boardmapper.getList();
	}

	
}

