package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="mymember")
@Data
public class MemberEntity {
	@Id
	private int no;
	private String id,name,sex,pwd;
}
