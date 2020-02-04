import events.Event;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

import people.*;

public class Main {

    public static LinkedBlockingQueue<Patient> triage;
    public static PriorityQueue<Patient> waitingRoom;
    public static ArrayList<Bed> beds;
    public static ArrayList<Bed> emptyBeds;
    private static LinkedBlockingQueue<Event> fel;

    final static int NUMBER_OF_BEDS = 10;

    static Comparator<Patient> priority = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.priority.ordinal() - p2.priority.ordinal();
        }
    };

    public static void main(String[] args) {
        System.out.println("Initial project");

        triage = new LinkedBlockingQueue<>();
        waitingRoom = new PriorityQueue<>(priority);
        beds = new ArrayList<Bed>();
        emptyBeds = new ArrayList<Bed>();
        fel = new LinkedBlockingQueue<>();

        for (int i = 0; i < NUMBER_OF_BEDS; i++) {
            emptyBeds.add(new Bed());
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
    }
}
