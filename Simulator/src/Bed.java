

public class Bed {
    public boolean isFull;
    private Patient patient;
    private MedicalProfessional medicalProfessional;

    public Bed(Patient patient) {
        this.isFull = false;
        this.patient = patient;
    }

    public Bed() {
        this.isFull = false;
    }

    //Getters
    public boolean isFull() {
        return this.isFull;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public MedicalProfessional getMedicalProfessional() {
        return medicalProfessional;
    }

    //Setters
    public void setMedicalProfessional(MedicalProfessional professional) {
        this.medicalProfessional = professional;
    }

    //False if patient already present
    public boolean addPatient(Patient patient) {
        if (this.isFull) {
            return false;
        }

        this.patient = patient;
        this.isFull = true;
        Simulator.bedsFull += 1;
        return true;
    }

    //False - no patient to remove
    public boolean removePatient() {
        if (this.isFull) {
            this.patient = null;
            this.isFull = false;
            Simulator.bedsFull -= 1;
            return true;
        }

        return false;
    }
}
