
package com.example.demo;

import java.io.File;
import java.sql.Blob;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
//import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@Table(name="doctor")
@EnableAutoConfiguration
public class Person {
	private static final Long serialVersionUID = -723583058586873479L;
	
//	@Column(unique=true)
	private String email;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String firstName;
	private String lastName;
//	@Column(columnDefinition="default 1")
	private int workingYears;
	private String degree;
//	@Column(columnDefinition="default 1")
	private int degreeCount;
	private String currentWorkingPlace;
	private int lastgraduateYear;
	
    private String image;
	
	protected Person() {}
	
//	public Person(String firstName, String lastName, String email,int workingYears, String des) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.workingYears = workingYears;
//		this.desgination = des;
//		this.email = email;
//	}
	
	public Person(Map<String, String> model) {
		
		this.firstName = model.get("firstName");
		this.lastName = model.get("lastName");
		this.workingYears = Integer.parseInt(model.get("workingYears"));
		this.lastgraduateYear = Integer.parseInt(model.get("lastgraduateYear"));

		this.email = model.get("email");
		this.currentWorkingPlace = model.get("currentWorkingPlace");
		this.degree = model.get("degree");
		this.degreeCount = Integer.parseInt(model.get("degreeCount"));
//		this.image =  this.get.toString();
		
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
    public void setDegreeCount(int co) {
		this.degreeCount = co;
	}
	
    public void setDegree(String degree) {
		this.degree = degree;
	}
	
    public void setCurrentWorkingPlace(String cwp) {
		this.currentWorkingPlace = cwp;
	}
	
    public void setEmail(String em) {
		this.email = em;
	}
	

	
    public void setLastgraduteYear(int y) {
		this.lastgraduateYear = y;
	}
	public void setWorkingYears(int y) {
		this.workingYears = y;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String ln) {
		this.lastName = ln;
	}
	
    
	/**
	 * @return the image
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	public String getFirstName() {
		return this.firstName;
	}
	
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getWorkingYears() {
		return this.workingYears;
	}
	
	
	public String getEmail(){
		return this.email;
	}
	
}