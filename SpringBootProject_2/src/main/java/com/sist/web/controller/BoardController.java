package com.sist.web.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

@Controller
public class BoardController {
	@Autowired
	private BoardDAO dao;
	
	@GetMapping("board/list")
	public String board_list(String page, Model model) {
		if(page==null) page="1";
		
		int curpage=Integer.parseInt(page);
		int rowSize=10;
		int start=(rowSize*curpage)-rowSize;
		
		List<BoardData> list=dao.boardListData(start);
		
		int count=(int)dao.count();
		int totalpage=(int)(Math.ceil(count/10.0));
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		
		return "board/list";	
	}
	
	@GetMapping("board/insert")
	public String board_insert() {
		return "board/insert";
	}
	
	@PostMapping("board/insert_ok")
	public String board_insert_ok(BoardEntity vo) {
		dao.save(vo);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("board/detail")
	public String board_detail(int no, Model model) {
		//조회수 증가
		BoardEntity vo=dao.findByNo(no);
		vo.setHit(vo.getHit()+1);
		dao.save(vo);
		
		vo=dao.findByNo(no);
		model.addAttribute("vo", vo);
		
		return "board/detail";
	}
	
	@GetMapping("board/delete")
	public String board_delete(int no, Model model) {
		model.addAttribute("no", no);
		
		return "board/delete";
	}
	
	@PostMapping("board/delete_ok")
	@ResponseBody	//script, html, json 전송
	public String board_delete_ok(int no, String pwd) {
		BoardEntity vo=dao.findByNo(no);
		String result="";
		if(pwd.equals(vo.getPwd())) {
			result="<script>"
					+ "location.href=\"\"/board/list\""
					+ "</script>";
			dao.delete(vo);		//insert[save()], delete[delete()], update[save()]는 사전구성
		}
		else {
			result="<script>"
					+ "alert(\"Password Fail!\");"
					+ "</script>";
					
		}
		
		return result;
	}
	
	
	@GetMapping("board/update")
	public String board_update(int no, Model model) {
		BoardEntity vo=dao.findByNo(no);
		model.addAttribute("vo", vo);
		
		return "board/update";
	}
	
	@PostMapping("board/update_ok")
	@ResponseBody
	public String board_update_ok(BoardEntity vo) {
		String result="";
		BoardEntity dbvo=dao.findByNo(vo.getNo());
		if(vo.getPwd().equals(dbvo.getPwd())) {
			//0값 초기화 방지
			vo.setHit(dbvo.getHit());		
			dao.save(vo);

			//수정
			result="<script>"
					+ "location.href=\"\"/board/list\""
					+ "</script>";
			dao.save(vo);		//insert[save()], delete[delete()], update[save()]는 사전구성
		}
		else {
			result="<script>"
					+ "alert(\"Password Fail!\");"
					+ "</script>";
					
		}
		
		return "result";
	}
	
}
