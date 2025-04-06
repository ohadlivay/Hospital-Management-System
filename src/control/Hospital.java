//ID:208464289 OHAD LIVAY

//V.19
//TURNING DOC INTO IC DOC 
package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import GUI.HospitalSystem;
import enums.*;
import exceptions.FutureDateException;
import exceptions.IDAlreadyAssociatedException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectDoesNotExist;
import model.*;
import utils.MyFileLogWriter;
import utils.UtilsMethods;
import view.Main;

public class Hospital implements Serializable{
	
	private static Hospital instance ;
	private HashMap<Integer,Department>departments;	
	private HashMap<String,MedicalProblem>medicalProblems;
	private HashMap<Integer,StaffMember>staffMembers;
	private HashMap<Integer,Medication>medications;
	private HashMap<Integer,Patient>patients;
	private HashMap<Integer,Treatment>treatments;
	private HashMap<Integer,Visit>visits;
	private HashMap<Integer,String>passwords; // ID->PASSWORD


	
	public final static Date TODAY=UtilsMethods.parseDate("30/04/2024");	
	public int PKCounter;
	//private constructor
	private Hospital() {
		super();
		this.departments = new HashMap<>();
		this.medicalProblems = new HashMap<>();
		this.staffMembers = new HashMap<>();
		this.medications = new HashMap<>();
		this.patients = new HashMap<>();
		this.treatments = new HashMap<>();
		this.visits = new HashMap<>();
		this.passwords = new HashMap<>();
		PKCounter = 1;
	}
	//getInstance
	public static Hospital getInstance() {
		if (instance==null) {
			instance = new Hospital();
		}
		return instance;
	}
	//used by serialization
	public static Hospital getInstance(Hospital hospital) {
		if (instance==null) {
			instance = hospital;
		}
		return instance;
	}
	
	
	//add
	public boolean addDoctorToDepartment(Department department,Doctor doctor) {
		if(department==null||doctor==null) {
			throw new NullPointerException();
		}

		if(!departments.containsKey(department.getNumber())){
			throw new ObjectDoesNotExist(department.getNumber(), department.getClass().getSimpleName(), this.getClass().getSimpleName());
		}

		if(!staffMembers.containsKey(doctor.getId())) {
			throw new ObjectDoesNotExist(doctor.getId(), doctor.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		return department.addDoctor(doctor);
//		return (department.addDoctor(doctor) && doctor.addDepartment(department));

	}
	//mine
	public boolean addTreatmentToMedicalProblem(Treatment treatment, MedicalProblem medicalProblem) {
		if(treatment==null||medicalProblem==null) {
			throw new NullPointerException();
		}
		if(!medicalProblems.containsKey(medicalProblem.getCode())){
			throw new ObjectDoesNotExist(medicalProblem.getCode(), medicalProblem.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		if(!treatments.containsKey(treatment.getSerialNumber())) {

			throw new ObjectDoesNotExist(treatment.getSerialNumber(), treatment.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
			return (medicalProblem.addTreatment(treatment) && (treatment.addMedicalProblem(medicalProblem)));

	}
	
	public boolean addNurseToDepartment(Department department,Nurse nurse) {
		if(department==null||nurse==null) {
			throw new NullPointerException();
		}
		if(!departments.containsKey(department.getNumber())){
			throw new ObjectDoesNotExist(department.getNumber(), department.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		if(!staffMembers.containsKey(nurse.getId())) {
			throw new ObjectDoesNotExist(nurse.getId(), nurse.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
				return department.addNurse(nurse);
	}
		
	
	public boolean addDepartment(Department department) {
		if(department==null) {
			throw new NullPointerException();
		}
		if (departments.containsKey(department.getNumber())) {
			throw new ObjectAlreadyExistsException(department,this.getClass().getSimpleName());
		}
		return departments.put(department.getNumber(),department)==null;
	}
	
	public boolean addDisease(Disease disease) {
		return addMedicalProblem(disease);
	}
	
	public boolean addMedicalProblem(MedicalProblem medicalProblem) {
		if(medicalProblem==null) {
			throw new NullPointerException();
		}
		if (medicalProblems.containsKey(medicalProblem.getCode())) {
		    throw new ObjectAlreadyExistsException(medicalProblem, this);
		}
		boolean flag=true;
		for(Treatment treatment:medicalProblem.getTreatmentsList()) {
			if(!treatment.addMedicalProblem(medicalProblem)) {
				flag=false;
			}
			
		}
		if (flag==true) {
			flag=medicalProblems.put(medicalProblem.getCode(),medicalProblem)==null;
		}
		if(flag==false) {
			for(Treatment treatment:medicalProblem.getTreatmentsList()) {
				treatment.removeMedicalProblem(medicalProblem);
			}
		}
		return flag;
	}
	
	public boolean addFracture(Fracture fracture) {
		return addMedicalProblem(fracture);
	}
	
	public boolean addInjury(Injury injury) {
		return addMedicalProblem(injury);
	}
	
	public boolean addMedication(Medication medication) {
		if(medication==null) {
			return false;
		}
		if (medications.containsKey(medication.getCode())) {
			throw new ObjectAlreadyExistsException(medication,this.getClass().getSimpleName());
		}
		return medications.put(medication.getCode(),medication)==null;
	}
	
	public boolean addStaffMember(StaffMember staffMember) {
		if(staffMember==null) {
			throw new NullPointerException();
		}
		if(staffMembers.containsKey(staffMember.getId())) {
			throw new IDAlreadyAssociatedException();
		}
		if (staffMembers.containsKey(staffMember.getId())) {
			throw new ObjectAlreadyExistsException(staffMember,this.getClass().getSimpleName());
		}
		return staffMembers.put(staffMember.getId(),staffMember)==null;
	}
	
	public boolean addDoctor(Doctor doctor) {
		return addStaffMember(doctor);
	}
	
	public boolean addIntensiveCareDoctor(IntensiveCareDoctor doctor) {
		return addStaffMember(doctor);
	}
	
	public boolean addNurse(Nurse nurse) {
		return addStaffMember(nurse);
	}
	
	public boolean addIntensiveCareNurse(IntensiveCareNurse nurse) {
		return addStaffMember(nurse);
	}
	
	public boolean addPatient(Patient patient) {
		if(patient==null) {
			throw new NullPointerException();
		}
		if(patients.containsKey(patient.getId())) {
			throw new IDAlreadyAssociatedException();
		}
		if (patients.containsKey(patient.getId())) {
			throw new ObjectAlreadyExistsException(patient,this.getClass().getSimpleName());
		}
		return patients.put(patient.getId(),patient)==null;
	}
	
	public boolean addTreatment(Treatment treatment) {
		if(treatment==null) {
			throw new NullPointerException();
		}
		if (treatments.containsKey(treatment.getSerialNumber())) {
			throw new ObjectAlreadyExistsException(treatment,this.getClass().getSimpleName());
		}
		return treatments.put(treatment.getSerialNumber(),treatment)==null;
	}
	

	public boolean addVisit(Visit visit) {
	    if (visit == null) {
	        throw new NullPointerException();
	    }
	    if (visit.getStartDate().after(Hospital.TODAY)) {
	        throw new FutureDateException(visit.getStartDate());
	    }
	    if (visit.getEndDate().after(Hospital.TODAY)) {
	        throw new FutureDateException(visit.getEndDate());
	    }
	    if (visits.containsKey(visit.getNumber())) {
	        throw new ObjectAlreadyExistsException(visit, this.getClass().getSimpleName());
	    }
	    if (visit.getPatient().addVisit(visit)) {
	        return visits.put(visit.getNumber(), visit) == null;
	    }
	    return false;
	}

	
	//remove
	public boolean removeDepartment(Department department) {
	    if (department == null) {
	        throw new NullPointerException();
	    }
	    

	    if (!departments.containsKey(department.getNumber())) {
	        throw new ObjectDoesNotExist(department.getNumber(), department.getClass().getSimpleName(), this.getClass().getSimpleName());
	    }

	    // Remove all staff members from the department
	    if (!department.getStaffMembersList().isEmpty()) {
	        for (StaffMember staffMember : department.getStaffMembersList()) { // Use a copy of the list to avoid concurrent modification issues
	            staffMember.removeDepartment(department);
	        }
	    }

	    // Set department to null for all associated medical problems
	    for (MedicalProblem medicalProblem : medicalProblems.values()) {
	        if (medicalProblem.getDepartment() != null && medicalProblem.getDepartment().equals(department)) {
	            medicalProblem.setDepartment(null);
	        }
	    }

	    // Remove department from the hospital's department map
	    boolean removed = departments.remove(department.getNumber()) != null;

	    if (removed) {
	    } else {
	    }

	    return removed;
	}

	// remove medical problem from hospital, all visits and all treatments
	public boolean removeMedicalProblem(MedicalProblem medicalProblem) {

		if(medicalProblem==null) {
			throw new NullPointerException();
		}
		if (!medicalProblems.containsKey(medicalProblem.getCode())) {

			throw new ObjectDoesNotExist(medicalProblem.getCode(), medicalProblem.getClass().getSimpleName(), this.getClass().getSimpleName());
		}

		for(Treatment t:medicalProblem.getTreatmentsList()) {
			t.removeMedicalProblem(medicalProblem);
		}

		for(Visit v:visits.values()) {
			if(v.getMedicalProblemsList().contains(medicalProblem)){
				v.removeMedicalProblem(medicalProblem);
			}
		}

		return medicalProblems.remove(medicalProblem.getCode())!=null;
	}
	public boolean removeDisease(Disease disease) {
		return removeMedicalProblem(disease);
	}
	
	public boolean removeFracture(Fracture fracture) {
		return removeMedicalProblem(fracture);
	}
	
	public boolean removeInjury(Injury injury) {
		return removeMedicalProblem(injury);
	}
	
	public boolean removeMedication(Medication medication) {
		if(medication==null) {
			throw new NullPointerException();
		}
		if (!medications.containsKey(medication.getCode())) {
			throw new ObjectDoesNotExist(medication.getCode(), medication.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		for(Treatment treatment:treatments.values()) {
			if(treatment.getMedicationsList().contains(medication)) {
				treatment.removeMedication(medication);
			}
		}
		return medications.remove(medication.getCode())!=null;
	}
	
	public boolean removeStaffMember(StaffMember staffMember) {
		if(staffMember==null) {
			throw new NullPointerException();
		}
		if (!staffMembers.containsKey(staffMember.getId())) {
			throw new ObjectDoesNotExist(staffMember.getId(), staffMember.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		for(Department d:staffMember.getDepartments()) {
			if(d.getStaffMembersList().contains(staffMember)) {
				d.removeStaffMember(staffMember);
				if(d.getmanager().equals(staffMember)) {
					d.setmanager(null);
				}
			}

		}
		for(Treatment t : treatments.values()) {
			if(t.getDoctorsList().contains(staffMember)) {
				t.getDoctorsList().remove(staffMember);
			}
		}
		return staffMembers.remove(staffMember.getId()) != null;
	}
	
	public boolean removeDoctor(Doctor doctor) {
		return removeStaffMember(doctor);
	}
	
	public boolean removeNurse(Nurse nurse) {
		return removeStaffMember(nurse);
	}
	
	public boolean removePatient(Patient patient) {
		if(patient==null) {
			throw new NullPointerException();
		}
		if (!patients.containsKey(patient.getId())) {
			throw new ObjectDoesNotExist(patient.getId(), patient.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		for(Visit visit:patient.getVisitsList()) {
			visit.setPatient(null);
		}
		return patients.remove(patient.getId()) != null;
	}
	
	public boolean removeTreatment(Treatment treatment) {
		if(treatment==null) {
			throw new NullPointerException();
		}
		if (!treatments.containsKey(treatment.getSerialNumber())) {
			throw new ObjectDoesNotExist(treatment.getSerialNumber(), treatment.getClass().getSimpleName(), this.getClass().getSimpleName());
		}
		for(MedicalProblem medicalProblem:treatment.getMedicalProblemsList()) {
			if(medicalProblem.getTreatmentsList().contains(treatment)){
				medicalProblem.getTreatmentsList().remove(treatment);
			}
		}
		for(Visit v:visits.values()) {
			if(v.getTreatmentsList().contains(treatment)){
				v.getTreatmentsList().remove(treatment);
			}		
		}
		return treatments.remove(treatment.getSerialNumber()) != null;
	}
	
	public boolean removeVisit(Visit visit) {
		if(visit==null) {
			throw new NullPointerException();
		}
		if (!visits.containsKey(visit.getNumber())) {
			throw new ObjectDoesNotExist(visit.getNumber(), visit.getClass().getSimpleName(), this.getClass().getSimpleName());
		}if(visit.getPatient()!=null) {
			visit.getPatient().removeVisit(visit);
		}
		return visits.remove(visit.getNumber()) != null;
	}
	
	//getReal
	public Department getRealDepartment(Integer number) {
		if(!departments.containsKey(number)) {
			throw new ObjectDoesNotExist(number, "Department", this.getClass().getSimpleName());
		}
		return departments.get(number);
	}
	
	public MedicalProblem getMedicalProblem(String code) {
		if(!medicalProblems.containsKey(code)) {
			throw new ObjectDoesNotExist(code, "MedicalProblem", this.getClass().getSimpleName());
		}
		return medicalProblems.get(code);
	}
	
	public Disease getRealDisease(String code) {
		return (Disease) getMedicalProblem(code);
	}
	
	public Fracture getRealFracture(String code) {
		return (Fracture) getMedicalProblem(code);
	}
	

	public Injury getRealInjury(String code) {
		return (Injury) getMedicalProblem(code);
	}
	
	public StaffMember getStaffMember(Integer id) {
		if(!staffMembers.containsKey(id)) {
			throw new ObjectDoesNotExist(id, "StaffMember", this.getClass().getSimpleName());
		}
		return staffMembers.get(id);
	}
	public Doctor getRealDoctor(int id) {
		return (Doctor) getStaffMember(id);
	}
	
	public Nurse getRealNurse(int id) {
		return (Nurse) getStaffMember(id);
	}
	
	public Patient getRealPatient(Integer id) {
		if(!patients.containsKey(id)) {
			throw new ObjectDoesNotExist(id, "Patient", this.getClass().getSimpleName());
		}
		return patients.get(id);
	}
	
	
	public Medication getRealMedication(Integer code) {
		if(!medications.containsKey(code)) {
			throw new ObjectDoesNotExist(code, "Medication", this.getClass().getSimpleName());
		}
		return medications.get(code);
	}
	
	public Treatment getRealTreatment(Integer number) {
		if(!treatments.containsKey(number)) {
			throw new ObjectDoesNotExist(number, "Treatment", this.getClass().getSimpleName());
		}
		return treatments.get(number);
	}
	
	public Visit getRealVisit(Integer number) {
		if(!visits.containsKey(number)) {
			throw new ObjectDoesNotExist(number, "Visit", this.getClass().getSimpleName());
		}
		return visits.get(number);
	}
	
	//Queries
	public int countMedications(double min_dosage, double max_dosage) {
		//Returns the number of medications that have bigger dosage then the min and smaller then the max
		int count=0;
		for(Medication medication:medications.values()) {
			if(medication.getDosage()>=min_dosage&&medication.getDosage()<=max_dosage) {
				count++;
			}
		}
		return count;
	}
	
	public double differenceBetweenTheLongestAndShortestVisit(Patient patient) {
		//Returns the difference Between The Longest And Shortest Visit of the patient
		double longestVisit=Double.MIN_VALUE;
		double shortestVisit=Double.MAX_VALUE;
		for (Visit v:patient.getVisitsList()) {
			if(v.visitLength()>longestVisit) {
				longestVisit=v.visitLength();
			}
			if(v.visitLength()<shortestVisit) {
				shortestVisit=v.visitLength();
			}
		}
		return longestVisit-shortestVisit;
	}
	
	public void printHowManyFinishInternship() {
		//print the number of doctors that finish internship
		int count=0;
		for(StaffMember staffMember:staffMembers.values()) {
			if(staffMember instanceof Doctor) {
				Doctor doctor=(Doctor) staffMember;
				if(doctor.isFinishInternship()) {
					count++;
				}
			}
		}
		MyFileLogWriter.println("The number of doctors that finish internship is: "+count);
	}
	
	public int howManyVisitBefore(Date date) {
		//Returns how many patients have a visit that end before the date
		int count=0;
		for(Patient patient:patients.values()) {
			boolean hasVisitBefore=false;
			for(Visit v:patient.getVisitsList()) {
				if(hasVisitBefore==false) {
					hasVisitBefore=v.getEndDate().before(date);
				}
			}
			if(hasVisitBefore) {
				count++;
			}
		}
		return count;
	}
	
	public void printOldestNurse() {
		//Prints the details of the nurse that have works the longest time
		Nurse oldestNurse=null;
		double oldestAge=Double.MIN_VALUE;
		for(StaffMember staffMember:staffMembers.values()) {
			if(staffMember instanceof Nurse) {
				if(oldestAge<staffMember.getWorkTime()) {
					oldestAge=staffMember.getWorkTime();
					oldestNurse=(Nurse) staffMember;
				}
			}
		}
		MyFileLogWriter.println("The oldest Nurse in the job is : "+oldestNurse);
	}
	
	public HashMap<StaffMember, ArrayList<Department>>staffMembersThatWorksInMoreThenOneDepartment(){
		//Returns the StaffMembers that have more then one Department
		HashMap<StaffMember, ArrayList<Department>>result=new HashMap<StaffMember, ArrayList<Department>>();
		for(StaffMember staffMember:staffMembers.values()) {
			if(staffMember.getDepartments().size()>1) {
				result.put(staffMember, new ArrayList<Department>(staffMember.getDepartments()));
			}
		}
		return result;
	}
	
	public HashMap<Department, HashMap<MedicalProblem, ArrayList<Treatment>>>getTreatmentsByMedicalProblemsByDepartment(){
		//Returns for each Department the MedicalProblems that treatments in it and their Treatments
		HashMap<Department, HashMap<MedicalProblem, ArrayList<Treatment>>>result=
				new HashMap<Department, HashMap<MedicalProblem,ArrayList<Treatment>>>();
		for(Department department:departments.values()) {
			result.put(department, new HashMap<MedicalProblem, ArrayList<Treatment>>());
		}
		for(MedicalProblem medicalProblem:medicalProblems.values()) {
			result.get(medicalProblem.getDepartment()).put(medicalProblem, new ArrayList<Treatment>(medicalProblem.getTreatmentsList()));
		}
		return result;
	}
	
	public HashMap<Specialization, Integer>getNumberOfDoctorsBySpecialization(){
		//Returns for each Specialization the Number of Doctors that Specialize in it
		HashMap<Specialization, Integer>result=new HashMap<Specialization, Integer>();
		for(Specialization specialization:Specialization.values()) {
			result.put(specialization, 0);
		}
		for(StaffMember staffMember:staffMembers.values()) {
			if(staffMember instanceof Doctor) {
				Doctor doctor=(Doctor) staffMember;
				int x=result.get(doctor.getSpecialization());
				//if(doctor.getSpecialization().equals(Specialization.IntensiveCare))
				//System.out.println("a");
				result.put(doctor.getSpecialization(), x+1);
				//System.out.println(result.get(Specialization.IntensiveCare));
			}
		}
		return result;
	}
	
	public int howManyIntensiveCareStaffMembers() {
		//Returns the number of StaffMembers that are IntensiveCareStaffMembers
		int count=0;
		for(StaffMember staffMember:staffMembers.values()) {
			if(staffMember instanceof IntensiveCareStaffMember) {
				count++;
			}
		}
		return count;
	}
	
	public double avgSalary() {
		//Returns the average salary of all the StaffMembers
		double sum=0.0;
		for(StaffMember staffMember:staffMembers.values()) {
			sum+=staffMember.getSalary();
		}
		return sum/staffMembers.size();
	}

	
	public boolean isCompliesWithTheMinistryOfHealthStandard() {
		/*Checks if the hospital complies with the ministry of health standard. a.k.a:
		 * a. more then 50% IntensiveCareStaffMembers
		 * b. the average salary of all the StaffMembers is higher then 10,000
		*/
		double requiredSalary=10000;
		double requiredPercentOfIntensiveCareStaffMembers=0.5;
		return (avgSalary()>=requiredSalary&&
				(howManyIntensiveCareStaffMembers()/staffMembers.size())>=
				requiredPercentOfIntensiveCareStaffMembers);
	}
	
	public Doctor AppointANewManager(Department department){
		/* The method Appoint a new manager to the department:
		 * Fired the old manager if exist
		 * Finds the designated new manager, a.k.a:
		 * 		the doctor from the department that is works the most time,
		 * 		that have finish internship
		 * 		and their specialization matches the department specialization
		 * Appoint them as the new manager and give him 5000 rise
		 * */

		if(department==null) {
			return null;

		}
		if(department.getmanager() != null) {
			System.out.println("Manager found. firing.");
			removeDoctor(department.getmanager());
		}
		Doctor doctor=getOldestDoctor(department);
		if(doctor==null) {
			System.out.println("No suitable doctor found, returning null.");
			return null;
		}
		doctor.setSalary(doctor.getSalary()+5000);
		System.out.println("giving raise");

		department.setmanager(doctor);
		System.out.println("setting new manager");

		return doctor;	
	}
	
	public Doctor getOldestDoctor(Department department) {
		/*Finds the doctor from the department that is works the most time,
		 *that have finish internship
		 *and them specialization much the department specialization*/
		Doctor oldestdoctor=null;
		double oldestAge=Double.MIN_VALUE;
		for(StaffMember staffMember:department.getStaffMembersList()) {
			if(staffMember instanceof Doctor) {
				Doctor doctor=(Doctor) staffMember;
				if(doctor.getSpecialization().equals(department.getSpecialization())&&doctor.isFinishInternship()) {
					if(oldestAge<doctor.getWorkTime()) {
						oldestAge=doctor.getWorkTime();
						oldestdoctor = doctor;
					}
				}
			}
		}	
		return oldestdoctor;
	}
	

	
	
	
	//getters
	public HashMap<Integer, Department> getDepartments() {
		return departments;
	}
	public HashMap<String, MedicalProblem> getMedicalProblems() {
		return medicalProblems;
	}
	public HashMap<Integer, StaffMember> getStaffMembers() {
		return staffMembers;
	}
	public HashMap<Integer, Medication> getMedications() {
		return medications;
	}
	public HashMap<Integer, Patient> getPatients() {
		return patients;
	}
	public HashMap<Integer, Treatment> getTreatments() {
		return treatments;
	}
	public HashMap<Integer, Visit> getVisits() {
		return visits;
	}
	//ASSUMPTION: THIS WILL ALWAYS WORK
	public Department searchDepartmentBySpecialization(Specialization specialization) {
		//search Department that it's specialization much the required specialization
		for(Department department:departments.values()) {
			if(department.getSpecialization().equals(specialization)) {
				System.out.println("found department with specialization: " + specialization);
				return department;
			}
		}
		System.out.println("failed to find department with specialization: " + specialization);

		return null;
	}
	
	
	// Generic method to filter and return a set of instances of a specific class
    private <T> HashSet<T> getAllInstancesOfType(Class<T> type, Iterable<?> collection) {
        HashSet<T> toReturn = new HashSet<>();
        for (Object obj : collection) {
            if (type.isInstance(obj)) {
                toReturn.add(type.cast(obj));
            }
        }
        return toReturn;
    }

    // Method to get all doctors
    public HashSet<Doctor> getAllDoctors() {
        return getAllInstancesOfType(Doctor.class, getStaffMembers().values());
    }

    // Method to get all nurses
    public HashSet<Nurse> getAllNurses() {
        return getAllInstancesOfType(Nurse.class, getStaffMembers().values());
    }

    // Method to get all diseases
    public HashSet<Disease> getAllDiseases() {
        return getAllInstancesOfType(Disease.class, medicalProblems.values());
    }

    // Method to get all injuries
    public HashSet<Injury> getAllInjuries() {
        return getAllInstancesOfType(Injury.class, medicalProblems.values());
    }

    // Method to get all fractures
    public HashSet<Fracture> getAllFractures() {
        return getAllInstancesOfType(Fracture.class, medicalProblems.values());
    }
	
	//setters
	public void setDepartments(HashMap<Integer, Department> departments) {
		this.departments = departments;
	}
	public void setMedicalProblems(HashMap<String, MedicalProblem> medicalProblems) {
		this.medicalProblems = medicalProblems;
	}
	public void setStaffMembers(HashMap<Integer, StaffMember> staffMembers) {
		this.staffMembers = staffMembers;
	}
	public void setMedications(HashMap<Integer, Medication> medications) {
		this.medications = medications;
	}
	public void setPatients(HashMap<Integer, Patient> patients) {
		this.patients = patients;
	}
	public void setTreatments(HashMap<Integer, Treatment> treatments) {
		this.treatments = treatments;
	}
	public void setVisits(HashMap<Integer, Visit> visits) {
		this.visits = visits;
	}
	
	public HashMap<Integer, String> getPasswords() {
		return passwords;
	}

    public StaffMember getStaffMemberById(int id) {
        return staffMembers.get(id); // Retrieves the staff member from the map
    }
	public void replaceNurse(int id, IntensiveCareNurse intensiveCareNurse) {
		staffMembers.put(id,intensiveCareNurse);
	}
	public void replaceDoctor(int id, IntensiveCareDoctor intensiveCareDoctor) {
		staffMembers.put(id,intensiveCareDoctor);
	}
	
	// all object instances use this counter
    public synchronized int getNextPKNumber() {
        return PKCounter++;
    }
	
//	private Object readResolve() {
//		System.out.println("reading");
//	    return getInstance();
//	}

}
		