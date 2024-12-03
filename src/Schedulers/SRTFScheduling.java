package Schedulers;

import contextSwitch.ContextSwitch;
import Processes.Process;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling implements SchedulingStrategy {
    @Override
    public void schedule(List<Process> processes) {
        List<Process> readyQueue = new ArrayList<>();
        List<String> execution = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int totalProcesses = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        Process previousProcess = null;

        while (completed < totalProcesses) {
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !readyQueue.contains(process) && !process.isCompleted()) {
                    readyQueue.add(process);
                }
            }
            
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process shortestJob = readyQueue.get(0);
            for (Process process : readyQueue) {
                if (process.getRemainingTime() < shortestJob.getRemainingTime()) {
                    shortestJob = process;
                }
            }

            // Add context switch time if switching processes
            if (previousProcess != null && !previousProcess.equals(shortestJob)) {
                currentTime += ContextSwitch.contextSwitchTime;
                execution.add("Context switch at time " + (currentTime - ContextSwitch.contextSwitchTime));
            }

            // Execute the shortest job for 1 unit of time
            int startTime = currentTime;
            shortestJob.DecreaseRemainingTime(1);
            currentTime++;
            previousProcess = shortestJob;

            // Log execution
            execution.add("Process " + shortestJob.getName() + " executed from " + startTime + " to " + currentTime);

            // Check if process is completed
            if (shortestJob.getRemainingTime() == 0) {
                shortestJob.setCompleted(true);
                readyQueue.remove(shortestJob);
                completed++;

                // Calculate waiting and turnaround times
                int waitingTime = currentTime - shortestJob.getArrivalTime() - shortestJob.getBurstTime();
                int turnaroundTime = currentTime - shortestJob.getArrivalTime();

                shortestJob.setWaitTime(waitingTime);
                shortestJob.setTurnaroundTime(turnaroundTime);

                // Aggregate totals
                totalWaitingTime += shortestJob.getWaitTime();
                totalTurnaroundTime += shortestJob.getTurnaroundTime();
            }
        }

        // Output results
        System.out.println("Execution Log:");
        for (String logged : execution) {
            System.out.println(logged);
        }

        System.out.println("\nProcess Results:");
        for (Process process : processes) {
            System.out.println("Process " + process.getName() +
                               ": Waiting Time = " + process.getWaitTime() +
                               ", Turnaround Time = " + process.getTurnaroundTime());
        }

        // Output averages
        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
