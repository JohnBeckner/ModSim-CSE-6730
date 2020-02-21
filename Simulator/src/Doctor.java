

public class Doctor implements MedicalProfessional {

    public Patient[] patients;

    @Override
    public int examine(Patient patient) {
        return 0;
    }

    public Doctor() {
        patients = new Patient[Simulator.MAX_DOCTOR_PATIENTS];
    };
}
