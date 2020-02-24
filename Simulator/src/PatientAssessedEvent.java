public class PatientAssessedEvent extends Event {

    @Override
    public void execute() {
        Priority newPriority = pickRandomPriority();
        while(newPriority == null) {
            newPriority = pickRandomPriority();
        }
        Patient patient = new Patient(newPriority);
        Simulator.waitingRoom.add(patient);
        //System.out.println("Patient " + patient.patientNumber + " added with priority: " + patient.getPriority().toString());
    }

    //randomly pick patient priority
    private static Priority pickRandomPriority() {
        double rand = Math.random();
        for (Priority item: Priority.values()) {
            if (rand <= item.getProb()) {
                return item;
            }
            rand -= item.getProb();
        }
        return null;
    }
}
