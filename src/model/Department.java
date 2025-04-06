package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import enums.Specialization;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectDoesNotExist;

public class Department implements Serializable , ShortToStringable {
	
	private int number;//PK
	private String name;
	private Doctor manager;
	private String location;
	private HashSet<StaffMember>staffMembersList;
	private Specialization specialization;

	
	//constructors
	public Department(int number, String name, Doctor manager, String location
			,Specialization specialization, HashSet<StaffMember>staffMembersList) {
		super();
		this.number = number;
		this.name = name;
		this.manager = manager;
		this.location = location;
		this.staffMembersList = staffMembersList;
		this.specialization= specialization;
	}
	
	public Department(int number, String name, Doctor manager, String location,Specialization specialization) {
		super();
		this.number = number;
		this.name = name;
		this.manager = manager;
		this.location = location;
		this.staffMembersList = new HashSet<>();
		this.specialization= specialization;

	}


	//getters
	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public Doctor getmanager() {
		return manager;
	}

	public String getLocation() {
		return location;
	}

	public HashSet<StaffMember> getStaffMembersList() {
		return staffMembersList;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	//setters
	public void setName(String name) {
		this.name = name;
	}

	public void setmanager(Doctor manager) {
		this.manager = manager;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStaffMembersList(HashSet<StaffMember> staffMembersList) {
		this.staffMembersList = staffMembersList;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	//equals and hashCode based on the PK
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return number == other.number;
	}
	
	
	//add
	public boolean addStaffMember(StaffMember staffMember) {
		if(staffMember==null) {
			throw new NullPointerException();
		}
		System.out.println("Adding" + staffMember.getFirstName() + " to " + this.getName());

		if(staffMembersList.contains(staffMember)) {
			System.out.println("it alrdy contains");
			throw new ObjectAlreadyExistsException(staffMember,this);
		}
		if(staffMember.addDepartment(this)) {
			return staffMembersList.add(staffMember);
		}
		return false;
	}
	
	public boolean addDoctor(Doctor doctor) {
		return addStaffMember(doctor);
	}
	
	public boolean addNurse(Nurse nurse) {
		return addStaffMember(nurse);
	}
	
	//remove
	public boolean removeStaffMember(StaffMember staffMember) {
		if(staffMember==null) {
			throw new NullPointerException();
		}
		if (!staffMembersList.contains(staffMember)) {
			throw new ObjectDoesNotExist(staffMember.getId(), staffMember.getClass().getSimpleName(), this);
		}
		return staffMembersList.remove(staffMember);
	}
	public boolean removeDoctor(Doctor doctor) {
		return removeStaffMember(doctor);
	}
	public boolean removeNurse(Nurse nurse) {
		return removeStaffMember(nurse);
	}

	//toString
	@Override
	public String toString() {
		return "Department [number=" + number + ", name=" + name + ", manager=" + manager + ", location=" + location
				+ ", specialization=" + specialization + "]";
	}
	
	public String shortToString() {
		return "Department #" + number + " " + name ;
	}

	

}
