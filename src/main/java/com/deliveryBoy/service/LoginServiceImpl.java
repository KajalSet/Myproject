package com.deliveryBoy.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliveryBoy.auth.CurrentUser;
import com.deliveryBoy.auth.JdbcUserDetailsService;
import com.deliveryBoy.auth.JwtUtil;
import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.entity.RegisterEntity;
import com.deliveryBoy.entity.UserEntity;
import com.deliveryBoy.repository.LoginRepository;
import com.deliveryBoy.repository.RegisterRepository;
import com.deliveryBoy.request.LoginRequest;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	 @Lazy 
    private JdbcUserDetailsService jdbcUserDetailsService;

	@Autowired
	 LoginRepository loginRepository;
	@Autowired
	RegisterRepository registerRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Override
	public String register(LoginRequest loginRequest) {
		
		if(registerRepository.findByUserName(loginRequest.getUserName()).isPresent()) {
			return "Username already exists";
			}
		
		RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setUserName(loginRequest.getUserName());
        registerEntity.setPassword(passwordEncoder.encode(loginRequest.getPassword()));

		
		RegisterEntity entity = registerRepository.save(registerEntity);
		
		LoginEntity loginEntity = new LoginEntity();
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(entity.getUserName());
		userEntity.setPassword(entity.getPassword()); 
		
		loginEntity.setUser(userEntity); 
		
	      
	    loginEntity.setMpin(null); 
	    loginEntity.setMpinCreated(false);  

	    
	    loginRepository.save(loginEntity);

		
		
		return "User registered sucessfully";
	}
	
	
	@Override
	public ResponseEntity login(String username, String password) {
		try {
	        // Fetch user details from database
	        Optional<LoginEntity> optionalLoginEntity = loginRepository.findByUserName(username);

	        if (optionalLoginEntity.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }

	        LoginEntity loginEntity = optionalLoginEntity.get();

	        // Validate password
	        if (!passwordEncoder.matches(password, loginEntity.getUser().getPassword())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	        }

	        // Check MPIN status
	        boolean isMpinCreated = loginEntity.isMpinCreated();

	        // Load user details and construct CurrentUser
	        UserDetails userDetails = jdbcUserDetailsService.loadUserByUsername(username);
	        CurrentUser currentUser = new CurrentUser(
	            userDetails.getUsername(),
	            userDetails.getPassword(),
	            userDetails.getAuthorities()
	        );

	        // Generate JWT token
	        String token = jwtUtil.generateToken(currentUser);

	        // Prepare response
	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("isMpinRequired", !isMpinCreated);

	        if (!isMpinCreated) {
	            response.put("message", "Login successful. Please create an MPIN.");
	        } else {
	            response.put("message", "Login successful.");
	        }

	        return ResponseEntity.ok(response);

	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Login failed: " + ex.getMessage());
	    }
	}

	
	
	
//	@Override
//	public ResponseEntity<String> login(String username, String password) {
//	    
//	    
//	    try {
//	    	Optional<LoginEntity> dblog = loginRepository.findByUserName(username);
//	    	if (dblog.isEmpty()) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	            		.body("User not found");
//	        }
//		    
//		    
//		    LoginEntity loginEntity = dblog.get();
//		    
//		    if (!passwordEncoder.matches(password,loginEntity.getUser().getPassword())) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	            		.body("Invalid password");
//	        }
//		    
//		    if (!loginEntity.isMpinCreated()) {
//	            return ResponseEntity.status(HttpStatus.OK).body("Login successful. Please create an MPIN.");
//	        }
//		    return ResponseEntity.ok("Login successful with MPIN already created.");
//		    
//	    	
//           
//        } catch (UsernameNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + ex.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + ex.getMessage());
//        }  
//	    
//	    
//	    
//	    
//	}

	
	

	@Override
	public boolean isMpinCreated(String userName) {
	    return loginRepository.findByUserName(userName)
	                          .map(LoginEntity::isMpinCreated)
	                          .orElse(false);
	}//ismpincreated





	@Override
	public LoginEntity resetMpin(Long id, Integer newMpin) {
		LoginEntity loginEntity=loginRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("User Not Found!"));
		
		if (newMpin < 1000 || newMpin > 9999) {
	        throw new IllegalArgumentException("MPIN must be a 4-digit number!");
	    }
		loginEntity.setMpin(newMpin);
		return loginRepository.save(loginEntity);
	}




	@Override
	public LoginEntity createMpin(Long id, Integer mpin) {
		 LoginEntity loginEntity = loginRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("User not found"));

	        if (mpin < 1000 || mpin > 9999) {
	            throw new IllegalArgumentException("MPIN must be a 4-digit number!");
	        }

	        String encryptedMpin = passwordEncoder.encode(mpin.toString());
	        loginEntity.setEncryptedMpin(encryptedMpin);
	        loginEntity.setMpinCreated(true);  // Mark MPIN as created
	        return loginRepository.save(loginEntity);
		
	}




	@Override
	public LoginEntity save(LoginEntity loginEntity) {
		
		 return loginRepository.save(loginEntity);
	}


	@Override
	public boolean validateMpin(Long id, Integer mpin) {
		LoginEntity loginEntity = loginRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("User not found"));

		return passwordEncoder.matches(mpin.toString(), loginEntity.getEncryptedMpin());
	
	}

	
	
	
  
		
		
		
	}





	
	

	
	

