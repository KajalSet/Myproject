package com.deliveryBoy.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor 
public class ApiResponse<T> {
	private boolean success;
    private T data;
    
    
    
    
}
