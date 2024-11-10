package com.sist.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.*;
import java.util.*;


/*
	public int getHno();
	public int getPrice();
	public int getHit();
	public String getAddress();
	public String getLocation();
	public String getPoster();
 */

@Repository
public interface HotelDAO extends JpaRepository<HotelEntity, Integer>{
		//home
		@Query(value="SELECT hno, name, price, address, location, poster, hit "
			       +"FROM hotel ORDER BY hit ASC "
			       +"LIMIT 0,3",nativeQuery = true)
		public List<HotelVO> hotelHitTop3();
		
		//list
		@Query(value="SELECT hno, name, price, address, location, poster, hit "
		           +"FROM hotel ORDER BY hno ASC "
			       +"LIMIT :start,15",nativeQuery = true)
		public List<HotelVO> hotelListData(@Param("start") int start);
		
		//BId에 대한 값
		public HotelEntity findByHno(int hno);
		
		//find
		@Query(value="SELECT hno, name, price, address, location, poster, hit "
		           +"FROM hotel "
		           + "WHERE name LIKE CONCAT('%',:name,'%')) "
		           + "ORDER BY hno ASC "
			       +"LIMIT :start,15",nativeQuery = true)
		public List<HotelVO> hotelFindData(@Param("start") int start, @Param("name") String name);
		
		@Query (value = "SELECT CEIL(COUNT(*)/15.0) "
				+ "FROM hotel "
				+ "WHERE name LIKE CONCAT('%',:name,'%') AND name LIKE '호텔'")
		public int hotelFindTotalPage(@Param("name") String name);	//param와 :name(어노테이션)명이 일치해야함
		
		//인기목록
		@Query(value="SELECT hno, name, price, address, location, poster, hit "
		           +"FROM hotel ORDER BY hit DESC "
			       +"LIMIT :start,15",nativeQuery = true)
		public List<HotelVO> hotelHitData(@Param("start") int start);
	
}
