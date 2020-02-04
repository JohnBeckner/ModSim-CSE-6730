

public class PatientLeavesEvent extends Event {

    private Patient patient;

    @Override
    public void execute() {

    }

    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }
}