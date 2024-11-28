package Schedulers;
import java.util.List;

import Processes.Process;

public class Scheduler {
    private SchedulingStrategy strategy;

    public void setStrategy(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeSchedule(List<Process> processes) {
        strategy.schedule(processes);
    }
}
