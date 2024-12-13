package com.deliveryBoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.RejectOrderReason;
import com.deliveryBoy.request.HomeRequest;
import com.deliveryBoy.service.HomeService;

@RestController
@RequestMapping("/api/home")
public class HomeController {
	@Autowired
	private HomeService homeService;
	
	
	@GetMapping("/today-orders")
	public ResponseEntity<List<HomeRequest>> getTodayOrders(){
		
		List<HomeRequest> orders=homeService.getTodayOrders();
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	
	@GetMapping("/orders-by-status/{status}")
	public ResponseEntity<List<HomeRequest>> getOrdersByStatus(@PathVariable String status) {
	    List<HomeRequest> orders = homeService.getOrdersByStatus(status);
	    return ResponseEntity.ok(orders);
	}

	
	
	@PutMapping("/accept-order/{orderId}")
	public ResponseEntity<Void> acceptOrder(@PathVariable String orderId){
		
		homeService.acceptOrder(orderId);
		
		return ResponseEntity.ok().build();
		
	}
	

	
	@PutMapping("/reject-order/{orderId}")
	public ResponseEntity<Void> rejectOrder(@PathVariable String orderId,
			@RequestParam RejectOrderReason reason){
		
		   homeService.rejectOrder(orderId,reason);
				return ResponseEntity.ok().build();
		
	}
	
	@PutMapping("/{deliveryBoyId}/availabilty")
	public ResponseEntity<String> toggleAvailability(@PathVariable Long delivaeryBoyId,@RequestBody AvailabilityStatus status){
		homeService.toggleAvailabilty(delivaeryBoyId,status);
		
		return ResponseEntity.ok("Availabilty status updated to"+status);
		
	}	

	@GetMapping("/new-orders-count")
	public ResponseEntity<Integer> getNewOrdersCount(){
		
		int newOrdersCount=homeService.getNewOrdersCount();
		return ResponseEntity.ok(newOrdersCount);
		
	}
	
	
	
	
	
	
	
	
	
}
