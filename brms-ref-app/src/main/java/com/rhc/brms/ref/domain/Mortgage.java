package com.rhc.brms.ref.domain;

import java.math.BigDecimal;

public class Mortgage {
	
	private Long customerId;
	private Long applicationId;
	private BigDecimal approvedAmount;
	
	public Mortgage(Long customerId, Long applicationId, BigDecimal approvedAmount) {
		
		this.customerId = customerId;
		this.applicationId = applicationId;
		this.approvedAmount = approvedAmount;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	
	
}
