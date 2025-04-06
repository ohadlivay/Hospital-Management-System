package model;

public interface IntensiveCareStaffMember {
	//Associates a staff member as an ICU staff member
	public boolean removeDepartment(Department department);
	
	public Department getIntensiveCareDepartment();

}
