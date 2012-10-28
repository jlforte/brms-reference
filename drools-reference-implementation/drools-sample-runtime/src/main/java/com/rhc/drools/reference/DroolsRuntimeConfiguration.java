package com.rhc.drools.reference;

public class DroolsRuntimeConfiguration {

	private boolean isRecordingFiredRuleActivations = true;
	private boolean isRecordingAuditLog = true;
	private String fullyQualifiedLogFileName = "DroolsAuditLog";

	public DroolsRuntimeConfiguration( String fullyQualifiedLogFileName ) {
		this.fullyQualifiedLogFileName = fullyQualifiedLogFileName;
	}

	public DroolsRuntimeConfiguration() {
	}

	public boolean isRecordingFiredRuleActivations() {
		return isRecordingFiredRuleActivations;
	}

	public void setRecordingFiredRuleActivations( boolean isRecordingFiredRuleActivations ) {
		this.isRecordingFiredRuleActivations = isRecordingFiredRuleActivations;
	}

	public boolean isRecordingAuditLog() {
		return isRecordingAuditLog;
	}

	public void setRecordingAuditLog( boolean isRecordingAuditLog ) {
		this.isRecordingAuditLog = isRecordingAuditLog;
	}

	public String getFullyQualifiedLogFileName() {
		return fullyQualifiedLogFileName;
	}

	public void setFullyQualifiedLogFileName( String fullyQualifiedLogFileName ) {
		this.fullyQualifiedLogFileName = fullyQualifiedLogFileName;
	}

}
