package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import control.Hospital;
import enums.Specialization;
import utils.MyFileLogWriter;

public class Fracture extends MedicalProblem implements IntensiveCareMedicalProblem , Serializable, ShortToStringable{

	private String location;
	private boolean requiresCast;
	
	
	//constructors
	public Fracture(String name, Department department, HashSet<Treatment> treatmentsList,
			String location, boolean requiresCast) {
		super("f",name, department, treatmentsList);
		this.location = location;
		this.requiresCast = requiresCast;
	}
	
	public Fracture(String name, Department department,
			String location, boolean requiresCast) {
		super("f",name, department);
		this.location = location;
		this.requiresCast = requiresCast;
	}
	

	//getters
	public String getLocation() {
		return location;
	}

	public boolean isRequiresCast() {
		return requiresCast;
	}

	//setters
	public void setLocation(String location) {
		this.location = location;
	}

	public void setRequiresCast(boolean requiresCast) {
		this.requiresCast = requiresCast;
	}
	
	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Fracture ["+  super.toString()+", location=" + location + ", requiresCast=" + requiresCast 
				+ "]";
	}

	@Override
	public void setIntensiveCare() {
		setDepartment(Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare));
		
	}
	
	@Override
	public void describeSpecialProperties() {
		MyFileLogWriter.println("code="+getCode()+", location=" + location + ", requiresCast=" + requiresCast );
		
	}

	@Override
	public String shortToString() {
		return "Fracture: " + this.getCode() + " " + this.getName();
	}
}
