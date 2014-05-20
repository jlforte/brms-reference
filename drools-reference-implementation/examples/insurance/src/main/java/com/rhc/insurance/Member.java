package com.rhc.insurance;

public class Member
{
	public String name;
	public Boolean smokesRegularly;
	public Boolean skydivesRegularly;
	public Boolean filesMedicationRegularly;
	
	public Member()
	{
		name = "";
		smokesRegularly = false;
		skydivesRegularly = false;
	}
	
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