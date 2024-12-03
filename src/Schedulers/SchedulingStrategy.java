package Schedulers;
import Processes.Process;
import java.util.List;

public interface SchedulingStrategy  {
    void schedule(List<Process> processes);
}
