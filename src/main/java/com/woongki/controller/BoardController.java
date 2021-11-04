package com.woongki.controller;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.woongki.*;
import com.woongki.domain.BoardAttachVO;
import com.woongki.domain.BoardVO;
import com.woongki.domain.Criteria;
import com.woongki.domain.PageDTO;
import com.woongki.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	
	private final BoardService service;
	
//	@GetMapping("/list")
//	public void list(Model model) {
//		
//		log.info("list.............");
//		model.addAttribute("list",service.getList());
//	}
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
//		log.info("list.............");
//		log.info(cri);
//		log.info("-=------------------");
		model.addAttribute("list",service.getList(cri));
		model.addAttribute("pageMaker",new PageDTO(cri,service.getTotal(cri)));
	}
	@GetMapping("/register")
	public void registerGET() {
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("------------------");
		log.info("register: " + board);
		
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attact -> log.info(attact));
		}
		
		
	service.resister(board);
		
		log.info("----------------------------");
		
		rttr.addFlashAttribute("result", board.getBno()); // 세션
		
		return "redirect:/board/list";
	}
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") long bno,@ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board", service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify( BoardVO board,Criteria cri, RedirectAttributes rttr) {
		log.info("modify---------------------------------------------1");
		
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		log.info("modify---------------------------------------------2");
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return "redirect:/board/list";
	}
//	@PostMapping("/remove")
//	public String remove(@RequestParam("bno")Long bno,Criteria cri, RedirectAttributes rttr) {
//		
//		int count = service.remove(bno);
//		
//		if(count == 1) {
//			rttr.addFlashAttribute("result","success");
//		}
//		rttr.addAttribute("pageNum",cri.getPageNum());
//		rttr.addAttribute("amount",cri.getAmount());
//		rttr.addAttribute("type",cri.getType());
//		rttr.addAttribute("keyword",cri.getKeyword());
//		return "redirect:/board/list";
//	}
	@GetMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {

		log.info("remove..." + bno);

		List<BoardAttachVO> attachList = service.getAttachList(bno);

		if (service.remove(bno)) {

			// delete Attach Files
			deleteFiles(attachList);

			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
	
private void deleteFiles(List<BoardAttachVO> attachList) {
	    
	    if(attachList == null || attachList.size() == 0) {
	      return;
	    }
	    
	    log.info("delete attach files...................");
	    log.info(attachList);
	    
	    attachList.forEach(attach -> {
	      try {        
	        Path file  = Paths.get("C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload"+attach.getUploadPath()+"\\" + attach.getUuid()+"_"+ attach.getFileName());
	        
	        Files.deleteIfExists(file); //일반파일 삭제
	        
	        if(Files.probeContentType(file).startsWith("image")) { //첨부파일 삭제
	        
	          Path thumbNail = Paths.get("C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload"+attach.getUploadPath()+"\\s_" + attach.getUuid()+"_"+ attach.getFileName());
	          
	          Files.delete(thumbNail);
	        }
	
	      }catch(Exception e) {
	        log.error("delete file error" + e.getMessage());
	      }//end catch
	    });//end foreachd
	  }
	
	@GetMapping(value = "/getAttachList",
			produces =  MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("asdsdssssssssssssssssssss");
		log.info("getAttachList: "+bno);
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
}
