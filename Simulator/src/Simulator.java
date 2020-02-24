
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Scanner;


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
    final static Range PATIENT_ARRIVAL_RANGE = new Range(0, 46);
    final static Range NURSE_RANGE = new Range(20, 40);
    final static Range DOCTOR_RANGE = new Range(180, 240);

    public static int nextId = 0;
    public static int nextBed = 0;
    public static int index = 0;

    static Comparator<Patient> priority = (p1, p2) -> p1.priority.ordinal() - p2.priority.ordinal();

    public static void main(String[] args) {

        // Scanner sc = new Scanner(System.in);

        // generate new patient
        // make sure to print patient information
        // int patients = 0;

        // System.out.println("Enter the number of patients to generate");
        // String generatePatient = sc.nextLine();
        // try {
        // patients = Integer.parseInt(generatePatient);
        // if (patients < 0) {
        // sc.close();
        // throw new NumberFormatException();
        // }
        // System.out.println("Generating " + generatePatient + " patients");
        // } catch (NumberFormatException e) {
        // System.out.println("Not a valid input.");
        // System.exit(0);
        // }
        // sc.close();

        // init array = [#Patients, #Doctors, #Nurses, #Beds, #MAX_Doc, #MAX_NURSE,
        // #re-runs]

        int[][] initSettings = { { 30, 10, 10, 10, 10, 10, 10 } };

        for (int i = 0; i < initSettings.length; i++) {
            int patients = initSettings[i][0];
            int docs = initSettings[i][1];
            int numNurses = initSettings[i][2];
            int numBeds = initSettings[i][3];
            int maxDoc = initSettings[i][4];
            int maxNurse = initSettings[i][5];
            int reRuns = initSettings[i][6];

            runSimulation(patients, docs, numNurses, numBeds, maxDoc, maxNurse, reRuns);
        }
    }

    public static void runSimulation(int numPatients, int numDocs, int numNurse, int numBeds, int maxDoc, int maxNurse,
            int reRuns) {
        System.out.println("Initial project");

        int patients = numPatients;
        NUMBER_OF_DOCTORS = numDocs;
        NUMBER_OF_NURSES = numNurse;
        NUMBER_OF_BEDS = numBeds;
        MAX_DOCTOR_PATIENTS = maxDoc;
        MAX_NURSE_PATIENTS = maxNurse;

        /*

        public static ArrayList<String> patientOutput;

        */
        
        for (int r = 0; r < reRuns; r++) {
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
                //System.out.println("Created bed, ID: " + newBed.toString());
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

            System.out.println("Assume time in minutes:");
            while (!fel.isEmpty() || !waitingRoom.isEmpty() || time < 250 || bedsFull != 0) {
                //System.out.println(Simulator.time);

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
                    //System.out.println("Executing event");
                    event.execute();
                } else {
                    movePatientsForward();
                    Simulator.time += 1;
                }
            }
            //System.out.println(patientOutput);
            for (int i = 0; i < patientOutput.size(); i++) {
                System.out.println(patientOutput.get(i));
            }

            System.out.println("\n");
        }

        try {
            //write patientOutput to csv.
        } catch (Exception e) {
            e.printStackTrace();
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
            PatientAssignedBedEvent event = new PatientAssignedBedEvent();
            event.setPatient(patient);
            fel.add(event);
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
                        //case WAITING:
                        //    patient.setWaitTime(patient.getWaitTime() + 1);
                        case WAITING_FOR_NURSE:
                            if (!nurses.isEmpty()) {
                                // as soon as there is a nurse that can take patients assign
                                // this patient to the nurse, else keep patient in waiting state.
                                for (Nurse n : nurses) {
                                    if (n.canTakePatients()) {
                                        NurseAssignedEvent nurseAssigned = new NurseAssignedEvent(patient, n);
                                        fel.add(nurseAssigned);
                                        //System.out.println("Nurse assigned: " + n + " patients: " + n.getNumPatients() + "/" + MAX_NURSE_PATIENTS);
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
                                        //System.out.println("Doc assigned: " + d + " patients: " + d.getNumPatients() + "/" + MAX_DOCTOR_PATIENTS);
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

    /*
     * //TODO -- Needs implementation
     * Generates a random assessment time for the patient
     * currently in the triage queue
     */

}
