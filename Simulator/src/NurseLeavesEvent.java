public class NurseLeavesEvent extends Event {

    private Patient patient;
    private Nurse nurse;
    private Bed bed;

    @Override
    public void execute() {
        this.bed.setMedicalProfessional(null);
        this.patient.status = Status.WAITING_FOR_DOCTOR;
        //System.out.println("Nurse checkup complete for patient " + patient.patientNumber);
        nurse.removePatient(patient);
    }

    public NurseLeavesEvent(Patient patient) {
        this.patient = patient;
        this.bed = patient.bed;

        try {
            this.nurse = (Nurse) this.bed.getMedicalProfessional();
        } catch (ClassCastException e) {
            System.out.println("Is this type a nurse? " + (this.bed.getMedicalProfessional() instanceof Nurse));
        } catch (Exception e) {
            System.out.println("Unknown exception in NurseLeavesEvent");
        }
    }
}
