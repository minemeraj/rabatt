package de.rwthaachen.webtech.rabatt.service;

import java.util.List;

import de.rwthaachen.webtech.rabatt.model.Discount;

public interface SearchDiscountService {
	public List<Discount> findByTitle(String title);
}
