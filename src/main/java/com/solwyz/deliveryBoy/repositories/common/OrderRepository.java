package com.solwyz.deliveryBoy.repositories.common;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.deliveryBoy.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByDeliveryBoyIdAndStatus(Long deliveryBoyId, String status);

	List<Order> findByStatus(String status);

	List<Order> findByOrderDateBetween(Date startDate, Date endDate);

}
