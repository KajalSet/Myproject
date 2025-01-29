package com.deliveryBoy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,String> {

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.orderDate = :date AND oe.orderstatus IN ('ACCEPTED', 'IN_TRANSIT')")
    List<OrderEntity> findOrdersByDateAndStatus(@Param("date") LocalDate date);

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.orderstatus = :orderStatus")
    List<OrderEntity> findOrdersByStatus(@Param("orderStatus") OrderStatus orderStatus);

    List<OrderEntity> findAll();
    Optional<OrderEntity> findById(UUID id); // Changed to use the id field instead of orderId

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderstatus = :orderStatus")
    int countByStatus(@Param("orderStatus") OrderStatus orderStatus);

    @Query("SELECT o FROM OrderEntity o WHERE o.availabilityStatus = :status AND o.deliveryBoyId = :deliveryBoyId")
    List<OrderEntity> findOrdersByAvailabilityAndDeliveryBoyId(@Param("status") AvailabilityStatus status, @Param("deliveryBoyId") UUID deliveryBoyId);

    List<OrderEntity> findByOrderDate(LocalDate orderDate);
    
    List<OrderEntity> findByOrderstatusIn(List<OrderStatus> statuses);
}
