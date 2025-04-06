package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import control.Hospital;
import enums.Specialization;
import exceptions.NegativeRecoveryTimeException;
import utils.MyFileLogWriter;

public class Injury extends MedicalProblem implements IntensiveCareMedicalProblem,  Serializable, ShortToStringable{
	
	private double commonRecoveryTime;
	private String location;
	
	//constructors
	public Injury(String name, Department department, HashSet<Treatment> treatmentsList,
			double commonRecoveryTime, String location) {
		super("i",name, department, treatmentsList);
		this.commonRecoveryTime = this.setCommonRecoveryTime(commonRecoveryTime);;
		this.location = location;
	}
	
	public Injury(String name, Department department, double commonRecoveryTime,
			String location) {
		super("i",name, department);
		this.commonRecoveryTime = this.setCommonRecoveryTime(commonRecoveryTime);;
		this.location = location;
	}

	//getters
	public double getCommonRecoveryTime() {
		return commonRecoveryTime;
	}

	public String getLocation() {
		return location;
	}
	
	//setters
	public double setCommonRecoveryTime(double commonRecoveryTime) {
		if(commonRecoveryTime < 0) {
			throw new NegativeRecoveryTimeException(commonRecoveryTime);
		}
		return commonRecoveryTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Injury ["+ super.toString()+", commonRecoveryTime=" + commonRecoveryTime + ", location=" + location 
				 + "]";
	}
	
	@Override
	public void setIntensiveCare() {
		setDepartment(Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare));
		
	}

	@Override
	public void describeSpecialProperties() {
		MyFileLogWriter.println(getCode()+", commonRecoveryTime=" + commonRecoveryTime + ", location=" + location );
		
	}
	
	
	@Override
	public String shortToString() {
		return "Injury: " + this.getCode() + " " + this.getName();
	}
	
}
