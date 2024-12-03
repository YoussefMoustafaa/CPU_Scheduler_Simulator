
package Schedulers;

import Processes.Process;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling implements SchedulingStrategy {
    @Override
    public void schedule(List<Process> processes) 
    {
        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int totalProcesses = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int completed = 0;

        // Define context switch time directly
        int contextSwitchTime = 1;

        while (completed < totalProcesses) {
            // Add processes that have arrived to the ready queue
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && !process.isCompleted()) {
                    readyQueue.add(process);
                }
            }

            // If no process is ready, increment time
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Find process with the shortest remaining time in the ready queue
            Process shortestJob = readyQueue.get(0);
            for (Process process : readyQueue) {
                if (process.getRemainingTime() < shortestJob.getRemainingTime()) {
                    shortestJob = process;
                }
            }

            // Remove the shortest job from the ready queue
            readyQueue.remove(shortestJob);

            // Add context switching time if switching between processes
            if (completed > 0) {
                currentTime += contextSwitchTime;
            }

            // Execute the selected process for 1 unit of time
            shortestJob.DecreaseRemainingTime(1);
            currentTime++;

            // Check if process is completed
            if (shortestJob.getRemainingTime() == 0) {
                completed++;
                int waitingTime = currentTime - shortestJob.getArrivalTime() - shortestJob.getBurstTime();
                int turnaroundTime = currentTime - shortestJob.getArrivalTime();

                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;

                System.out.println("Process " + shortestJob.getName() + " completed:");
                System.out.println("Waiting Time: " + waitingTime);
                System.out.println("Turnaround Time: " + turnaroundTime);
            } else {
                // If the process is not completed, add it back to the ready queue
                readyQueue.add(shortestJob);
            }
        }
    }
}
