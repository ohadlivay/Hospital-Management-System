package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import enums.BiologicalSex;
import enums.HealthFund;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectDoesNotExist;

public class Patient extends Person implements Serializable, ShortToStringable{

	//Fields
	private HashSet<Visit> visitsList;
	private HealthFund healthFund;
	private BiologicalSex biologicalSex;

	
	//Constructors
	public Patient(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, HashSet<Visit> visitsList, HealthFund healthFund,
			BiologicalSex biologicalSex) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender);
		this.visitsList = visitsList;
		this.healthFund = healthFund;
		this.biologicalSex = biologicalSex;
	}
	public Patient(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, HealthFund healthFund,
			BiologicalSex biologicalSex) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender);
		this.visitsList = new HashSet<Visit>();
		this.healthFund = healthFund;
		this.biologicalSex = biologicalSex;
	}
	

	//getters
	public HashSet<Visit> getVisitsList() {
		return visitsList;
	}

	public HealthFund getHealthFund() {
		return healthFund;
	}
	public BiologicalSex getBiologicalSex() {
		return biologicalSex;
	}
	
	//setters
	public void setVisitsList(HashSet<Visit> visitsList) {
		this.visitsList = visitsList;
	}
	public void setHealthFund(HealthFund healthFund) {
		this.healthFund = healthFund;
	}
	public void setBiologicalSex(BiologicalSex biologicalSex) {
		this.biologicalSex = biologicalSex;
	}
	
	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Patient ["+super.toString() + ", healthFund=" + healthFund + ", biologicalSex="+biologicalSex+"]";
	}
	
	//add
	public boolean addVisit(Visit visit) {
		if(visit==null) {
			throw new NullPointerException();
		}
		if (visitsList.contains(visit)) {
			throw new ObjectAlreadyExistsException(visit, this);
		}
		return visitsList.add(visit);
	}
	
	//remove	
	public boolean removeVisit(Visit visit) {
		if(visit==null) {
			throw new NullPointerException();
		}
		if (!visitsList.contains(visit)) {
			throw new ObjectDoesNotExist(visit.getNumber(), visit.getClass().getSimpleName(), this);
		}
		return visitsList.remove(visit);
	}
	
	@Override
	public String shortToString() {
		return "Patient (ID:" + this.getId() + ") " + this.getFirstName() + " " + this.getLastName();   
	}

}
