package com.rhc.insurance;

public class Member
{
	private String name;
	private Boolean smokesRegularly;
	private Boolean skydivesRegularly;
	private Boolean filesMedicationRegularly;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSmokesRegularly() {
		return smokesRegularly;
	}
	public void setSmokesRegularly(Boolean smokesRegularly) {
		this.smokesRegularly = smokesRegularly;
	}
	public Boolean getSkydivesRegularly() {
		return skydivesRegularly;
	}
	public void setSkydivesRegularly(Boolean skydivesRegularly) {
		this.skydivesRegularly = skydivesRegularly;
	}
	public Boolean getFilesMedicationRegularly() {
		return filesMedicationRegularly;
	}
	public void setFilesMedicationRegularly(Boolean filesMedicationRegularly) {
		this.filesMedicationRegularly = filesMedicationRegularly;
	}


}