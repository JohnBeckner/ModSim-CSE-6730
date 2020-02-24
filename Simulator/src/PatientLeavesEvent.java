

public class PatientLeavesEvent extends Event {

    private Patient patient;
    private Bed bed;
    private Doctor doctor;

    @Override
    public void execute() {
        bed.removePatient();
        bed.setMedicalProfessional(null);
        patient.bed = null;
        patient.setTimeOut(Simulator.time);
        doctor.removePatient(patient);
        System.out.println("Patient " + patient.patientNumber + " evaluated by doctor. Leaving emergency room. bed ID:" + bed.toString());
        System.out.println("Total time: " + (patient.getTimeOut() - patient.getTimeIn()));
        System.out.println("wait time: " + ((patient.getTimeOut() - patient.getTimeIn()) - patient.getWaitTime()));
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