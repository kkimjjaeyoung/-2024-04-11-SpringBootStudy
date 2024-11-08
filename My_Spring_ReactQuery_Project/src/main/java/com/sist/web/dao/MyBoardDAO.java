package com.sist.web.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.MyBoardEntity;

import java.util.*;


@Repository
public interface MyBoardDAO extends JpaRepository<MyBoardEntity, Integer>{
    @Query(value="SELECT * FROM my_board "
    		    +"ORDER BY no DESC "
    		    +"LIMIT :start,10",nativeQuery = true)
    public List<MyBoardEntity> myBoardListData(@Param("start") int start);
    
    public MyBoardEntity findByNo(int no);
}
