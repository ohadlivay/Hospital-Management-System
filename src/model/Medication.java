package model;

import java.io.Serializable;
import java.util.Objects;

import exceptions.NegativeDosageException;
import exceptions.NegativeNumberOfDosesException;

public class Medication  implements Serializable , ShortToStringable{

	private int code;//PK
	private String name;
	private double dosage;
	private int numberOfDose;
	
	//constructors
	public Medication(int code, String name, double dosage, int numberOfDose) {
		super();
		this.code = code;
		this.name = name;
		setDosage(dosage);
		setNumberOfDose(numberOfDose);
	}

	//getters
	public int getCode() {
		return code;
	}


	public String getName() {
		return name;
	}


	public double getDosage() {
		return dosage;
	}


	public int getNumberOfDose() {
		return numberOfDose;
	}

	//setters
	public void setName(String name) {
		this.name = name;
	}


	public void setDosage(double dosage) {
		//checks if the dosage is not negative. if it is not, set to 1.0.
		if(dosage<0) {
			throw new NegativeDosageException(dosage);
		}
		this.dosage = dosage;
	}


	public void setNumberOfDose(int numberOfDose) {
		//checks if the numberOfDose is over 1. if it is not, set to 1.
		if(numberOfDose<1) {
			throw new NegativeNumberOfDosesException(numberOfDose);
		}
		this.numberOfDose = numberOfDose;
	}


	//hashCode and equals according to the PK
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medication other = (Medication) obj;
		return code == other.code;
	}

	//toString
	@Override
	public String toString() {
		return "Medication [code=" + code + ", name=" + name + ", dosage=" + dosage + ", numberOfDose=" + numberOfDose
				+ "]";
	}
	@Override
	public String shortToString() {
		return "Medication: " + this.getCode() + " " + this.getName();
	}
	
	
}
