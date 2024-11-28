package Processes;
public class PriorityProcess extends Process {
    private int priority;

    public PriorityProcess(String name, int arrivalTime, int burstTime, int priority) {
        super(name, arrivalTime, burstTime);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
