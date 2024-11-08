package com.sist.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.sist.web.entity.FoodHouseData;
import com.sist.web.entity.FoodHouseEntity;

/*
 * MySql 
 * 	1. 페이징 =" Limit 시작, 갯수
 *  2. LIKE : '%'||?||'&' 	->	CONCAT('%',?,'%')
 *  3. DATE : DATETIME 	-> 	sysdate : now()
 *  4. NVL : isnull
 *  5. 데이터타입
 *  	
 */

@Repository
public interface FoodHouseDAO extends JpaRepository<FoodHouseEntity, Integer>{
	//목록 출력
	@Query(value="SELECT fno, poster, name FROM food_house ORDER BY fno ASC LIMIT :start,12", nativeQuery=true)		//limit : start에서 12개 불러오기
	public List<FoodHouseData> foodListData(@Param("start") int start);
	
	//상세보기
	public FoodHouseEntity findByFno(int fno);		//fno 찾기
	//SELECT * FROM food_house WHERE fno=?
	
	//hit 증가 = update (save())
	
}
