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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.MyBoardDAO;
import com.sist.web.entity.MyBoardEntity;


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
			List<MyBoardEntity> list=mbDao.myBoardListData(start);
			for(MyBoardEntity rb:list){
			   String day=rb.getRegdate();
			   day=day.substring(0,day.indexOf(" "));
			   rb.setRegdate(day);
		   }
		   int totalpage=(int)(Math.ceil(mbDao.count()/10.0));
		   map.put("list", list);
		   map.put("curpage", page);
		   map.put("totalpage", totalpage);
		   map.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	   }catch(Exception ex){
		   return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		   // onError
	   }
	   return new ResponseEntity<>(map,HttpStatus.OK); //onSuccess
   }
}
