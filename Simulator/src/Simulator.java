
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;


public class Simulator {

    private static int triageQueue = 0;
    private static int interarrivalTime = 0;

    public static PriorityQueue<Patient> waitingRoom;
    public static ArrayList<Bed> beds;
    private static LinkedBlockingQueue<Event> fel;
    public static ArrayList<Doctor> doctors;
    public static ArrayList<Nurse> nurses;
    public static ArrayList<Integer> arrivals;
    public static ArrayList<String> patientOutput;

    public static int bedsFull = 0;
    public static int time = 0;

    public static int MAX_NURSE_PATIENTS = 5;
    public static int MAX_DOCTOR_PATIENTS = 10;

    static int NUMBER_OF_BEDS = 10;
    static int NUMBER_OF_NURSES = 5;
    static int NUMBER_OF_DOCTORS = 4;
    final static Range PATIENT_ARRIVAL_RANGE = new Range(0, 14);
    final static Range NURSE_RANGE = new Range(20, 40);
    final static Range DOCTOR_RANGE = new Range(180, 240);

    public static int nextId = 0;
    public static int nextBed = 0;
    public static int index = 0;

    public static int runID = 0;
    public static int trials = 0;
    static ArrayList<String> output = new ArrayList<>();

    static Comparator<Patient> priority = (p1, p2) -> p1.priority.ordinal() - p2.priority.ordinal();

    public static void main(String[] args) {

        // init Settings = [#Patients, #Doctors, #Nurses, #Beds, #MAX_Doc, #MAX_NURSE, #re-runs]

        int[][] initSettings = { {160, 12, 18, 5, 10, 5, 10},
                                 {160, 12, 18, 10, 10, 5, 10},
                                 {160, 12, 18, 15, 10, 5, 10},
                                 {160, 12, 18, 20, 10, 5, 10},
                                 {160, 12, 18, 25, 10, 5, 10},
                                 {160, 12, 18, 30, 10, 5, 10},
                                 {160, 12, 18, 35, 10, 5, 10},
                                 {160, 12, 18, 45, 10, 5, 10}};

        for (int i = 0; i < initSettings.length; i++) {
            runID = i;
            int patients = initSettings[i][0];
            int docs = initSettings[i][1];
            int numNurses = initSettings[i][2];
            int numBeds = initSettings[i][3];
            int maxDoc = initSettings[i][4];
            int maxNurse = initSettings[i][5];
            int reRuns = initSettings[i][6];

            runSimulation(patients, docs, numNurses, numBeds, maxDoc, maxNurse, reRuns);
        }

        try {
            File out = new File ("output.csv");
            FileOutputStream fos = new FileOutputStream(out);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            String header = "run id, Patient ID, Patient Priority, Wait Time, Time In, Time out, patients waiting";
            bw.write(header);
            bw.newLine();

            for (int i = 0; i < output.size(); i++) {
                bw.write(output.get(i));
                bw.newLine();
            }

            bw.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Total trials: " + (trials));
        System.out.println("Please refer to the generated output.csv for output data");

    }

    public static ArrayList<String> runSimulation(int numPatients, int numDocs, int numNurse, int numBeds, int maxDoc, int maxNurse,
            int reRuns) {
        System.out.println("Initalize project:");

        int patients = numPatients;
        NUMBER_OF_DOCTORS = numDocs;
        NUMBER_OF_NURSES = numNurse;
        NUMBER_OF_BEDS = numBeds;
        MAX_DOCTOR_PATIENTS = maxDoc;
        MAX_NURSE_PATIENTS = maxNurse;
        
        for (int r = 0; r < reRuns; r++) {
            System.out.println("Running Simulation: ");
            System.out.println("Run: " + r + " Number of patients: " + patients + " Number of beds: " + numBeds);
            trials ++;
            patientOutput = new ArrayList<String>();

            triageQueue = 0;
            interarrivalTime = 0;
            bedsFull = 0;
            time = 0;
            nextId = 0;
            nextBed = 0;
            index = 0;

            waitingRoom = new PriorityQueue<Patient>(Simulator.priority);
            beds = new ArrayList<Bed>();
            fel = new LinkedBlockingQueue<Event>();
            doctors = new ArrayList<Doctor>();
            nurses = new ArrayList<Nurse>();
            arrivals = new ArrayList<Integer>();

            for (int i = 0; i < NUMBER_OF_BEDS; i++) {
                Bed newBed = new Bed();
                beds.add(newBed);
            }

            for (int i = 0; i < NUMBER_OF_DOCTORS; i++) {
                doctors.add(new Doctor());
            }

            for (int i = 0; i < NUMBER_OF_NURSES; i++) {
                nurses.add(new Nurse());
            }

            triageQueue = patients;
            for (int i = 0; i < patients; i++) {
                arrivals.add(PATIENT_ARRIVAL_RANGE.generateRandomInRange());
            }
            arrivals.add(0, 0);

            while (!fel.isEmpty() || !waitingRoom.isEmpty() || time < 250 || bedsFull != 0) {

                if (interarrivalTime == arrivals.get(index)) {
                    if (triageQueue != 0) {
                        interarrivalTime = 0;
                        PatientAssessedEvent event = new PatientAssessedEvent();
                        event.execute();
                        index++;
                        triageQueue--;
                    }
                } else {
                    interarrivalTime += 1;
                }

                if (!fel.isEmpty()) {
                    Event event = fel.remove();
                    event.execute();
                } else {
                    movePatientsForward();
                    Simulator.time += 1;
                }
            }
            for (String s : patientOutput) {
                s = runID + "_" + r + "_" + numBeds + "," + s;
                output.add(s);
            }
            output.add("");
            System.out.println("Run " + r + " complete");
        }
        return patientOutput;
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
            if (!patient.priority.equals(Priority.BLACK)) {
                patient.waitRoomTime = (Simulator.time - patient.timeIn);
                PatientAssignedBedEvent event = new PatientAssignedBedEvent();
                event.setPatient(patient);
                fel.add(event);
            } //else {
                //patient.timeOut = patient.timeIn;
            //}

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
                        case WAITING_FOR_NURSE:
                            if (!nurses.isEmpty()) {
                                for (Nurse n : nurses) {
                                    if (n.canTakePatients()) {
                                        NurseAssignedEvent nurseAssigned = new NurseAssignedEvent(patient, n);
                                        fel.add(nurseAssigned);
                                        break;
                                    }
                                }
                            }
                            break;
                        case NURSE_EVAL:
                            NurseLeavesEvent nurseLeaving = new NurseLeavesEvent(patient);
                            fel.add(nurseLeaving);
                            break;
                        case WAITING_FOR_DOCTOR:
                            if (!doctors.isEmpty()) {
                                for (Doctor d : doctors) {
                                    if (d.canTakePatients()) {
                                        DoctorExamEvent event = new DoctorExamEvent(patient, d, bed);
                                        fel.add(event);
                                        break;
                                    }
                                }
                            }
                            break;
                        case DOCTOR_EVAL:
                            PatientLeavesEvent leavingEvent = new PatientLeavesEvent(bed, patient);
                            fel.add(leavingEvent);
                            patient.setTimeOut(Simulator.time);
                            patientOutput.add(patient.toString());
                            break;
                        default:
                            System.out.println("Status is incorrect: " + patient.status.toString());
                    }
                }
            }
        }
    }
}
