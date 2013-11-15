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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((creditScore == null) ? 0 : creditScore.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((valid == null) ? 0 : valid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (creditScore == null) {
			if (other.creditScore != null)
				return false;
		} else if (!creditScore.equals(other.creditScore))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (valid == null) {
			if (other.valid != null)
				return false;
		} else if (!valid.equals(other.valid))
			return false;
		return true;
	}

}
