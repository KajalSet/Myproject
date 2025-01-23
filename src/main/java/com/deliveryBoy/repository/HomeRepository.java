package com.deliveryBoy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.HomeEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

@Repository
public interface HomeRepository extends JpaRepository<HomeEntity, UUID> {

    @Query("SELECT he FROM HomeEntity he WHERE he.orderDate= :date AND he.status IN('Accepted','In Transit')")
    List<HomeEntity> findOrdersByDateAndStatus(@Param("date") LocalDate date);
    
    @Query("SELECT he FROM HomeEntity he WHERE he.orderstatus = :status")
    List<HomeEntity> findOrdersByStatus(@Param("status") OrderStatus orderStatus);  // Use orderstatus instead of status

    List<HomeEntity> findAll();
    Optional<HomeEntity> findByOrderId(String orderId);

    @Query("SELECT COUNT(h) FROM HomeEntity h WHERE h.status= :status")
    int countByStatus(@Param ("status")OrderStatus status);

    @Query("SELECT h FROM HomeEntity h WHERE h.availabilityStatus = :status AND h.deliveryBoyId = :deliveryBoyId")
    List<HomeEntity> findOrdersByAvailabilityAndDeliveryBoyId(@Param("status") AvailabilityStatus status, @Param("deliveryBoyId") UUID deliveryBoyId);
}
