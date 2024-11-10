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
public class HotelRestController {
	@Autowired
	private HotelDAO hDao;
	
	@GetMapping("/hotel/HotelList/{page}")
	public ResponseEntity<Map> hotel_list(@PathVariable("page") int page){
		Map map=new HashMap();
		try{
			int rowSize=15;
			int start=(rowSize*page)-rowSize;
			List<HotelVO> hList=hDao.hotelListData(start);
			int count=(int)hDao.count();
			int totalpage=(int)(Math.ceil(count/15.0));
			
			final int BLOCK=10;
			int startPage=((page-1)/BLOCK*BLOCK)+1;
			int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
			if(endPage>totalpage)
				endPage=totalpage;
			
			map.put("hList", hList);
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
	@GetMapping("/hotel/HotelDetail/{hno}")
    public ResponseEntity<HotelEntity> hotel_detail(@PathVariable("hno") int hno){
		HotelEntity vo=new HotelEntity();
    	try {
    		vo=hDao.findByHno(hno);

    	}catch (Exception ex) {
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<>(vo,HttpStatus.OK);
    }
	
	//도서검색
	@GetMapping("hotel/HotelFind/{page}/{address}")
    public ResponseEntity<Map> hotel_find(@PathVariable("page") int page,
    		@PathVariable("name") String name)
    {
    	Map map=new HashMap();
    	try
    	{
    		int rowSize=12;
    		int start=(page-1)*rowSize;
    		List<HotelVO> hList=hDao.hotelFindData(start, name);		//params 순서 주의!
    		int totalpage=hDao.hotelFindTotalPage(name);
    		final int BLOCK=10;
    		int startPage=((page-1)/BLOCK*BLOCK)+1;
    		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
            if(endPage>totalpage)
            	endPage=totalpage;
            
            map.put("hList", hList);
            map.put("curpage", page);
            map.put("totalpage", totalpage);
            map.put("startPage", startPage);
            map.put("endPage", endPage);
    	}catch(Exception ex){
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return new ResponseEntity<>(map,HttpStatus.OK); // 정상 수행 => 200
    }
	
	
	//인기목록
	@GetMapping("/hotel/HotelCell/{page}")
	public ResponseEntity<Map> hotel_cell(@PathVariable("page") int page){
		Map map=new HashMap();
		try{
			int rowSize=15;
			int start=(rowSize*page)-rowSize;
			List<HotelVO> hList=hDao.hotelHitData(start);
			int count=(int)hDao.count();
			int totalpage=(int)(Math.ceil(count/15.0));
			
			final int BLOCK=10;
			int startPage=((page-1)/BLOCK*BLOCK)+1;
			int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
			if(endPage>totalpage)
				endPage=totalpage;
			
			map.put("hList", hList);
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
