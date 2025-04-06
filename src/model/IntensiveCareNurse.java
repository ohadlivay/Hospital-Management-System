package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import control.Hospital;
import enums.Specialization;
import exceptions.NoIntensiveCareDepartmentForICStaffException;

public class IntensiveCareNurse extends Nurse implements IntensiveCareStaffMember , Serializable, ShortToStringable{

	public IntensiveCareNurse(int id, String firstName, String lastName, Date birthDate, String address,
			String phoneNumber, String email, String gender, Date workStartDate, double salary, int licenseNumber) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email,
				gender, workStartDate, salary, licenseNumber);
		Department department= Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare);
		if(department==null) {
			throw new NoIntensiveCareDepartmentForICStaffException();
		}
		department.addNurse(this);
//		addDepartment(department);
	}
	
	public IntensiveCareNurse(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, String gender, Date workStartDate,
			HashSet<Department> departments, double salary, int licenseNumber) {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender, workStartDate,
				departments, salary,licenseNumber);
		Department department= Hospital.getInstance().searchDepartmentBySpecialization(Specialization.IntensiveCare);
		department.addNurse(this);
//		addDepartment(department);		
	}
	


	@Override
	public boolean removeDepartment(Department department) {
		//The method prevents deletion of the department if it is an intensive care department
		if(department==null||department.getSpecialization().equals(Specialization.IntensiveCare)) {
			return false;
		}
		department.removeNurse(this);
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
