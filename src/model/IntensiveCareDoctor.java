package model;

import java.io.Serializable;
import java.net.NoRouteToHostException;
import java.util.Date;
import java.util.HashSet;

import control.Hospital;
import enums.Specialization;
import exceptions.NoIntensiveCareDepartmentForICStaffException;

public class IntensiveCareDoctor extends Doctor implements IntensiveCareStaffMember , Serializable, ShortToStringable{

	public IntensiveCareDoctor(int id, String firstName, String lastName, Date birthDate, String address,
			String phoneNumber, String email, String gender, Date workStartDate, double salary, int licenseNumber,
			boolean isFinishInternship) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate, salary, licenseNumber,
				isFinishInternship, Specialization.IntensiveCare);
		Department department= Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare);
		if(department==null) {
			throw new NoIntensiveCareDepartmentForICStaffException();
		}
		department.addDoctor(this);
//		addDepartment(department); silly willy

	}
	
	public IntensiveCareDoctor(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,String email,
			String gender, Date workStartDate,HashSet<Department> departments,
			double salary,int licenseNumber, boolean isFinishInternship) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,departments, salary, licenseNumber,
				isFinishInternship, Specialization.IntensiveCare);
		Department department= Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare);
		department.addDoctor(this);
//		addDepartment(department); redundant, throws unwanted exception
	}
	
	@Override
	public void setSpecialization(Specialization specialization) {
		//Automatically assigns the specialty to intensive care
		super.setSpecialization(Specialization.IntensiveCare);
	}
	
	@Override
	public boolean removeDepartment(Department department) {
		//The method prevents deletion of the department if it is an intensive care department
		if(department==null||department.getSpecialization().equals(Specialization.IntensiveCare)) {
			return false;
		}
		department.removeDoctor(this);
		return super.removeDepartment(department);
	}

	@Override
	public String toString() {
		return "IntensiveCare" + super.toString();
	}

	@Override
	public Department getIntensiveCareDepartment() {
		// TODO Auto-generated method stub
		return super.getDepartmentBySpecialization(Specialization.IntensiveCare);
	}
	
	

}
