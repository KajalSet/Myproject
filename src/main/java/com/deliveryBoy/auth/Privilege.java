package com.deliveryBoy.auth;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Privilege extends BaseEntity {

	private String name;

	private String controllerIdentifier;

	private String methodIdentifier;

	private String description;

	@Column(columnDefinition = "boolean default true")
	private boolean allowAll;

	private String apiPath;

	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getControllerIdentifier() {
		return controllerIdentifier;
	}

	public void setControllerIdentifier(String controllerIdentifier) {
		this.controllerIdentifier = controllerIdentifier;
	}

	public String getMethodIdentifier() {
		return methodIdentifier;
	}

	public void setMethodIdentifier(String methodIdentifier) {
		this.methodIdentifier = methodIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAllowAll() {
		return allowAll;
	}

	public void setAllowAll(boolean allowAll) {
		this.allowAll = allowAll;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
