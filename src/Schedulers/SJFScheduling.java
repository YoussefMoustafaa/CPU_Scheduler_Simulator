package Schedulers;

import Processes.Process;
import java.util.List;
import java.util.ArrayList;

public class SJFScheduling implements SchedulingStrategy {

    @Override
    public void schedule(List<Process> processes) 
    {
        List<Process> completedProcesses = new ArrayList<>();
        int currentTime = 0;
        int totalProcesses = processes.size();

        while (completedProcesses.size() < totalProcesses)
         {
            Process shortestJob = null;

            for (Process process : processes) {
                if (!process.isCompleted() && process.getArrivalTime() <= currentTime) 
                {
                    if (shortestJob == null || process.getBurstTime() < shortestJob.getBurstTime())
                    {
                        shortestJob = process;
                    }
                }
            }

            if (shortestJob != null) 
            {
                System.out.println("Executing Process: " + shortestJob.getName());
                currentTime += shortestJob.getBurstTime();
                shortestJob.setCompleted(true);
                completedProcesses.add(shortestJob);
            } 
            else
            {
                currentTime++; 
            }
        }

   
        System.out.println("All processes are done ya zmeely.");
    }
}
