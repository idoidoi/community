package com.woongki.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.woongki.*;
import com.woongki.domain.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testGetList() {
		log.info("---------------------");
		boardMapper.getList();
	}
	
	@Test
	public void testInsert() {
		log.info("------------------");
		BoardVO vo = new BoardVO();
		vo.setTitle("제목테스트");
		vo.setContent("내용테스트");
		vo.setWriter("글쓴이테스트");
		boardMapper.insertSelectKey(vo);
	}
	@Test
	public void testUpdate() {
		log.info("------------------");
		BoardVO vo = new BoardVO();
		vo.setBno(4L);
		vo.setTitle("제목테스트123");
		vo.setContent("내용테스트123");
		vo.setWriter("글쓴이테스트12333");
		boardMapper.update(vo);
		
		boardMapper.getList();
	}
	
	@Test
	public void testDelete() {
		log.info("------------------");
		boardMapper.delete(4L);
		boardMapper.getList();
	}
	
	@Test
	public void testRead() {
		log.info("------------------");
		BoardVO vo = new BoardVO();
		vo.setBno(23l);
		boardMapper.read(21L);
	}
	
}
