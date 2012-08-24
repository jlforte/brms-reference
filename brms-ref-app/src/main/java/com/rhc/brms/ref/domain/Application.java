package com.rhc.brms.ref.domain;

import java.math.BigDecimal;

public class Application {

	private BigDecimal amount;
	private Long customerId;
	private Boolean reviewed;
	private Boolean approved;
	private Long applicationId;
	
	public Application(BigDecimal amount, Long customerId, Boolean reviewed, Boolean approved, Long applicationId) {
		
		this.amount = amount;
		this.customerId = customerId;
		this.reviewed = reviewed;
		this.approved = approved;
		this.applicationId = applicationId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Boolean getReviewed() {
		return reviewed;
	}
	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	
	
	
}
