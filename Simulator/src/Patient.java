

public class Patient {
    public Priority priority;
    public Status status;
    public Bed bed;
    public int patientNumber;
    public int timeIn;
    public int timeOut;
    public double waitRoomTime;

    public double waitTime;

    public Patient(Priority p){
        priority = p;
        status = Status.WAITING_FOR_ROOM;
        waitTime = 0;
        waitRoomTime = 0;
        timeIn = Simulator.time;
        timeOut = -1;
        this.patientNumber = Simulator.nextId;
        Simulator.nextId++;
    }

    public Priority getPriority(){
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public int getTimeIn() {
        return timeIn;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public Priority setPriority(Priority p){
        priority = p;
        return priority;
    }

    public Status setStatus(Status s) {
        status = s;
        return status;
    }

    public double setWaitTime(double t) {
        waitTime = t;
        return waitTime;
    }

    public void setTimeOut(int time) {
        timeOut = time;
    }

    public String toString() {
        return "" + patientNumber + "," 
        + priority + ","
        + " Wait time: " + waitRoomTime + ","
        + timeIn + ","
        + timeOut + "," 
        + Simulator.waitingRoom.size();
    }
}
