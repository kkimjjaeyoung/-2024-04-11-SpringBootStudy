package com.sist.web.restcontroller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.MyBoardDAO;
import com.sist.web.entity.*;


@RestController
@CrossOrigin(origins = "*")
public class MyBoardRestController {
	@Autowired
	private MyBoardDAO mbDao;
	
	@GetMapping("/myboard/BoardList/{page}")
	public ResponseEntity<Map> myboard_list(@PathVariable("page") int page){

		Map map=new HashMap();
		try{
			int rowSize=10;
			int start=(page-1)*rowSize;
			List<MyBoardEntity> bodlist=mbDao.myBoardListData(start);
			for(MyBoardEntity rb:bodlist){
			   String day=rb.getRegdate();
			   day=day.substring(0,day.indexOf(" "));
			   rb.setRegdate(day);
		   }
		   int totalpage=(int)(Math.ceil(mbDao.count()/10.0));
		   map.put("bodlist", bodlist);
		   map.put("curpage", page);
		   map.put("totalpage", totalpage);
		   map.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	   }catch(Exception ex){
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   // onError
	   }
	   return new ResponseEntity<>(map,HttpStatus.OK); //onSuccess
   }
	
	
	// 상세보기 ===> GetMapping 
	@GetMapping("/myboard/BoardDetail/{no}")
	public ResponseEntity<MyBoardEntity> myboard_detail(@PathVariable("no") int no){
		MyBoardEntity vo=new MyBoardEntity();
		try{
			vo=mbDao.findByNo(no);
			vo.setHit(vo.getHit()+1);
			mbDao.save(vo); // 조회수 증가
			vo=mbDao.findByNo(no);
		}catch(Exception ex){
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(vo,HttpStatus.OK);
	}
	 @PostMapping("/myboard/BoardInsert")
	   public ResponseEntity<Map> board_insert(@RequestBody MyBoardEntity vo)
	   {
		   Map map=new HashMap();
		   try
		   {
			   MyBoardEntity _vo=mbDao.save(vo);
			   map.put("vo", _vo);
			   map.put("msg", "yes");
			   
		   }catch(Exception ex)
		   {
			   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   }
		   return new ResponseEntity<>(map,HttpStatus.CREATED);
	   }
	   // 수정하기 ===> PutMapping
	   @GetMapping("/myboard/BoardUpdate/{no}")
	   public ResponseEntity<MyBoardEntity> board_update(@PathVariable("no") int no)
	   {
		     MyBoardEntity vo=new MyBoardEntity();
		     try
		     {
		    	 vo=mbDao.findByNo(no);
		     }catch(Exception ex)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		     }
		     return new ResponseEntity<>(vo,HttpStatus.OK);
	   }
	   /*
	    *                                 @PathVariable("no")
	    *   apiClient.put(`/board/update_ok/${no}`,{
	                name:name,
	                subject:subject,
	                content:content,
	                pwd:pwd
	            })@RequestBody MyBoardEntity vo
	    */
	   @PutMapping("/myboard/BoardUpdate_ok/{no}")
	   public ResponseEntity<Map> board_update_ok(@PathVariable("no") int no,
			   @RequestBody MyBoardEntity vo)
	   {
		    /*
		     *   private int no;
		
			    private String name; // insert,update
			    private String subject;// insert,update
			    private String content; // insert,update
			    
			    @Column(insertable = true,updatable = false)
			    private String pwd;
			    @Column(insertable = true , updatable = false)
			    private String regdate;
			    
			    private int hit;//// insert,update
		     */
		     Map map=new HashMap();
		     try
		     {
		    	 MyBoardEntity dbvo=mbDao.findByNo(no);
		    	 if(vo.getPwd().equals(dbvo.getPwd()))// 수정
		    	 {
		    		 vo.setNo(no);
		    		 vo.setHit(dbvo.getHit());
		    		 mbDao.save(vo);// save() = insert/update 
		    		 // no가 있는 경우(update) / no가 없는 경우(insert) 
		    		 map.put("msg", "yes");
		    	 }
		    	 else
		    	 {
		    		 map.put("msg", "no");
		    	 }
		     }catch(Exception ex)
		     {
		    	 return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		    	 // onError
		     }
		     return new ResponseEntity<>(map,HttpStatus.OK); // onSuccess
	   }
	   // 삭제하기 ===> DeleteMapping
	   // /board/delete/${no}/${pwd}
	   @DeleteMapping("/myboard/BoardDelete/{no}/{pwd}")
	   //-------------------------------------- Mutation
	   public ResponseEntity<Map> board_delete(@PathVariable("no") int no,
			   @PathVariable("pwd") String pwd)
	   {
		   Map map=new HashMap();
		   try
		   {
			   MyBoardEntity vo=mbDao.findByNo(no);
			   if(pwd.equals(vo.getPwd()))
			   {
				   mbDao.delete(vo);
				   map.put("msg", "yes");
			   }
			   else
			   {
				   map.put("msg", "no");
			   }
			   
		   }catch(Exception ex)
		   {
			   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			   //onError
		   }
		   return new ResponseEntity<>(map,HttpStatus.OK);
		   // onSuccess
	   }
	   
}
