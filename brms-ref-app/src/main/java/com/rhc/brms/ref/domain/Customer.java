package com.rhc.brms.ref.domain;

public class Customer {

	private String name;
	private Integer age;
	private Integer creditScore;
	private Long id;
	
	public Customer(String name, Integer age, Integer creditScore, Long id) {
		
		this.name = name;
		this.age = age;
		this.creditScore = creditScore;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", creditScore=" + creditScore + ", id=" + id + "]";
	}
	
	
	
	
}
