package Schedulers;
import Processes.PriorityProcess;
import Processes.Process;
import contextSwitch.ContextSwitch;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduling implements SchedulingStrategy {
    int context = ContextSwitch.contextSwitchTime;
    List<PriorityProcess> remainingProcesses = new ArrayList<>();   
    @Override
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        for (Process p : processes) {
            if(p instanceof PriorityProcess){
                PriorityProcess pp = (PriorityProcess) p;
                if(p.getArrivalTime() == 0){
                    if (currentTime < p.getArrivalTime()) {
                        currentTime = p.getArrivalTime();
                    }
                    System.out.println("Executing process: " + pp.getName() +
                        " [Priority: " + pp.getPriority() +
                        ", Arrival Time: " + pp.getArrivalTime() +
                        ", Burst Time: " + pp.getBurstTime() + "]");
                        currentTime+=p.getBurstTime()+context;
            System.out.println("Process " + p.getName() + " completed at time " + currentTime);
                }
                else {
                    if (pp.getArrivalTime() > 0) {
                        remainingProcesses.add(pp);
                    }
                }
            }
        }

        remainingProcesses.sort(
            Comparator.comparingInt(PriorityProcess::getPriority)
                      .thenComparingInt(PriorityProcess::getArrivalTime)
        );

        

        for (PriorityProcess p : remainingProcesses) {
        
            System.out.println("Executing process: " + p.getName() +
            " [Priority: " + p.getPriority() +
            ", Arrival Time: " + p.getArrivalTime() +
            ", Burst Time: " + p.getBurstTime() + "]");
            currentTime+=p.getBurstTime()+context;
            System.out.println("Process " + p.getName() + " completed at time " + currentTime);
        }
    }
}
