package events;

public abstract class Event {
    public double timeStamp;
    public abstract void execute();
}
