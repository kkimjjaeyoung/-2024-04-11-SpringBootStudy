package com.sist.web.entity;
/*
HNO int 
NAME text 
PRICE int 
ADDRESS text 
CHECKIN text 
CHECKOUT text 
LOCATION text 
POSTER text 
IMAGES text 
RDAYS text 
JJIMCOUNT int 
LIKECOUNT int 
HIT int 
SCORE double 
REPLYCOUNT text
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="hotel")
@Data
public class HotelEntity {
	@Id
	private int hno;
	private int price, jjimcount, likecount, hit;
	private double score;
	private String name, address, chjeckin, checkout, location, poster, images, rdays, replycount;
}
