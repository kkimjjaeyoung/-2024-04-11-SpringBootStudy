package com.sist.web.controller;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// ObjectMApper 자동처리 : JSON
@RestController
public class MainRestController {
	@GetMapping("/names")
	public List<String> main_name(){
		List<String> list=new ArrayList<>();
		list.add("홍길동");
		list.add("심청이");
		list.add("박문수");
		
		return list;
	}
}
