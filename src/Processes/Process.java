package Processes;
public class Process {
    protected String name;
    // protected String color;
    protected int arrivalTime;
    protected int burstTime;
    protected int remainingTime;
    private boolean completed;


    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public String getName() { 
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int DecreaseRemainingTime(int currentWorkTime) {
        this.remainingTime -= currentWorkTime;
        return remainingTime;
    }

        public boolean isCompleted() 
    { 
        return completed;
    }

    public void setCompleted(boolean completed)
    { 
        this.completed = completed;
    }

}
