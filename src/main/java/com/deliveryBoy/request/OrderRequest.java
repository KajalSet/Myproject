package com.deliveryBoy.request;

public class OrderRequest {
	private String orderId;
	private String customerName;
	private String deliveryAddress;
	private String contactNumber;
	private String orderstatus; // Enum as String
	private String availabilityStatus; // Enum as String
	private String deliveryBoyId;

	// Getters and Setters
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public String getDeliveryBoyId() { // Add getter
		return deliveryBoyId;
	}

	public void setDeliveryBoyId(String deliveryBoyId) { // Add setter
		this.deliveryBoyId = deliveryBoyId;
	}

	// Builder class for fluent creation
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String orderId;
		private String customerName;
		private String deliveryAddress;
		private String contactNumber;

		private String orderstatus;
		private String availabilityStatus;
		private String deliveryBoyId;

		public Builder orderId(String orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder customerName(String customerName) {
			this.customerName = customerName;
			return this;
		}

		public Builder deliveryAddress(String deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
			return this;
		}

		public Builder contactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
			return this;
		}

		public Builder orderstatus(String orderstatus) {
			this.orderstatus = orderstatus;
			return this;
		}

		public Builder availabilityStatus(String availabilityStatus) {
			this.availabilityStatus = availabilityStatus;
			return this;
		}

		public Builder deliveryBoyId(String deliveryBoyId) { // Add this method
			this.deliveryBoyId = deliveryBoyId;
			return this;
		}

		public OrderRequest build() {
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.orderId = this.orderId;
			orderRequest.customerName = this.customerName;
			orderRequest.deliveryAddress = this.deliveryAddress;
			orderRequest.contactNumber = this.contactNumber;

			orderRequest.orderstatus = this.orderstatus;
			orderRequest.availabilityStatus = this.availabilityStatus;
			orderRequest.deliveryBoyId = this.deliveryBoyId; // Set this field

			return orderRequest;
		}
	}
}
