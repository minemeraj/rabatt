package de.rwthaachen.webtech.rabatt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.service.SearchDiscountService;

@RestController
@RequestMapping("/api/v1/sdiscounts")
public class SearchDiscountController {

	@Autowired
	SearchDiscountService discountService;
	
	

	@RequestMapping(value = "/search/findByTitle/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Discount>> findByTitle(@PathVariable("title") String title) {
		List<Discount> discounts = discountService.findByTitle(title);
		if (discounts.isEmpty()) {
			return new ResponseEntity<List<Discount>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Discount>>(discounts, HttpStatus.OK);
	}

}
