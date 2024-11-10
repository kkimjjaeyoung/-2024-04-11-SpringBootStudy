package com.sist.web.restcontroller;

import com.sist.web.dao.*;
import com.sist.web.entity.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") 
public class MemberRestController {
	@Autowired
	private MemberDAO mDao;
	
	
	@GetMapping("/member/login/{id}/{pwd}")
	public ResponseEntity<Map> memberLogin(@PathVariable("id") String id,
		@PathVariable("pwd") String pwd){
		Map map=new HashMap();
		try{
			int count=mDao.idCount(id);
		    if(count==0) {
		    	map.put("msg", "NOID");
		    }
		    else {
		    	MemberEntity vo=mDao.findById(id);
		    	if(vo.getPwd().equals(pwd)) {
			    	map.put("msg", "OK");
			    	map.put("name", vo.getName());
			    	map.put("id", vo.getId());
			    	map.put("sex", vo.getSex());
		    	}
		    	else {
		    		map.put("msg", "NOPWD");
		    	}
		    }
		}catch(Exception ex){
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
			return new ResponseEntity<>(map,HttpStatus.OK);// onSuccess
	   }
}
