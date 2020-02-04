package people;

import types.Priority;
import types.Status;

public class Patient {
    public Priority priority;
    public Status status;

    public double waitTime;

    public Patient(Priority p, Status s){
        waitTime = 0;
        status = s;
        priority = p;
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
