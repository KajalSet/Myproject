package com.deliveryBoy.auth;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

public class PrivilegeInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("Pre-handle");
		HandlerMethod hm;
		try {
			hm = (HandlerMethod) handler;
		} catch (ClassCastException e) {
			return false;
		}
		Method method = hm.getMethod();
		if (method.isAnnotationPresent(Operation.class)) {

			System.out.println("Method Annotation " + method.getAnnotation(Operation.class).operationId());

			if (method.getDeclaringClass().isAnnotationPresent(Tag.class)) {

				System.out.println("Class Annotation " + method.getDeclaringClass().getAnnotation(Tag.class).name());

			}
			// enable this after checking privileges
//			  throw new ForbiddenException("Forbidden Not Allowed to Access");
		}
		return true;
	}

	

}
