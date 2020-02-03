import events.Event;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

import people.*;
import types.*;

public class Main {

    public static LinkedBlockingQueue<Patient> triage;
    public static PriorityQueue<Patient> waitingRoom;
    public static ArrayList<Room> rooms;
    public static ArrayList<Room> emptyRooms;
    private static LinkedBlockingQueue<Event> fel;

    final static int NUMBER_OF_TREATMENT_ROOMS = 10;
    final static int NUMBER_OF_XRAY_ROOMS = 2;
    final static int NUMBER_OF_LABS = 3;

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
        rooms = new ArrayList<Room>();
        emptyRooms = new ArrayList<Room>();
        fel = new LinkedBlockingQueue<>();

        for (int i = 0; i < NUMBER_OF_TREATMENT_ROOMS; i++) {
            emptyRooms.add(new Room(RoomType.TREATMENT));
        }

        for (int i = 0; i < NUMBER_OF_XRAY_ROOMS; i++) {
            emptyRooms.add(new Room(RoomType.XRAY));
        }

        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            emptyRooms.add(new Room(RoomType.LAB_TEST));
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

        for (Room room: rooms) {
            switch (room.getRoomType()) {
                case TREATMENT:
                    break;
                case LAB_TEST:
                    break;
                case XRAY:
                    break;
                default:
                    throw new EnumConstantNotPresentException(RoomType.class, room.getRoomType().toString());
            }
        }
    }
}
