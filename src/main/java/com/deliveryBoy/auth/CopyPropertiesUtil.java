package com.deliveryBoy.auth;

import org.springframework.beans.BeanUtils;

import com.deliveryBoy.entity.UserEntity;

public class CopyPropertiesUtil {
	public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);

	}
}
