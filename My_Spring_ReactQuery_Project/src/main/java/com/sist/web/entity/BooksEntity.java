package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
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
@Entity(name="book")
@Data
public class BooksEntity {
	@Id
	private int bid;
	private int catid, bprice, pyear, bpages, bcell;
	private String catname, bname, publisher, state, bcontent,bimage;
}
