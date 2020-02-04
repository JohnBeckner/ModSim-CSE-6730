

public class DoctorExamEvent extends Event {

    private Patient patient;
    private Doctor doctor;

    @Override
    public void execute() {
        patient.status = Status.DOCTOR_EVAL;
        patient.bed.setMedicalProfessional(doctor);
        System.out.println("Patient evaluated by nurse");
    }

    public Patient setPatient(Patient patient) {
        this.patient = patient;
        return this.patient;
    }

    public Doctor setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this.doctor;
    }
}