package com.rhc.brms.ref.domain;

import java.math.BigDecimal;

public class Mortgage {

	private Long customerId;
	private Long applicationId;
	private BigDecimal approvedAmount;

	public Mortgage( Long customerId, Long applicationId, BigDecimal approvedAmount ) {
		this.customerId = customerId;
		this.applicationId = applicationId;
		this.approvedAmount = approvedAmount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId( Long customerId ) {
		this.customerId = customerId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId( Long applicationId ) {
		this.applicationId = applicationId;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount( BigDecimal approvedAmount ) {
		this.approvedAmount = approvedAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( applicationId == null ) ? 0 : applicationId.hashCode() );
		result = prime * result
				+ ( ( approvedAmount == null ) ? 0 : approvedAmount.hashCode() );
		result = prime * result
				+ ( ( customerId == null ) ? 0 : customerId.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Mortgage other = (Mortgage) obj;
		if ( applicationId == null ) {
			if ( other.applicationId != null )
				return false;
		} else if ( !applicationId.equals( other.applicationId ) )
			return false;
		if ( approvedAmount == null ) {
			if ( other.approvedAmount != null )
				return false;
		} else if ( !approvedAmount.equals( other.approvedAmount ) )
			return false;
		if ( customerId == null ) {
			if ( other.customerId != null )
				return false;
		} else if ( !customerId.equals( other.customerId ) )
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mortgage [customerId=" + customerId + ", applicationId=" + applicationId + ", approvedAmount="
				+ approvedAmount + "]";
	}

}
