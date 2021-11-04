package com.woongki.mapper;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woongki.domain.ReplyVO;
import com.woongki.service.ReplyService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyServicempl {

	private Long[] bnoArr = {99L, 98L,97L,96L,95L};	
	
	@Autowired
	ReplyService replyservice;
	
	@Test
	public void registerServiceTests() {
			IntStream.rangeClosed(1,5).forEach( i -> {
			
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer" + i);
			
			replyservice.register(vo);
		});
	}
}
