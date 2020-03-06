package com.dialoguemd.patient.jpa;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Patient JPA Entity
 */
@Entity
@Table(name="PATIENT")
public class PatientEntity {
	
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "email", nullable = false)
	public String email;
    
    @Column(name = "first_name", nullable = false)
	public String firstName;
    
    @Column(name = "last_name", nullable = false)
	public String lastName;
    
    @Column(name = "birth_date", nullable = false)
	public Date birthDate;
    
    @Column(name = "sex", nullable = false)
	public String sex;
    
    public PatientEntity() {}
	
	public PatientEntity(Long id, String email, String firstName, String lastName, Date birthDate, String sex) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.sex = sex;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

}
