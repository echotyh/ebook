package com.xiaotangbao.ebook.entity;

import java.util.Date;

public class User {
	private String name;
	private String password;
	private String status;//顾客身份
	private String gender;
	private String birth;
	private String phone;
	private String email;
	private int expenditure;
	public User(){}
	
	public User(String name, String password, String status, String gender,
			String birth, String phone, String email, int expenditure) {
		super();
		this.name = name;
		this.password = password;
		this.status = status;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
		this.email = email;
		this.expenditure = expenditure;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getExpenditure() {
		return expenditure;
	}
	public void setExpenditure(int expenditure) {
		this.expenditure = expenditure;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", status="
				+ status + ", gender=" + gender + ", birth=" + birth
				+ ", phone=" + phone + ", email=" + email + ", expenditure="
				+ expenditure + "]";
	}
	
	

}
