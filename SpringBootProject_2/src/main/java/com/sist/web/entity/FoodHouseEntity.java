package com.sist.web.entity;
/*
 * VO
 * DTO
 * Entity : 다른 데이터 첨부 불가. 테이블의 컴럼만 추가 가능(컬럼명과 동일해야함)
 * 		- INSERT, UPDATE, DELETE 문장 생성
 * 		- SELECT : SQL문장 / 자동 SQL 문장 생성
 * 		- 검색
 * 			findBy컬럼명()
 * 					-- findByFno(int fno)
 * 					-- WHERE fno=
 * 					-- 메소드로 SQL 문장 처리
 * 					-- JPA (Hibernate) : 자동 SQL문장 제작(Method 패턴으로 제작)
 * 						---adress="" AND type=""
 * 						---findByAddressAndType(String address, String type)
 * 						--- sql문장으로도 가능
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="food_house")
@Data
public class FoodHouseEntity {
	@Id 	//sequence(자동증가)
	private int fno;
	private int jjimcount, likecount, hit, replycount;
	private String name, phone, address, theme, poster, images, time, parking, content, rdays, type;
	private double score;
}
