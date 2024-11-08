package com.sist.web.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="board")
@Data
@NoArgsConstructor		//매개변수 없는 디폴트 생성자
@AllArgsConstructor		//매개변수가 있는 생성자
public class BoardEntity {
	@Id
	private int no;
	private int hit;
	private String subject;
	private String content;
	@Column(insertable=true, updatable=false)		//insert만 가능, update불가능
	private String pwd;
	private String name;
	@Column(insertable=true, updatable=false)		//insert만 가능, update불가능
	private String regdate;
	
	//날짜 변환
	@PrePersist
	public void regdate() {
		this.regdate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd"));
	}
}
