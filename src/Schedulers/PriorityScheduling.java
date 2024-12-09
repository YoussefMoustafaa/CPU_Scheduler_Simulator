package Schedulers;
import Processes.PriorityProcess;
import Processes.Process;
import contextSwitch.ContextSwitch;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PriorityScheduling extends SchedulingStrategy {
    int context = ContextSwitch.contextSwitchTime;
    List<PriorityProcess> remainingProcesses = new ArrayList<>();   
    @Override
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        int totalTurnaroundTime=0;
        int totalWaitTime=0;

        processes.sort(Comparator.comparingInt(Process::getArrivalTime)
                .thenComparingInt(p -> ((PriorityProcess) p).getPriority()));

        for (int i =0; i<processes.size() ;i++) {
            PriorityProcess pp = (PriorityProcess) processes.get(i);
            if(i==0){
                currentTime = Math.max(currentTime, pp.getArrivalTime());
                pp.setWaitTime(currentTime - pp.getArrivalTime());
                currentTime+=pp.getBurstTime();
                // current time become a completetion time
                pp.setTurnaroundTime(currentTime-pp.getArrivalTime());
                System.out.println(pp.getName() + " executes from " + (currentTime-pp.getBurstTime()) + " to " + currentTime);
                pp.addTime(currentTime-pp.getBurstTime());
                pp.addTime(currentTime);
                currentTime+=context;
                totalTurnaroundTime += pp.getTurnaroundTime();
                totalWaitTime += pp.getWaitTime();
            }
            else {
                remainingProcesses.add(pp);
            }   
        }
        remainingProcesses.sort(
            Comparator.comparingInt(PriorityProcess::getPriority)
                      .thenComparingInt(PriorityProcess::getArrivalTime)
        );

        for (PriorityProcess p : remainingProcesses) {
            currentTime = Math.max(currentTime, p.getArrivalTime());
            p.setWaitTime(currentTime - p.getArrivalTime());
            // current time become a completetion time
            currentTime += p.getBurstTime(); 
            p.setTurnaroundTime(currentTime - p.getArrivalTime());
            System.out.println(p.getName() + " executes from " + (currentTime - p.getBurstTime()) + " to " + currentTime);
            p.addTime(currentTime - p.getBurstTime());
            p.addTime(currentTime);
            currentTime+=+context;
            totalTurnaroundTime += p.getTurnaroundTime();
            totalWaitTime += p.getWaitTime();
        }

        System.out.println("\n");
        for (Process p : processes) {
                PriorityProcess pp = (PriorityProcess) p;
                System.out.println(pp.getName() + ": wait time: " + pp.getWaitTime() + ", turnaround time: " + pp.getTurnaroundTime());
        }
        int totalProcesses = processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;
        double avgWaitTime = (double) totalWaitTime / totalProcesses;

        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Wait Time: " + avgWaitTime);

        setAverageTurnaroundTime(avgTurnaroundTime);
        setAverageWaitTime(avgWaitTime);
    }
}