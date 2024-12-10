package com.deliveryBoy.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
public class SuccessResponse {
	private String message;
	private Object data;
	private HttpStatus status;
	private LocalDateTime timeStamp;

}
