package Schedulers;
import java.util.List;

import Processes.Process;

public interface SchedulingStrategy  {
    void schedule(List<Process> processes);
}
