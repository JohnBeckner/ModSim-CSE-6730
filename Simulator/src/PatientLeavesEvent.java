

public class PatientLeavesEvent extends Event {

    private Patient patient;
    private Bed bed;

    @Override
    public void execute() {
        bed.removePatient();
        patient.bed = null;
        System.out.println("Patient " + patient.patientNumber + " evaluated by doctor. Leaving emergency room. " + bed);
    }

    public PatientLeavesEvent(Bed bed, Patient patient) {
        this.bed = bed;
        this.patient = patient;
    }


    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }
}