package com.deliveryBoy.controller;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.request.OTPVerificationRequest;
import com.deliveryBoy.request.OrderStatusRequest;
import com.deliveryBoy.response.OrderResponse;
import com.deliveryBoy.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/category")
	public ResponseEntity<List<OrderResponse>> getOrdersByCategory(@RequestParam("category")String category){
		try {
			List<OrderResponse> orders=orderService.getOrdersByCategory(category);
			return new ResponseEntity<>(orders,HttpStatus.OK); 
		}
		catch(IllegalArgumentException e){
			return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{orderId}/status")
	public ResponseEntity<String> updateOrderStatus(
			@PathVariable Long orderId,
			@RequestBody OrderStatusRequest statusRequest, @RequestParam(required=false)String updatedBy  ){
		
		try {
			
			if (updatedBy == null || updatedBy.isEmpty()) {
	            updatedBy = "System";
	        }
	        orderService.updateOrderStatus(orderId, statusRequest.getStatus(), updatedBy);

	        return new ResponseEntity<>("Order status updated successfully.", HttpStatus.OK);
		
		} catch (IllegalArgumentException e) {
			
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            
        } catch (EntityNotFoundException e) {
        	
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            
        }catch (Exception e) {
	        return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
		
      }//update status
	
	
	@PostMapping("/{orderId}/verify-otp")
    public ResponseEntity<String> verifyOtpAndMarkDelivered( @PathVariable Long orderId, @RequestBody OTPVerificationRequest otpRequest) {
		
		
        try {
            orderService.verifyOtpAndMarkDelivered(orderId, otpRequest.getOtp());
            
            return new ResponseEntity<>("Order successfully delivered.", HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
        	
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            
        } catch (EntityNotFoundException e) {
        	
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            
        }
    }
	
	
	
	
	     
	
	
	
	
	
		
	
	
	
	
}
