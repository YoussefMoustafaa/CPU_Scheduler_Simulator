package Schedulers;

import Processes.Process;
import java.util.List;
import java.util.ArrayList;

public class SJFScheduling extends SchedulingStrategy {
    @Override
    public void schedule(List<Process> processes)
     {
        List<Process> completedProcesses = new ArrayList<>();
        int currentTime = 0;
        int totalProcesses = processes.size();
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;



        while (completedProcesses.size() < totalProcesses)
         {
            Process shortestJob = null;

            for (Process process : processes) 
            {
                if (!process.isCompleted() && process.getArrivalTime() <= currentTime)
                 {
                    // Calculate effective burst time considering starvation factor
                    int effectiveBurstTime = process.getBurstTime() - process.getStarvationFactor();

                    if (shortestJob == null || effectiveBurstTime < (shortestJob.getBurstTime() - shortestJob.getStarvationFactor())) 
                    {
                        shortestJob = process;
                    }
                }
            }



            if (shortestJob != null) 
            {
                int startTime = currentTime;
                int endTime = startTime + shortestJob.getBurstTime();
                currentTime = endTime;

                int waitTime = startTime - shortestJob.getArrivalTime();
                int turnaroundTime = endTime - shortestJob.getArrivalTime();
                shortestJob.setWaitTime(waitTime);
                shortestJob.setTurnaroundTime(turnaroundTime);

                totalWaitTime += waitTime;
                totalTurnaroundTime += turnaroundTime;

                shortestJob.setCompleted(true);
                completedProcesses.add(shortestJob);

                System.out.println(shortestJob.getName() + " executes from " + startTime + " to " + endTime
                        + ", wait time: " + waitTime + ", turnaround time: " + turnaroundTime);
                shortestJob.addTime(startTime);
                shortestJob.addTime(endTime);
            }
            
            else 
            {
                currentTime++;
            }

            // To control the starvation :
            // Increment starvation factor for all waiting processes
            for (Process process : processes) 
            {
                if (!process.isCompleted() && process.getArrivalTime() <= currentTime)
                {
                    process.incrementStarvationFactor();
                }
            }
        }
        

        double avgWaitTime = (double) totalWaitTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.printf("Average wait time = %.1f\n", avgWaitTime);
        System.out.printf("Average turnaround time = %.2f\n", avgTurnaroundTime);

        setAverageTurnaroundTime(avgTurnaroundTime);
        setAverageWaitTime(avgWaitTime);
    }
}

