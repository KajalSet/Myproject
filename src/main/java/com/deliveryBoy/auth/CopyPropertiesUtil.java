package com.deliveryBoy.auth;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;

public class CopyPropertiesUtil {
	public static Object copyProperties(Object source, Object dest) {
		BeanUtils.copyProperties(source, dest, getNullPropertyNames(source));
		return dest;
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
	
	
	}
	
	
	
}
