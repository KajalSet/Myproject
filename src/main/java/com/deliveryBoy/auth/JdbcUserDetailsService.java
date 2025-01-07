//package com.deliveryBoy.auth;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import javax.management.relation.Role;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.deliveryBoy.entity.RoleEntity;
//import com.deliveryBoy.entity.UserEntity;
//import com.deliveryBoy.repository.UserRepo;
//
//@Service
//public class JdbcUserDetailsService implements UserDetailsService{
//	private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public JdbcUserDetailsService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//	 
//	 
//	 @Autowired
//	    private UserRepo userRepo;
//
//	    @Override
//	    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//	       
//	    	Optional<UserEntity> userEntity = userRepo.findByUserName(userName);
//
//	        if (userEntity.isEmpty()) {
//	            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
//	        }
//
//	        List<GrantedAuthority> authorities = new ArrayList<>();
//	        for (RoleEntity role : userEntity.get().getRoles()) {
//	            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//	        }
//
//	        return new org.springframework.security.core.userdetails.User(
//	                userEntity.get().getUserName(),
//	                userEntity.get().getPassword(),
//	                authorities
//	        );
//	    	
//	    }
//
//	    
//	    
//	    
//	    public CurrentUser loadUserByUsernameAndPass(String username, String password) {
//	        Optional<UserEntity> userEntity = userRepo.findByUserName(username);
//	        if (userEntity.isEmpty()) {
//	            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//	        }
//
//	        boolean match = passwordEncoder.matches(password, userEntity.get().getPassword());
//	        if (!match) {
//	            throw new UsernameNotFoundException(String.format("Incorrect password for '%s'.", username));
//	        }
//
//	        if (!userEntity.get().isActive()) {
//	            throw new UsernameNotFoundException(String.format("User is inactive"));
//	        }
//
//	        List<GrantedAuthority> authorities = new ArrayList<>();
//	        Collection<RoleEntity> roles = userEntity.get().getRoles();
//	        for (RoleEntity role : roles) {
//	            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//	        }
//
//	        CurrentUser currentUser = new CurrentUser(username, "", authorities);
//	        CopyPropertiesUtil.copyProperties(userEntity.get(), currentUser);
//
//	        return currentUser;
//	    }
//
//}
