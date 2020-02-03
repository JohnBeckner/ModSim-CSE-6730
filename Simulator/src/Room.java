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
            this.isFull = true;
        } else {
            this.isFull = false;
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
            if (this.patients.isEmpty()) {
                Main.rooms.add(this);
                Main.emptyRooms.remove(this);
            }
            this.patients.add(patient);
            updateIsFull();
            return true;
        }

        return false;
    }

    public boolean removePatient(Patient patient) {
        if (!this.patients.isEmpty()) {
            if (this.patients.remove(patient)) {
                if (this.patients.isEmpty()) {
                    Main.rooms.remove(this);
                    Main.emptyRooms.add(this);
                }
                updateIsFull();
                return true;
            }
        }

        return false;
    }
}
