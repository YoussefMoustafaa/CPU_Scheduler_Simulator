package Processes;

public class Process {
    protected String name;
    protected int arrivalTime;
    protected int burstTime;
    protected int remainingTime;
    protected boolean completed;
    protected int waitTime;
    protected int turnaroundTime;
    protected int starvationFactor; //to solve starvation problem

    public Process(String name, int arrivalTime, int burstTime)
     {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitTime = 0;
        this.turnaroundTime = 0;
        this.starvationFactor = 0; 
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getStarvationFactor() 
    {
        return starvationFactor;
    }

    public void incrementStarvationFactor() 
    {
        this.starvationFactor++;
    }
}
