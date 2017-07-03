package de.rwthaachen.webtech.rabatt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.repository.DiscountRepository;

@Service
public class SearchDiscountServiceImpl implements SearchDiscountService {
	@Autowired
	DiscountRepository reppo;

	@Override
	public List<Discount> findByTitle(String title) {
		List<Discount> discounts = new ArrayList<Discount>();
		reppo.findAll().parallelStream().forEach((dis) -> {
			if (dis.getTitle().toLowerCase().contains(title.toLowerCase())) {
				discounts.add(dis);
			}
		});
		return discounts;
	}

}
