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
        int totalTurnaroundTime=0;
        int totalWaitTime=0;
        for (Process p : processes) {
            if(p instanceof PriorityProcess){
                PriorityProcess pp = (PriorityProcess) p;
                if(p.getArrivalTime() == 0){
                    if (currentTime < p.getArrivalTime()) {
                        currentTime = p.getArrivalTime();
                    }
                    pp.setWaitTime(currentTime - pp.getArrivalTime());
                    currentTime+=p.getBurstTime();
                    // current time become a completetion time
                    pp.setTurnaroundTime(currentTime-pp.getArrivalTime());

                     System.out.println(p.getName() + " executes from " + currentTime + " to " + (currentTime + p.getBurstTime()) );


                    currentTime+=context;
                    totalTurnaroundTime += pp.getTurnaroundTime();
                    totalWaitTime += pp.getWaitTime();
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
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }

            p.setWaitTime(currentTime - p.getArrivalTime());
            // current time become a completetion time
            currentTime += p.getBurstTime(); 
            p.setTurnaroundTime(currentTime - p.getArrivalTime());
            System.out.println(p.getName() + " executes from " + currentTime + " to " + (currentTime + p.getBurstTime()));

            currentTime+=+context;
            totalTurnaroundTime += p.getTurnaroundTime();
            totalWaitTime += p.getWaitTime();
        }

        System.out.println("\n");
        for (Process p : processes) {
            if (p instanceof PriorityProcess) {
                PriorityProcess pp = (PriorityProcess) p;
                System.out.println(pp.getName() + ": wait time: " + pp.getWaitTime() + ", turnaround time: " + pp.getTurnaroundTime());
            }
        }
        int totalProcesses = processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;
        double avgWaitTime = (double) totalWaitTime / totalProcesses;

        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Wait Time: " + avgWaitTime);
    }
}
