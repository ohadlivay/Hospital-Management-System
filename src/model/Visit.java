package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectDoesNotExist;
import utils.UtilsMethods;

public class Visit  implements Serializable, ShortToStringable{

	private int number;//PK
	private Patient patient;
	private Date startDate;
	private Date endDate;
	private HashSet<MedicalProblem>medicalProblemsList;
	private HashSet<Treatment> treatmentsList;
	
	
	//constructors
	public Visit(int number, Patient patient, Date startDate, Date endDate, HashSet<MedicalProblem>medicalProblemsList,
			HashSet<Treatment> treatmentsList) {
		super();
		this.number = number;
		this.patient = patient;
		this.startDate = startDate;
		this.endDate = endDate;
		this.medicalProblemsList = medicalProblemsList;
		this.treatmentsList = treatmentsList;
	}
	
	public Visit(int number, Patient patient, Date startDate, Date endDate) {
		super();
		this.number = number;
		this.patient = patient;
		this.startDate = startDate;
		this.endDate = endDate;
		this.medicalProblemsList = new HashSet<>();
		this.treatmentsList = new HashSet<>();
	}

	

	//getters
	public double visitLength() {
		//returns the length of the visit, in days
		return UtilsMethods.dateDiffInDays(startDate, endDate);
	}
	
	public int getNumber() {
		return number;
	}

	public Patient getPatient() {
		return patient;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public HashSet<MedicalProblem> getMedicalProblemsList() {
		return medicalProblemsList;
	}

	public HashSet<Treatment> getTreatmentsList() {
		return treatmentsList;
	}

	//setters
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setMedicalProblemsList(HashSet<MedicalProblem> medicalProblemsList) {
		this.medicalProblemsList = medicalProblemsList;
	}

	public void setTreatmentsList(HashSet<Treatment> treatmentsList) {
		this.treatmentsList = treatmentsList;
	}

	//add
	public boolean addMedicalProblem(MedicalProblem medicalProblem) {
		if(medicalProblem==null) {
			throw new NullPointerException();
		}
		if (medicalProblemsList.contains(medicalProblem)) {
			throw new ObjectAlreadyExistsException(medicalProblem, this);
		}
		return medicalProblemsList.add(medicalProblem);
	}
	
	public boolean addTreatmentAndMedicalProblemToVisit(MedicalProblem medicalProblem, Treatment treatment, Visit visit) {
		if(medicalProblem==null || treatment==null || visit == null) {
			throw new NullPointerException();
		}
		if (medicalProblemsList.contains(medicalProblem)) {
			throw new ObjectAlreadyExistsException(medicalProblem, this);
		}
		return medicalProblemsList.add(medicalProblem);
	}
	
	public boolean addDisease(Disease disease) {
		return addMedicalProblem(disease);
	}
	
	public boolean addFracture(Fracture fracture) {
		return addMedicalProblem(fracture);
	}
	
	public boolean addInjury(Injury injury) {
		return addMedicalProblem(injury);
	}
	
	public boolean addTreatment(Treatment treatment) {
		if(treatment==null) {
			throw new NullPointerException();
		}
		if (treatmentsList.contains(treatment)) {
			throw new ObjectAlreadyExistsException(treatment, this);
		}
		return treatmentsList.add(treatment);
	}
	
	//remove
	public boolean removeMedicalProblem(MedicalProblem medicalProblem) {
		if(medicalProblem==null) {
			throw new NullPointerException();
		}
		if (!medicalProblemsList.contains(medicalProblem)) {

			throw new ObjectDoesNotExist(medicalProblem.getCode(), medicalProblem.getClass().getSimpleName(), this);
		}
		return medicalProblemsList.remove(medicalProblem);
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
	
	public boolean removeTreatment(Treatment treatment) {
		if(treatment==null) {
			throw new NullPointerException();
		}
		if (!treatmentsList.contains(treatment)) {
			throw new ObjectDoesNotExist(treatment.getSerialNumber(), treatment.getClass().getSimpleName(), this);
		}
		return treatmentsList.remove(treatment);
	}

	//toString
	@Override
	public String toString() {
		return "Visit [number=" + number + ", patient=" + patient + ", startDate=" + startDate + ", endDate=" + endDate
				+  "]";
	}

	//hashCode and equals according to the PK
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
		Visit other = (Visit) obj;
		return number == other.number;
	}
	
	@Override
	public String shortToString() {
		return "Visit: #" + this.getNumber() + " of " + this.getPatient().getFirstName() + " " + this.getPatient().getLastName();
	}

    public String toWordString() {
        // Create a date format for displaying dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Start building the formatted string
        StringBuilder sb = new StringBuilder();
        sb.append("Visit Number: ").append(number).append("\n");
        sb.append("Patient: ").append(patient.getFirstName()).append(" ").append(patient.getLastName()).append("\n");
        sb.append("Start Date: ").append(startDate != null ? dateFormat.format(startDate) : "N/A").append("\n");
        sb.append("End Date: ").append(endDate != null ? dateFormat.format(endDate) : "N/A").append("\n");

        // Add medical problems
        sb.append("Medical Problems: \n");
        if (medicalProblemsList != null && !medicalProblemsList.isEmpty()) {
            for (MedicalProblem problem : medicalProblemsList) {
                sb.append(" - Code: ").append(problem.getCode()).append(", Name: ").append(problem.getName()).append("\n");
            }
        } else {
            sb.append(" No medical problems recorded.\n");
        }

        // Add treatments
        sb.append("Treatments: \n");
        if (treatmentsList != null && !treatmentsList.isEmpty()) {
            for (Treatment treatment : treatmentsList) {
                sb.append(" - ").append(treatment.getDescription()).append("\n");
            }
        } else {
            sb.append(" No treatments recorded.\n");
        }

        // Return the formatted string
        return sb.toString();
    }
	
}
