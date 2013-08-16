package com.rhc.insurance;

public class Policy {
	
	public Member member;
	public int price;
	public int physicalHealth;
	public int behavioralHealth;
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPhysicalHealth() {
		return physicalHealth;
	}
	public void setPhysicalHealth(int physicalHealth) {
		this.physicalHealth = physicalHealth;
	}
	public int getBehavioralHealth() {
		return behavioralHealth;
	}
	public void setBehavioralHealth(int behavioralHealth) {
		this.behavioralHealth = behavioralHealth;
	}
	
	

}
