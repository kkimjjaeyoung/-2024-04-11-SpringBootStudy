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
public interface HotelVO {
	public int getHno();
	public String getName();
	public int getPrice();
	public int getHit();
	public String getAddress();
	public String getLocation();
	public String getPoster();
	
}
