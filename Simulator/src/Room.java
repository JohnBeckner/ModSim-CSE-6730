import java.util.LinkedList;

public class Room {
    private boolean isFull;
    private int capacity;
    private RoomType type;
    private LinkedList<Patient> patients;
    private MedicalProfessional medicalProfessional;

    public Room(RoomType type) {
        this.type = type;
        this.isFull = false;
        this.capacity = roomCapacity();
        this.patients = new LinkedList<>();
    }

    //TODO -- Add more accurrate ranges/constants for room capacity (if necessary)
    public int roomCapacity() {
        int capacity = -1;
        switch (this.type) {
            case TREATMENT:
                capacity = 2;
                break;
            case LAB_TEST:
            case XRAY:
                capacity = 1;
                break;
            default:
                throw new EnumConstantNotPresentException(RoomType.class, this.type.toString());
        }

        return capacity;
    }

    //Call this to update the isFull boolean
    private void updateIsFull() {
        if (this.patients.size() >= capacity) {
            isFull = true;
        }
    }

    //Getters
    public boolean isFull() {
        return this.isFull;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomType getRoomType() {
        return type;
    }

    public Patient[] getPatients() {
        return (Patient[])this.patients.toArray();
    }

    public MedicalProfessional getMedicalProfessional() {
        return medicalProfessional;
    }

    //Setters
    public void setMedicalProfessional(MedicalProfessional professional) {
        this.medicalProfessional = professional;
    }

    //Returns true if successful
    public boolean addPatient(Patient patient) {
        if (!isFull) {
            this.patients.add(patient);
            updateIsFull();
            return true;
        }

        return false;
    }

    public boolean removePatient(Patient patient) {
        if (!this.patients.isEmpty()) {
            return this.patients.remove(patient);
        }

        return false;
    }
}
