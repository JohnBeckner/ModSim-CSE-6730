import java.util.ArrayList;
import java.util.Arrays;

public class Nurse implements MedicalProfessional {

    public ArrayList<Patient> patients;

    @Override
    public int examine(Patient patient) {
        return 0;
    }

    public Nurse() {
        patients = new ArrayList<Patient>();
    }

    public int getNumPatients() {
        return patients.size();
    }

    public boolean canTakePatients() {
        if (patients.size() >= Simulator.MAX_NURSE_PATIENTS) {
            return false;
        } else {
            return true;
        }
    }

    public Patient addPatient(Patient patient) {

        if (patients.size() >= Simulator.MAX_NURSE_PATIENTS) {
            return null;
        } else {
            patients.add(patient);
        }

        return patients.get(patients.size() - 1);
    }

    public Patient removePatient(Patient patient) {
        Patient removedPatient = null;

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientNumber() == patient.getPatientNumber()) {
                patients.remove(patients.get(i));
            } 
        }
        return removedPatient;
    }
}
