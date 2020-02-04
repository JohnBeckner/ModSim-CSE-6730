import events.Event;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Random;

import people.*;
import types.Status;

public class Main {

    public static PriorityQueue<Patient> waitingRoom;
    public static ArrayList<Bed> beds;
    private static LinkedBlockingQueue<Event> fel;

    public static int bedsFull = 0;

    final static int NUMBER_OF_BEDS = 10;
    final static Range NURSE_RANGE = new Range(20, 40);
    final static Range DOCTOR_RANGE = new Range(180, 240);

    static Comparator<Patient> priority = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.priority.ordinal() - p2.priority.ordinal();
        }
    };

    public static void main(String[] args) {
        System.out.println("Initial project");

        waitingRoom = new PriorityQueue<>(priority);
        beds = new ArrayList<Bed>();
        fel = new LinkedBlockingQueue<>();

        for (int i = 0; i < NUMBER_OF_BEDS; i++) {
            beds.add(new Bed());
        }

        //Populate initial events somehow
        while (!fel.isEmpty()) {
            Event event = fel.remove();
            event.execute();
            movePatientsForward();
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

        if (Main.bedsFull < NUMBER_OF_BEDS) {
            Patient patient = waitingRoom.remove();
            for (Bed bed: beds) {
                if (!bed.isFull()) {
                    bed.addPatient(patient);
                    bed.setMedicalProfessional(new Nurse());
                }
            }
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
                            patient.status = Status.DOCTOR_EVAL;
                            bed.setMedicalProfessional(new Doctor());
                            System.out.println("Patient evaluated by nurse");
                            break;
                        case DOCTOR_EVAL:
                            patient.status = Status.TREATED;
                            bed.setMedicalProfessional(null);
                            System.out.println("Patient evaluated by doctor");
                            break;
                        case TREATED:
                            bed.removePatient();
                            System.out.println("Patient has been treated");
                            break;
                        default:
                            System.out.println("Status is incorrect: " + patient.status.toString());
                    }
                }
            }
        }
    }
}
