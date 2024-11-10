package com.sist.web.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

@RestController
@CrossOrigin(origins = "*")
public class MainRestController {
	@Autowired
	private BookDAO bookDao;
	
	@Autowired
	private MyBoardDAO boardDao;
	
	 @GetMapping("/main/home")
	  public ResponseEntity<Map> home_data(){
		  Map map=new HashMap();
		  
		  try{
			  List<BooksVO> bList=bookDao.bookHitTop2();
			  List<MyBoardEntity> mbList=boardDao.myBoardHit3();
			  
			  map.put("bList", bList);
			  map.put("mbList", mbList);
			  /*
			  List<BooksVO> bookData=new ArrayList<BooksVO>();
			  for(int i=1;i<=2;i++){
				  bookData.add(bList.get(i));
			  }
			  List<MyBoardEntity> boardData=new ArrayList<MyBoardEntity>();
			  for(int i=1;i<=3;i++){
				  boardData.add(mbList.get(i));
			  }
			  
			  map.put("bookData", bookData);
			  map.put("boardData", boardData);
			  */
			  System.out.println(map);
		  }catch(Exception ex){
			  return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		  }
		  return new ResponseEntity<>(map,HttpStatus.OK);
		  // HttpStatus.OK => 200 => 정상 수행 
	  }
	
	
}
