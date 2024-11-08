package com.sist.web.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.dao.*;
@RestController
@CrossOrigin(origins="*")
public class BoardRestController {
   @Autowired
   private BoardDAO bDao;
   
   // ==> useQuery , useMutation , useQueries 
   // 목록   ===> GetMapping
   @GetMapping("/board/list/{page}")
   public ResponseEntity<Map> board_list(@PathVariable("page") int page)
   {
	   System.out.println("1");
	   Map map=new HashMap();
	   try
	   {
		   int rowSize=10;
		   int start=(page-1)*rowSize;
		   List<ReactBoardEntity> list=bDao.boardListData(start);
		   for(ReactBoardEntity rb:list)
		   {
			   String day=rb.getRegdate();
			   day=day.substring(0,day.indexOf(" "));
			   rb.setRegdate(day);
		   }
		   int totalpage=(int)(Math.ceil(bDao.count()/10.0));
		   map.put("list", list);
		   map.put("curpage", page);
		   map.put("totalpage", totalpage);
		   map.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	   }catch(Exception ex)
	   {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   // onError
	   }
	   return new ResponseEntity<>(map,HttpStatus.OK); //onSuccess
   }
   //--------------------------------------
   // 글쓰기  ===> PostMapping
   @PostMapping("/board/insert")
   public ResponseEntity<Map> board_insert(@RequestBody ReactBoardEntity vo)
   {
	   Map map=new HashMap();
	   try
	   {
		   ReactBoardEntity _vo=bDao.save(vo);
		   map.put("vo", _vo);
		   map.put("msg", "yes");
		   
	   }catch(Exception ex)
	   {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   return new ResponseEntity<>(map,HttpStatus.CREATED);
   }
   // 수정하기 ===> PutMapping
   @GetMapping("/board/update/{no}")
   public ResponseEntity<ReactBoardEntity> board_update(@PathVariable("no") int no){
	   ReactBoardEntity vo=new ReactBoardEntity();
	   try {
		   vo=bDao.findByNo(no);
	   }catch (Exception ex) {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
	   return new ResponseEntity<>(vo,HttpStatus.OK);
   }
   
   // 삭제하기 ===> DeleteMapping
   @DeleteMapping("/board/delete/{no}/{pwd}")
   public ResponseEntity<Map> board_delete(@PathVariable("no") int no, @PathVariable("pwd") String pwd){
	   Map map=new HashMap<>();
	   try {
		   ReactBoardEntity vo=bDao.findByNo(no);
		   if(pwd.equals(vo.getPwd())) {
			   bDao.delete(vo);
			   map.put("msg", "yes");
		   }
		   else {
			   map.put("msg","no");
		   }
	   }catch (Exception ex) {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   		return new ResponseEntity<>(map, HttpStatus.OK);
   }
   
   
   //-------------------------------------- Mutation
   // 상세보기 ===> GetMapping 
   @GetMapping("/board/detail/{no}")
   public ResponseEntity<ReactBoardEntity> board_detail(@PathVariable("no") int no)
   {
	   ReactBoardEntity vo=new ReactBoardEntity();
	   try
	   {
		   vo=bDao.findByNo(no);
		   vo.setHit(vo.getHit()+1);
		   bDao.save(vo); // 조회수 증가
		   vo=bDao.findByNo(no);
	   }catch(Exception ex)
	   {
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   return new ResponseEntity<>(vo,HttpStatus.OK);
   }
}