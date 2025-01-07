//package com.deliveryBoy.repository;
//
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.deliveryBoy.entity.OrderEntity;
//import com.deliveryBoy.enums.OrderStatus;
//
//@Repository
//public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
//
//	
//
//	List<OrderEntity> findByStatusIn(List<OrderStatus> list);
//
//	List<OrderEntity> findByOrderDateAndStatus(LocalDate today, OrderStatus accepted);
//
//}
