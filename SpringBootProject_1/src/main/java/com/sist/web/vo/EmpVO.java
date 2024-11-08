package com.sist.web.vo;

import java.util.*;

import lombok.Data;

//Spring to Spring-Boot
// XML파일 최소한 - 어노테이션 사용
//FrameWork -- Spring-Boot 포함
//장점 : 소스 최소화, 서버 역할만 수행(프론트는 별도작성)---html(ThymelLeaf), Vue, React, Next

@Data
public class EmpVO {
	private int empno, sal;
	private String ename, job, dbday;
	private Date hiredate;
}
