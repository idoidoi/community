package com.woongki.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.woongki.*;
import com.woongki.domain.BoardVO;
import com.woongki.domain.Criteria;
import com.woongki.domain.PageDTO;

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
	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		List<BoardVO> list = boardMapper.getListWithPaging(cri);
		list.forEach( b -> log.info(b));
	}
	
	@Test
	public void testPageDTO() {
		Criteria cri = new Criteria();
		cri.setPageNum(13);
		PageDTO pageDTO = new PageDTO(cri,250);
		log.info(pageDTO);
	}
	@Test
	public void testSearch() {
		Map<String,String> map = new HashMap<>();
		map.put("T","TTT");
		map.put("C", "CCC");
		map.put("W", "WWWW");
		
		Map<String,Map<String,String>> outer = new HashMap<>();
		outer.put("map", map);
		
		List<BoardVO> list = boardMapper.searchTest(outer);
		
		log.info("ssssssssssssssssssssssssssssssss");
		log.info(list);
	}
	
	@Test
	public void testSearchPageing() {
		
		Criteria cri = new Criteria();
//		cri.setType("TCW");
//		cri.setKeyword("Test");
		
		List<BoardVO> list = boardMapper.getListWithPaging(cri);
		
		list.forEach( b -> log.info(b));
	}
}
