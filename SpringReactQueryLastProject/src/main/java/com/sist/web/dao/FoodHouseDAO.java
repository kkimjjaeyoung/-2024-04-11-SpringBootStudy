package com.sist.web.dao;
import java.util.*;
import com.sist.web.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodHouseDAO extends JpaRepository<FoodHouseEntity, Integer>{
	//main 출력
	@Query(value="SELECT fno,name,poster,score,hit,jjimcount,type,content,theme "
		       +"FROM food_house ORDER BY hit DESC "
		       +"LIMIT 0,9",nativeQuery = true)
	public List<FoodHouseVO> foodHitTop9();
	
	public FoodHouseEntity findByFno(int fno);		//자동 sql문장
	
	@Query(value="SELECT fno,name,poster,score,hit,jjimcount,type,content,theme "
	           +"FROM food_house ORDER BY fno ASC "
		       +"LIMIT :start,12",nativeQuery = true)
	// nativeQUery = true : 자체 sql문장을 제작(작성된 sql문장을 수행)
	public List<FoodHouseVO> foodListData(@Param("start") int start);
	//count() : 총페이지를 구할 필요 없다
	//이미 존제하는 메소드를 이용 => 복잡한 쿼리:SELECT
	//메소드 패턴을 이용 : SELECT에서 검색
	
	@Query(value="SELECT fno,name,poster,score,hit,jjimcount,type,content,theme "
	           +"FROM food_house WHERE address LIKE CONCAT('%',:address,'%') ORDER BY fno ASC "
		       +"LIMIT :start,12",nativeQuery = true)
	public List<FoodHouseVO> foodFindData(@Param("address") String address, @Param("start") int start);
	
	@Query (value = "SELECT CEIL(COUNT(*)/12.0) "
				+ "FROM food_house "
				+ "WHERE address LIKE CONCAT('%', :address, '%')")
	public int foodFindTotalPage(@Param("address") String address);
}
