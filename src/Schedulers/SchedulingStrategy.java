package Schedulers;
import Processes.Process;
import java.util.List;

public abstract class SchedulingStrategy  {
    protected double averageWaitTime;
    protected double averageTurnaroundTime;
    
    public abstract void schedule(List<Process> processes);
    
    public double getAverageWaitTime() {
        return averageWaitTime;
    }
    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }
    public void setAverageWaitTime(double avgWaitTime) {
        this.averageWaitTime = avgWaitTime;
    }
    public void setAverageTurnaroundTime(double avgTurnaroundTime) {
        this.averageTurnaroundTime = avgTurnaroundTime;
    }
}
