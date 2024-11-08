package com.sist.web.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

@RestController
@CrossOrigin(origins = "*")
public class BookRestController {
	@Autowired
	private BookDAO bokDao;
	
	@GetMapping("/books/BookList/{page}")
	public ResponseEntity<Map> book_list(@PathVariable("page") int page){
		Map map=new HashMap();
		try{
			int rowSize=15;
			int start=(rowSize*page)-rowSize;
			List<BooksVO> bokList=bokDao.bookListData(start);
			int count=(int)bokDao.count();
			int totalpage=(int)(Math.ceil(count/15.0));
			
			final int BLOCK=10;
			int startPage=((page-1)/BLOCK*BLOCK)+1;
			int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
			if(endPage>totalpage)
				endPage=totalpage;
			
			map.put("bokList", bokList);
			map.put("curpage", page);
			map.put("totalpage", totalpage);
			map.put("startPage", startPage);
			map.put("endPage", endPage);

		}catch(Exception ex){
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			return new ResponseEntity<>(map,HttpStatus.OK); // 200
	}
	
	//상세보기
	@GetMapping("/books/BookDetail/{bId}")
    public ResponseEntity<BooksEntity> book_detail(@PathVariable("bId") int bId){
		BooksEntity vo=new BooksEntity();
    	try {
    		vo=bokDao.findByBid(bId);

    	}catch (Exception ex) {
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<>(vo,HttpStatus.OK);
    }
	
	//도서검색
	@GetMapping("book/BookFind/{page}/{address}")
    public ResponseEntity<Map> book_find(@PathVariable("page") int page,
    		@PathVariable("bName") String bName)
    {
    	Map map=new HashMap();
    	try
    	{
    		int rowSize=12;
    		int start=(page-1)*rowSize;
    		List<BooksVO> bfList=bokDao.bookFindData(start, bName);		//params 순서 주의!
    		int totalpage=bokDao.bookFindTotalPage(bName);
    		final int BLOCK=10;
    		int startPage=((page-1)/BLOCK*BLOCK)+1;
    		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
            if(endPage>totalpage)
            	endPage=totalpage;
            
            map.put("bfList", bfList);
            map.put("curpage", page);
            map.put("totalpage", totalpage);
            map.put("startPage", startPage);
            map.put("endPage", endPage);
    	}catch(Exception ex)
    	{
    		// {isLoading,isError,error,data}
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    		/*
    		 *   *** 500 : 소스 에러 
    		 *   *** 404 : 파일이 없는 경우
    		 *   *** 400 : Bad Request => 데이터 전송이 틀린 경우 
    		 *   415 : 한글 변환 
    		 *   403 : 접근 거부 
    		 */
    	}
    	return new ResponseEntity<>(map,HttpStatus.OK); // 정상 수행 => 200
    }
	
	
	//인기목록
	@GetMapping("/books/BookCell/{page}")
	public ResponseEntity<Map> book_cell(@PathVariable("page") int page){
		Map map=new HashMap();
		try{
			int rowSize=15;
			int start=(rowSize*page)-rowSize;
			List<BooksVO> bokList=bokDao.bookCellData(start);
			int count=(int)bokDao.count();
			int totalpage=(int)(Math.ceil(count/15.0));
			
			final int BLOCK=10;
			int startPage=((page-1)/BLOCK*BLOCK)+1;
			int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
			if(endPage>totalpage)
				endPage=totalpage;
			
			map.put("bokList", bokList);
			map.put("curpage", page);
			map.put("totalpage", totalpage);
			map.put("startPage", startPage);
			map.put("endPage", endPage);

		}catch(Exception ex){
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			return new ResponseEntity<>(map,HttpStatus.OK); // 200
		}
}
