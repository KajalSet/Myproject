

//
//import java.io.IOException;
//import java.security.SignatureException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.deliveryBoy.service.LoginService;
//
//import io.jsonwebtoken.ExpiredJwtException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
////	private final JdbcUserDetailsService jwtUserDetailsService;
//
////    @Autowired
////    public JwtRequestFilter(@Lazy JdbcUserDetailsService jwtUserDetailsService) {
////        this.jwtUserDetailsService = jwtUserDetailsService;
////    }
//
//
//	@Autowired
//	private JwtUtil jwtTokenUtil;
//	
//	@Autowired
//    @Lazy
//    private LoginService loginService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		final String requestTokenHeader = request.getHeader("Authorization");
//		String username = null;
//		String jwtToken = null;
//		
//		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username = jwtTokenUtil.extractUsername(jwtToken);
//			} catch (IllegalArgumentException e) {
//				System.out.println("Unable to get JWT Token");
//			} catch (ExpiredJwtException e) {
//				System.out.println("JWT Token has expired");
//			} catch (SignatureException e) {
//				e.printStackTrace();
//			}
//		} else {
//			logger.warn("JWT Token does not begin with Bearer String");
//		}
//		// Once we get the token validate it.
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//			// if token is valid configure Spring Security to manually set
//			// authentication
//			try {
//				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//							userDetails, null, userDetails.getAuthorities());
//					usernamePasswordAuthenticationToken
//							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					// After setting the Authentication in the context, we specify
//					// that the current user is authenticated. So it passes the
//					// Spring Security Configurations successfully.
//					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//					
//					request.setAttribute("UserId", userDetails.getUsername());
//				}
//			} catch (SignatureException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		String mpin = request.getHeader("Mpin");
//        String userId = request.getHeader("UserId");
//
//        if (request.getRequestURI().contains("/api/sensitive/")) { // MPIN required for sensitive endpoints
//            if (mpin == null || userId == null) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("MPIN is required");
//                return;
//            }
//
//            boolean isValid = loginService.validateMpin(Long.parseLong(userId), Integer.parseInt(mpin));
//            if (!isValid) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid MPIN");
//                return;
//            }
//        }
//
//		
//		
//		
//		
//		
//		filterChain.doFilter(request, response);
//	}
//
//}

package com.deliveryBoy.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  // Import missing here

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deliveryBoy.service.LoginService;
import javax.servlet.FilterChain;  // Import missing here

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token
                if (jwtUtil.validateToken(token)) {
                    // Token is valid, set the authentication context
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Token is invalid, clear the context and reject the request
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return;  // Stop further processing if the token is invalid
                }
        }
            }

        filterChain.doFilter(request, response);
    }
}