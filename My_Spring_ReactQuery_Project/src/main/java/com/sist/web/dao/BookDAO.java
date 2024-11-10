package com.sist.web.dao;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.*;


/*
bId int 
catId int 
catName text 
bName text 
publisher text 
state text 
bPrice int 
bContent text 
pYear int 
bPages int 
bImage text 
bCell int
 */

@Repository
public interface BookDAO extends JpaRepository<BooksEntity, Integer>{
	//home
	@Query(value="SELECT bId, bName, bImage, bContent, catName "
		       +"FROM book ORDER BY bCell ASC "
		       +"LIMIT 0,2",nativeQuery = true)
	public List<BooksVO> bookHitTop2();
	
	//list
	@Query(value="SELECT bId, bName, bImage, bContent, catName "
	           +"FROM book ORDER BY bId ASC "
		       +"LIMIT :start,15",nativeQuery = true)
	public List<BooksVO> bookListData(@Param("start") int start);
	
	//BId에 대한 값
	public BooksEntity findByBid(int bid);
	
	//find
	@Query(value="SELECT bId, bName, bImage, bContent, catName "
	           +"FROM book "
	           + "WHERE bname LIKE CONCAT('%',:bName,'%') "
	           + "ORDER BY bId ASC "
		       +"LIMIT :start,15",nativeQuery = true)
	public List<BooksVO> bookFindData(@Param("start") int start, @Param("bName") String bName);
	@Query (value = "SELECT CEIL(COUNT(*)/15.0) "
			+ "FROM book "
			+ "WHERE bname LIKE CONCAT('%',:bName,'%')")
	public int bookFindTotalPage(@Param("bName") String bName);	//param와 :bname(어노테이션)명이 일치해야함
	
	//인기목록
	@Query(value="SELECT bId, bName, bImage, bContent, catName "
	           +"FROM book ORDER BY bCell DESC "
		       +"LIMIT :start,15",nativeQuery = true)
	public List<BooksVO> bookCellData(@Param("start") int start);
}
