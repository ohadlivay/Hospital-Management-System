package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import enums.BiologicalSex;
import utils.MyFileLogWriter;

public class Nurse extends StaffMember implements Serializable, ShortToStringable{
	
	private int licenseNumber;
	
	
	//constructors
	public Nurse(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			HashSet<Department> departments, double salary, int licenseNumber) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,
				departments, salary);
		this.licenseNumber = licenseNumber;
	}
	
	public Nurse(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			 double salary, int licenseNumber) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,
				 salary);
		this.licenseNumber = licenseNumber;
	}

	//getters
	public int getLicenseNumber() {
		return licenseNumber;
	}


	//setters
	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}


	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Nurse ["+ super.toString()+", licenseNumber=" + licenseNumber 
				+  "]";
	}
	
	@Override
	public String shortToString() {
		return "Nurse: (ID" + this.getId() + ") " + this.getFirstName() + " " + this.getLastName();   
	}

}
