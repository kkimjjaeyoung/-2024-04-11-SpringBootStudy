package com.sist.web.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.sist.web.dao.*;
import com.sist.web.entity.*;


@RestController
@CrossOrigin(origins = "*")
public class FoodHouseRestController {
	@Autowired	//스프링에서 메모리 할당을 한 경우만 autowired 사용 가능
	private FoodHouseDAO fDao;
	/*
	 * 클래스는 반드시 메모리 할당 후에 사용 : new (결합성 강한 프로그램)
	 * 스프링의 클래스 할당
	 * 	1. <bean> : 라이브러리 클래스를 메모리 할당하는 경우(공통으로 사용되는 경우)
	 * 	2. 어노테이션 : 개발자가 주로 사용하는 방식
	 * 		@Component : 일반 클래스(~Manager : OpenApi)
	 * 		@Repository : DAO(데이터베이스 연동. Oracle, MySQL, ElasticSearch.....)
	 * 		@Service : BI(다수의 DAO 통합해 사용)
	 * 		@Controller : 웹 파일 제어(유지보수 다수)
	 * 			-- Front / Back 을 구분해서 제어(JSON데이터로 변경해 수령)
	 * 		@RestController : JSON으로 전송해 다른 언어와 연동
	 * 		@ControllerAdvice : 공통예외처리(Controller)
	 * 		@RestControllerAdvice : 공통예외처리(RestController)
	 * 
	 * 	1.web.xml/server.xml : 경로확인(<Context>. SpringBoot에선 생략-임베디드 tomcat자체 처리)
	 * 		-- 사용 프레임워크 분석(Spring)
	 * 		-- 연결 파일 파악(application_*.xml)
	 *  2. 동작
	 *  	-- 요청(.do)
	 */
	// 맛집 목록 출력 
    @GetMapping("/food/list/{page}")
    public ResponseEntity<Map> food_list(@PathVariable("page") int page){
    	Map map=new HashMap();
    	try
    	{
    		int rowSize=12;
    		int start=(rowSize*page)-rowSize;
    		List<FoodHouseVO> fList=fDao.foodListData(start);
    		int count=(int)fDao.count();
    		int totalpage=(int)(Math.ceil(count/12.0));
    		
    		final int BLOCK=10;
    		int startPage=((page-1)/BLOCK*BLOCK)+1;
    		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
            if(endPage>totalpage)
            	endPage=totalpage;
            
            map.put("fList", fList);
            map.put("curpage", page);
            map.put("totalpage", totalpage);
            map.put("startPage", startPage);
            map.put("endPage", endPage);
    	}catch(Exception ex)
    	{
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    		// 404 , 500 , 415 ,405 ,400
    	}
    	return new ResponseEntity<>(map,HttpStatus.OK); // 200
    }
    
    // /food/find/${curpage}/${address}
    @GetMapping("food/find/{page}/{address}")
    public ResponseEntity<Map> food_find(@PathVariable("page") int page,
    		@PathVariable("address") String address)
    {
    	Map map=new HashMap();
    	try
    	{
    		int rowSize=12;
    		int start=(page-1)*rowSize;
    		List<FoodHouseVO> fList=fDao.foodFindData(address, start);
    		int totalpage=fDao.foodFindTotalPage(address);
    		final int BLOCK=10;
    		int startPage=((page-1)/BLOCK*BLOCK)+1;
    		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
            if(endPage>totalpage)
            	endPage=totalpage;
            
            map.put("fList", fList);
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
    
    @GetMapping("food/detail/{fno}")
    public ResponseEntity<FoodHouseEntity> food_detail(@PathVariable("fno") int fno){
    	FoodHouseEntity vo=new FoodHouseEntity();
    	try {
    		//조회수증가
    		vo=fDao.findByFno(fno);
    		vo.setHit(vo.getHit()+1);
    		fDao.save(vo);
    		
    		vo=fDao.findByFno(fno);
    	}catch (Exception ex) {
    		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<>(vo,HttpStatus.OK);
    }
	
}