package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import control.Hospital;
import enums.BiologicalSex;
import enums.Specialization;
import utils.MyFileLogWriter;

public class Doctor extends StaffMember implements Serializable, ShortToStringable{
	
	private int licenseNumber;
	private boolean isFinishInternship;
	private Specialization specialization;

	
	//constructors
	public Doctor(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,String email,
			String gender, Date workStartDate,HashSet<Department> departments,
			double salary,int licenseNumber, boolean isFinishInternship,
			Specialization specialization) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,
				departments, salary);
		this.licenseNumber = licenseNumber;
		this.isFinishInternship = isFinishInternship;
		this.specialization = specialization;
	}
	
	public Doctor(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			 double salary, int licenseNumber, boolean isFinishInternship,
			Specialization specialization) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,
				 salary);
		this.licenseNumber = licenseNumber;
		this.isFinishInternship = isFinishInternship;
		this.specialization = specialization;
	}

	//getters
	public int getLicenseNumber() {
		return licenseNumber;
	}


	public boolean isFinishInternship() {
		return isFinishInternship;
	}


	public Specialization getSpecialization() {
		return specialization;
	}


	//setters
	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}


	public void setFinishInternship(boolean isFinishInternship) {
		this.isFinishInternship = isFinishInternship;
	}


	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Doctor ["+super.toString() +", licenseNumber=" + licenseNumber + ", isFinishInternship=" + isFinishInternship
				+ ", specialization=" + specialization  +  "]";
	}

	@Override
	public String shortToString() {
		return "Doctor: (ID" + this.getId() + ") " + this.getFirstName() + " " + this.getLastName();   
	}

}
