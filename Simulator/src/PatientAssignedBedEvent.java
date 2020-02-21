
public class PatientAssignedBedEvent extends Event {

    private Patient patient;
    private Nurse nurse;

    @Override
    public void execute() {
        System.out.println("Patient " + patient.patientNumber + " being assigned bed");
        for (Bed bed: Simulator.beds) {
            if (!bed.isFull()) {
                bed.addPatient(patient);
                patient.bed = bed;
                patient.status = Status.WAITING_FOR_NURSE;
                //bed.setMedicalProfessional(this.nurse);
                break;
            }
        }
    }

    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }

    public Nurse setNurse(Nurse nurse) {
        this.nurse = nurse;
        return this.nurse;
    }
}
