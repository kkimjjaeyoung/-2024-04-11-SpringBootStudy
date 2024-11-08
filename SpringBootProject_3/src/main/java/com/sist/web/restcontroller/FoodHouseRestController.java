package com.sist.web.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.entity.*;
import com.sist.web.dao.*;

@RestController
@CrossOrigin(origins = "*")		//다른 포트도 접속 허용
public class FoodHouseRestController {
	@Autowired
	private FoodHouseDAO fDao;
	@Autowired
	private RecipeDAO rDao;
	@Autowired
	private ChefDAO cDao;
	
	@GetMapping("food/main_react")
	public Map foodMainTopData(){
		Map map=new HashMap();
		List<FoodHouseVO> fList=fDao.foodHitTop9();
		List<RecipeEntity> rList=rDao.recipeMainData();
		ChefEntity vo=cDao.findByChef("지선");
		
		map.put("fList", fList);
		map.put("rList", rList);
		map.put("cvo", vo);
		
		return map;
	}
	
	@GetMapping("food/list_react")
	public Map food_list(int page) {
		Map map=new HashMap();
		int start=(page*12)-11;
		
		List<FoodHouseVO> list=fDao.foodListData(start);
		int count=(int)fDao.count();
		int totalpage=(int)(Math.ceil(count/12.0));
		final int BLOCK=10;
		int startPage=((page-1)/BLOCK*BLOCK)+1;
		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage){
			endPage=totalpage;
		}
		map.put("list", list);
		map.put("curpage", page);
		map.put("totalpage", totalpage);
		map.put("startPage", startPage);
		map.put("entPage", endPage);
		
		return map;
	}
	
	@GetMapping("food/detail_react")
	public FoodHouseEntity food_detail(int fno) {
		FoodHouseEntity vo=fDao.findByFno(fno);
		   vo.setHit(vo.getHit()+1);// 조회수 증가 Cookie에 저장 (Cookie저장이 안된다)
			   fDao.save(vo);
			   vo=fDao.findByFno(fno);
			   return vo;
	}
	
	@GetMapping("food/find_react")
	public Map food_find(int page, String address) {
		Map map=new HashMap();
		int start=(page*12)-11;
		
		List<FoodHouseVO> list=fDao.foodFindData(address,  start);
		int totalpage=fDao.foodFindTotalPage(address);
		final int BLOCK=10;
		int startPage=((page-1)/BLOCK*BLOCK)+1;
		int endPage=((page-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage){
			endPage=totalpage;
		}
		map.put("list", list);
		map.put("curpage", page);
		map.put("totalpage", totalpage);
		map.put("startPage", startPage);
		map.put("entPage", endPage);
		map.put("address", address);
		
		return map;
	}
	
}