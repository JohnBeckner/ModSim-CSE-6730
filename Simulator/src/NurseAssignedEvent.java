public class NurseAssignedEvent extends Event {

    private Patient patient;
    private Nurse nurse;

    @Override
    public void execute() {
        this.patient.bed.setMedicalProfessional(this.nurse);
        this.patient.status = Status.NURSE_EVAL;
        //System.out.println("Nurse assigned to patient " + patient.patientNumber);
        this.nurse.addPatient(patient);
    }

    public NurseAssignedEvent(Patient patient, Nurse nurse) {
        this.patient = patient;
        this.nurse = nurse;
    }
}
