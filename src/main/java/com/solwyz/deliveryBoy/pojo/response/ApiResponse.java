package com.solwyz.deliveryBoy.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private String success;
	private T data;

//	public ApiResponse(String success, T data) {
//		this.success = success;
//		this.data = data;
//	}

	public ApiResponse(String success, T data) {
		this.success = success;
		this.data = data;
	}

	// Getters and setters
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
