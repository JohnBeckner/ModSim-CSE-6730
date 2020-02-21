import java.util.Arrays;

public class Nurse implements MedicalProfessional {

    public Patient[] patients;

    @Override
    public int examine(Patient patient) {
        return 0;
    }

    public Nurse() {
        patients = new Patient[Simulator.MAX_NURSE_PATIENTS];
    }

    public Patient[] getPatients() {
        return patients;
    }

    public Patient addPatient(Patient patient) {

        if (patients.length >= Simulator.MAX_NURSE_PATIENTS) {
            //cannot add new patient
        } else {
            // add new patient
        }

        return patients[patients.length - 1];
    }

    public Patient removePatient(Patient patient) {
        Patient removedPatient = null;

        for (int i = 0; i < patients.length; i++) {
            if (patients[i].getPatientNumber() == patient.getPatientNumber()) {
                //remove Patient
            } 

            if (removedPatient != null) {
                //shift patients over by one
            }
        }
        return removedPatient;
    }
}
