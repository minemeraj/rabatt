package de.rwthaachen.webtech.rabatt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.rwthaachen.webtech.rabatt.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

}
