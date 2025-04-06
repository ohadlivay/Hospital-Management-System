package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import control.Hospital;
import enums.BiologicalSex;
import enums.Specialization;
import exceptions.*;
import utils.MyFileLogWriter;
import utils.UtilsMethods;

public abstract class StaffMember extends Person implements Serializable, ShortToStringable{
	
	private Date workStartDate;
	protected HashSet<Department> departments;
	private double salary;

	
	//Constructors
	public StaffMember(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			HashSet<Department> departments, double salary) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender);
		setWorkStartDate(workStartDate);
		this.departments = departments;
		this.salary = salary;
	}
	
	public StaffMember(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			 double salary) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender);
		setWorkStartDate(workStartDate);
		this.departments = new HashSet<Department>();
		this.salary = salary;
	}

	//getters
	public Date getWorkStartDate() {
		return workStartDate;
	}

	public HashSet<Department> getDepartments() {
		return departments;
	}

	public double getSalary() {
		return salary;
	}
	
	public double getWorkTime() {
		//returns the WorkTime of the StaffMember, in days
		System.out.println(UtilsMethods.dateDiffInDays(Hospital.TODAY, workStartDate));
		return UtilsMethods.dateDiffInDays(Hospital.TODAY, workStartDate);
	}

	//setters
	public void setWorkStartDate(Date workStartDate) {
		//if the workStartDate is after "today", it set to "today"
		if(workStartDate.after(Hospital.TODAY)) {
			throw new FutureDateException(workStartDate);
		}
		if(this.getBirthDate().after(workStartDate)) {
			throw new BirthdateAfterWorkdateException(workStartDate,this.getBirthDate());
		}
		this.workStartDate = workStartDate;
	}

	public void setDepartments(HashSet<Department> departments) {
		this.departments = departments;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	//add
	public boolean addDepartment(Department department) {
		
		if(department==null) {
			throw new NullPointerException();
		}
		if(departments.contains(department)) {
			throw new ObjectAlreadyExistsException(department, this);
		}
			return departments.add(department);
	}
	
	//remove
	public boolean removeDepartment(Department department) {
		if(department==null) {
			throw new NullPointerException();
		}
		if(!departments.contains(department)) {
	    	System.out.println("in staff");

			throw new ObjectDoesNotExist(department.getNumber(), department.getClass().getSimpleName(), this);

		}
			return departments.remove(department);

	}

	//toString based on the super.toString()
	@Override
	public String toString() {
		return super.toString()+", workStartDate=" + workStartDate  +
				", salary=" + salary
				;
	}
	
	public Department getDepartmentBySpecialization(Specialization specialization) {
		for(Department department:departments) {
			if (department.getSpecialization().equals(specialization)) {
				return department;
			}
		}
		return null;
	}
	
	
	public void signUpUser(StaffMember user, String password) {
		if(password.trim().isBlank() || password.length() < 6 || password.length() > 30) {
			throw new InvalidPasswordException();
		}
		if(Hospital.getInstance().getPasswords().keySet().contains(user.getId())) {
			throw new UserIsAlreadySignedUp();
		}
		Hospital.getInstance().getPasswords().put(user.getId(), password);
	}
	
	public void loginUser(Integer id, String password) {
	    // Check if the login is for the special ADMIN account
	    if (id.equals("ADMIN") && password.equals("ADMIN")) {
	        return; // Login successful for ADMIN
	    }

	    // Get the hospital instance
	    Hospital hospital = Hospital.getInstance();

	    // Check if the user is registered
	    if (!hospital.getPasswords().containsKey(id)) {
	        throw new UserNotRegisteredException();
	    }

	    // Check if the password is correct
	    if (!hospital.getPasswords().get(id).equals(password)) {
	        throw new IncorrectPasswordException();
	    }

	    // Login successful, further logic (if needed) can be added here
	}

}
