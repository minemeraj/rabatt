package de.rwthaachen.webtech.rabatt.elasticrepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import de.rwthaachen.webtech.rabatt.model.Discount;
import java.lang.String;
import java.util.List;

public interface SearchDiscountRepository extends ElasticsearchRepository<Discount, String> {
	List<Discount> findByTitle(String title);
}
