
import java.util.concurrent.LinkedBlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Simulator {

    public static PriorityQueue<Patient> waitingRoom;
    public static ArrayList<Bed> beds;
    private static LinkedBlockingQueue<Event> fel;

    public static int bedsFull = 0;
    public static int time = 0;

    final static int NUMBER_OF_BEDS = 10;
    final static Range NURSE_RANGE = new Range(20, 40);
    final static Range DOCTOR_RANGE = new Range(180, 240);

    private static Priority[] priorityValues = Priority.values();
    private static int prioritySize = priorityValues.length;
    private static final Random randomPriority = new Random();
    public static int nextId = 0;

    static Comparator<Patient> priority = (p1, p2) -> p1.priority.ordinal() - p2.priority.ordinal();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Initial project");

        waitingRoom = new PriorityQueue<Patient>(Simulator.priority);
        beds = new ArrayList<Bed>();
        fel = new LinkedBlockingQueue<Event>();

        for (int i = 0; i < NUMBER_OF_BEDS; i++) {
            beds.add(new Bed());
        }

        // generate new patient
        // make sure to print patient information
        int patients = 0;

        System.out.println("Enter the number of paitents to generate");
        String generatePatient = sc.nextLine();
        try {
            patients = Integer.parseInt(generatePatient);
            if (patients < 0) {
                throw new NumberFormatException();
            }
            System.out.println("Generating " + generatePatient + " patients");
        } catch (NumberFormatException e) {
            System.out.println("Not a valid input.");
            System.exit(0);
        }

        for (int i = 0; i < patients; i ++) {
            Patient newPatient = new Patient(priorityValues[randomPriority.nextInt(prioritySize)]);
            waitingRoom.add(newPatient);
            System.out.println("Patient" + newPatient.patientNumber + " added with priority: " + newPatient.getPriority().toString());
        }

        //Populate initial events somehow
        System.out.println("Assume time in minutes:");
        while (!fel.isEmpty() || !waitingRoom.isEmpty() || time < 250) {
            System.out.println(Simulator.time);
            if (!fel.isEmpty()) {
                Event event = fel.remove();
                event.execute();
            }
            movePatientsForward();
            Simulator.time += 1;
        }
    }

    /*
     * Function to push patients along the ER path
     * Go backwards
     */
    private static void movePatientsForward() {
        //1) Are patients being examined ready to leave?
        //2) Are patients undergoing tests ready to be re-examined?
        //3) Are patients who are waiting for lab results ready to be re-examined?

        if (Simulator.bedsFull < NUMBER_OF_BEDS && !waitingRoom.isEmpty()) {
            Patient patient = waitingRoom.remove();
            PatientAssessedEvent event = new PatientAssessedEvent();
            event.setPatient(patient);
            event.setNurse(new Nurse());
            fel.add(event);
            //PatientAssessedEvent


        }

        for (Bed bed: beds) {
            if (bed.isFull()) {
                bed.getPatient().waitTime += 1;

                //Default set for TREATED state so immediate exit upon next iteration
                int rangeVal = -1;
                if (bed.getMedicalProfessional() != null) {
                    if (bed.getMedicalProfessional() instanceof Nurse) {
                        rangeVal = NURSE_RANGE.generateRandomInRange();
                    } else {
                        rangeVal = DOCTOR_RANGE.generateRandomInRange();
                    }
                }

                Patient patient = bed.getPatient();
                if (patient.waitTime >= rangeVal) {
                    switch(patient.status) {
                        case NURSE_EVAL:
                            DoctorExamEvent event = new DoctorExamEvent();
                            event.setDoctor(new Doctor());
                            event.setPatient(patient);
                            fel.add(event);
                            break;
                        case DOCTOR_EVAL:
                            bed.removePatient();
                            System.out.println("Patient " + patient.patientNumber + " with priority " + patient.priority + " has been evaluated by doctor. Leaving emergency room. " + bed);
                            break;
                        default:
                            System.out.println("Status is incorrect: " + patient.status.toString());
                    }
                }
            }
        }
    }
}
