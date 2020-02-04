package events;

import people.Patient;
import people.Doctor;

public class DoctorExamEvent extends Event {

    private Patient patient;
    private Doctor doctor;

    @Override
    public void execute() {
        
    }

    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }

    public Doctor setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this.doctor;
    }
}