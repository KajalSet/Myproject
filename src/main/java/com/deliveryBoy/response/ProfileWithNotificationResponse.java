package com.deliveryBoy.response;

import java.util.List;

import com.deliveryBoy.entity.NotificationEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileWithNotificationResponse {
	private Long userId;
    private String name;
    private String phoneNumber;
    private byte[] profilePhoto;
    private List<NotificationEntity> notifications;
}
