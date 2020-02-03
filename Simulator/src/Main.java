import java.util.concurrent.LinkedBlockingQueue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {

    static LinkedBlockingQueue<Patient> triage;
    static PriorityQueue<Patient> waitingRoom;
    static LinkedList<Room> rooms;

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
        rooms = new LinkedList<Room>();

        for (int i = 0; i < NUMBER_OF_TREATMENT_ROOMS; i++) {
            rooms.add(new Room(RoomType.TREATMENT));
        }

        for (int i = 0; i < NUMBER_OF_XRAY_ROOMS; i++) {
            rooms.add(new Room(RoomType.XRAY));
        }

        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            rooms.add(new Room(RoomType.LAB_TEST));
        }
    }
}
