import java.util.LinkedList;

import types.*;
import people.*;

public class Bed {
    private boolean isFull;
    private Patient patient;
    private MedicalProfessional medicalProfessional;

    public Bed(Patient patient) {
        this.isFull = true;
        this.patient = patient;
    }

    public Bed() {
        this.isFull = false;
    }

    //Getters
    public boolean isFull() {
        return this.isFull;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public MedicalProfessional getMedicalProfessional() {
        return medicalProfessional;
    }

    //Setters
    public void setMedicalProfessional(MedicalProfessional professional) {
        this.medicalProfessional = professional;
    }

    //False if patient already present
    public boolean addPatient(Patient patient) {
        if (this.isFull) {
            return false;
        }

        this.patient = patient;
        this.isFull = true;
        return true;
    }

    //False - no patient to remove
    public boolean removePatient() {
        if (this.isFull) {
            this.patient = null;
            this.isFull = false;
            return true;
        }

        return false;
    }
}
