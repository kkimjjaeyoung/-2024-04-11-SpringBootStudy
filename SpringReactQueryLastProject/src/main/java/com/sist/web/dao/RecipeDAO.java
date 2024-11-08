package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sist.web.dao.*;
import com.sist.web.entity.RecipeEntity;

@Repository
public interface RecipeDAO extends JpaRepository<RecipeEntity, Integer>{
	@Query(value="SELECT * FROM recipe ORDER BY no ASC LIMIT 0, 5", nativeQuery = true)
	public List<RecipeEntity> recipeMainData();
}
