package Processes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Process {
    protected String name;
    protected Color color;
    protected int arrivalTime;
    protected int burstTime;
    protected int remainingTime;
    protected boolean completed;
    protected int waitTime;
    protected int turnaroundTime;
    protected int starvationFactor; //to solve starvation problem
    protected List<Integer> startTimes;

    public Process(String name, int arrivalTime, int burstTime)
     {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitTime = 0;
        this.turnaroundTime = 0;
        this.starvationFactor = 0; 
        this.color = null;
        this.startTimes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
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

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStarvationFactor() 
    {
        return starvationFactor;
    }

    public void incrementStarvationFactor() 
    {
        this.starvationFactor++;
    }

    public void addTime(int time) {
        startTimes.add(time);
    }

    public void setStartTimes(List<Integer> startTimes) {
        this.startTimes = startTimes;
    }

    public List<Integer> getStartTimes() {
        return startTimes;
    }

    public void clearStartTimes() {
        this.startTimes.clear();
    }

    public void setStarvationFactor(int starvationFactor) {
        this.starvationFactor = starvationFactor;
    }

}
