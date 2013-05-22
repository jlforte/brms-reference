package com.rhc.mortgage.domain;

public class Customer {

	private String name;
	private Integer age;
	private Integer creditScore;
	private Long id;
	private Boolean valid = true;

	public Customer( String name, Integer age, Integer creditScore, Long id ) {

		this.name = name;
		this.age = age;
		this.creditScore = creditScore;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge( Integer age ) {
		this.age = age;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore( Integer creditScore ) {
		this.creditScore = creditScore;
	}

	public long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid( Boolean valid ) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", creditScore=" + creditScore + ", id=" + id + ", valid="
				+ valid + "]";
	}

}
