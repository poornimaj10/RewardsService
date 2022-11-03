package com.application.rewards.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.rewards.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByPhone(String phone);

}
