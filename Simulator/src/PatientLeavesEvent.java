

public class PatientLeavesEvent extends Event {

    private Patient patient;
    private Bed bed;
    private Doctor doctor;

    @Override
    public void execute() {
        bed.removePatient();
        bed.setMedicalProfessional(null);
        patient.bed = null;
        Simulator.doctors.push(this.doctor);
        System.out.println("Patient " + patient.patientNumber + " evaluated by doctor. Leaving emergency room. bed ID:" + bed.toString());
    }

    public PatientLeavesEvent(Bed bed, Patient patient) {
        this.bed = bed;
        this.patient = patient;

        try {
            this.doctor = (Doctor) this.bed.getMedicalProfessional();
        } catch (ClassCastException e) {
            System.out.println("Is this type a doctor? " + (this.bed.getMedicalProfessional() instanceof Doctor));
        } catch (Exception e) {
            System.out.println("Unknown exception in PatientLeavesEvent");
        }
    }


    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }
}