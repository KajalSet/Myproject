package com.deliveryBoy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.HomeEntity;
import com.deliveryBoy.enums.OrderStatus;

@Repository
public interface HomeRepository extends JpaRepository<HomeEntity, Long> {

@Query("SELECT he FROM HomeEntity he WHERE he.orderDate= :date AND he.status IN('Accepted','In Transit')")	
List<HomeEntity> findOrdersByDateAndStatus(@Param("date") LocalDate date);

@Query("SELECT e FROM HomeEntity e WHERE e.status= :status")
List<HomeEntity> findOrderByStatus(@Param("status") String status);

Optional<HomeEntity> findByOrderId(String orderId);


@Query("SELECT COUNT(h) FROM HomeEntity h WHERE h.status= :status")
int countByStatus(@Param ("status")OrderStatus status);

}
