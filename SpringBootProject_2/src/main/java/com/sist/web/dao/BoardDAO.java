package com.sist.web.dao;

import com.sist.web.entity.*;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDAO extends JpaRepository<BoardEntity, Integer>{
	public BoardEntity findByNo(int no);		//상세보기
	
	@Query(value = "SELECT no, name, subject, content, date_format(regdate, '%Y-%MM-%d') as regdate, hit "
			+ "FROM board "
			+ "ORDER BY no DESC "
			+ "LIMIT :start, 10", nativeQuery = true)
	public List<BoardData> boardListData(@Param("start") int start);
	//가독성 낮음, 적은 코딩 구현 가능
}
