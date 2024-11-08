package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="food_house")
@Data
public class FoodHouseEntity {
	@Id 	//sequence(자동증가)
	private int fno;
	private int jjimcount, likecount, hit, replycount;
	private String name,phone,address,theme,poster,images,time,parking,content,rdays,type;
	private double score;
}
