package com.deliveryBoy.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpinRequest {

@NotBlank(message = "Username cannot be empty.")
private String username;

@NotNull(message = "MPIN cannot be null.")
@Min(value = 1000, message = "MPIN must be a 4-digit number.")
@Max(value = 9999, message = "MPIN must be a 4-digit number.")
private Integer mpin;

}
