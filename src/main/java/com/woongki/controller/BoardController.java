package com.woongki.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.woongki.*;

import com.woongki.domain.BoardVO;
import com.woongki.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		
		log.info("list.............");
		model.addAttribute("list",service.getList());
	}
	@GetMapping("/register")
	public void registerGET() {
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		Long bno = service.resister(board);
		
		log.info("----------------"+bno);
		
		rttr.addFlashAttribute("result", bno); // 세션
		
		return "redirect:/board/list";
	}
	@PostMapping("/modify")
	public String modify( BoardVO board, RedirectAttributes rttr) {
			
		int count = service.modify(board);
		
		if(count == 1) {
			rttr.addFlashAttribute("result","success"); 
		}
		return "redirect:/board/list";
	}
	@PostMapping("/remove")
	public String remove(@RequestParam("bno")Long bno, RedirectAttributes rttr) {
		
		int count = service.remove(bno);
		
		if(count == 1) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
}