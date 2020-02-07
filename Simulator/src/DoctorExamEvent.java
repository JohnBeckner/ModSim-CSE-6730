

public class DoctorExamEvent extends Event {

    private Patient patient;
    private Doctor doctor;
    private Bed bed;

    @Override
    public void execute() {
        patient.status = Status.DOCTOR_EVAL;
        bed.setMedicalProfessional(doctor);
        System.out.println("Patient " + patient.patientNumber + " evaluated by nurse");
    }

    public DoctorExamEvent(Patient patient, Doctor doctor, Bed bed) {
        this.patient = patient;
        this.bed = bed;
        this.doctor = doctor;
    }

    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }

    public Doctor setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this.doctor;
    }

    public Bed setBed(Bed bed) {
        this.bed = bed;
        return this.bed;
    }
}