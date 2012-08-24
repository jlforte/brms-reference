package com.rhc.brms.ref.domain;

import java.math.BigDecimal;

public class Application {

	private BigDecimal amount;
	private Long customerId;
	private Boolean reviewed = false;
	private Boolean approved;
	private Long applicationId;
	private Boolean valid = true;
	
	public Application(BigDecimal amount, Long customerId,  Long applicationId) {
		
		this.amount = amount;
		this.customerId = customerId;
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

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Application [amount=" + amount + ", customerId=" + customerId + ", reviewed=" + reviewed + ", approved=" + approved
				+ ", applicationId=" + applicationId + ", valid=" + valid + "]";
	}


	
	
}
