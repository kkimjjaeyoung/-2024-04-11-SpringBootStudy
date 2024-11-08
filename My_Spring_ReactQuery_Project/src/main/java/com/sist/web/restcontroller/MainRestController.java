package com.sist.web.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dao.BookDAO;

@RestController
@CrossOrigin(origins = "*")
public class MainRestController {
	@Autowired
	private BookDAO bookDao;
	
	
}
