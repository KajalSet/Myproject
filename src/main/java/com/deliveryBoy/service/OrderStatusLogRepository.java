package com.deliveryBoy.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.OrderStatusLog;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long> {

}
