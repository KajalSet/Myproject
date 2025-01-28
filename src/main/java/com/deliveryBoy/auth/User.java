package com.deliveryBoy.auth;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_user")
public class User extends BaseEntity {

	@Column(unique = true)
	private String mobileNumber;

	@Column(unique = true)
	private String userName;

	@Column(unique = true)
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	
	private String currentLocation;

	@Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean deleteAllowed = true;

	private String mpin;
	
	private String fcmToken;

	@Column(columnDefinition = "boolean default false")
	private boolean emailVerified;

	@Column(columnDefinition = "boolean default false")
	private boolean mobileVerified;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String resetToken;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String activationToken;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Date resetTokenExpiryDate;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Date emailTokenExpiryDate;
	
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeleteAllowed() {
		return deleteAllowed;
	}

	public void setDeleteAllowed(boolean deleteAllowed) {
		this.deleteAllowed = deleteAllowed;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

//	public Branch getBranch() {
//		return branch;
//	}
//
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}

//	public Company getCompany() {
//		return company;
//	}
//
//	public void setCompany(Company company) {
//		this.company = company;
//	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public Date getResetTokenExpiryDate() {
		return resetTokenExpiryDate;
	}

	public void setResetTokenExpiryDate(Date resetTokenExpiryDate) {
		this.resetTokenExpiryDate = resetTokenExpiryDate;
	}

	public Date getEmailTokenExpiryDate() {
		return emailTokenExpiryDate;
	}

	public void setEmailTokenExpiryDate(Date emailTokenExpiryDate) {
		this.emailTokenExpiryDate = emailTokenExpiryDate;
	}
	
	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	
}
