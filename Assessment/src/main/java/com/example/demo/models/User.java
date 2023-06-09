package com.example.demo.models;




import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;



public class User {
	
	private @Id @GeneratedValue Integer id;
	private String firstName;
	private String lastName;
	private String phone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
