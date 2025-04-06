package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import utils.MyFileLogWriter;

public class Disease extends MedicalProblem implements Serializable , ShortToStringable{
	
	private String description;

	//constructors
	public Disease(String name, Department department, HashSet<Treatment> treatmentsList,
			String description) {
		super("d",name, department, treatmentsList);
		this.description = description;
	}
	
	public Disease(String name, Department department, 
			String description) {
		super("d",name, department);
		this.description = description;
	}

	//getter
	public String getDescription() {
		return description;
	}
	
	//setters
	public void setDescription(String description) {
		this.description = description;
	}
	
	//toString based on the super.toString()
	@Override
	public String toString() {
		return "Disease ["+super.toString()+", description=" + description  +  "]";
	}

	@Override
	public void describeSpecialProperties() {
		MyFileLogWriter.println(getCode()+", description=" + description);
		
	}

	@Override
	public String shortToString() {
		return "Disease " + getCode() + " " + this.getName();
	}
	
	

}
