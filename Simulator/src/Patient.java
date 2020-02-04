

public class Patient {
    public Priority priority;
    public Status status;
    public Bed bed;

    public double waitTime;

    public Patient(Priority p){
        priority = p;
        status = Status.WAITING;
        waitTime = 0;
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
}
