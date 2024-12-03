package Schedulers;

import Processes.Process;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduling implements SchedulingStrategy {
    @Override
    public void schedule(List<Process> processes) {
        int currentTime = 0;
        int totalProcesses = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int completed = 0;

        // Define context switch time directly
        int contextSwitchTime = 1;

        List<Process> executionOrder = new ArrayList<>();
        
        while (completed < totalProcesses) {
            Process selectedProcess = null;

            // Find process with the shortest remaining time available at currentTime
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0) {
                    // Select the process with the shortest remaining time
                    if (selectedProcess == null || process.getRemainingTime() < selectedProcess.getRemainingTime()) {
                        selectedProcess = process;
                    }
                    // Handle tie: Choose process with earlier arrival time if remaining times are equal
                    else if (process.getRemainingTime() == selectedProcess.getRemainingTime() &&
                             process.getArrivalTime() < selectedProcess.getArrivalTime()) {
                        selectedProcess = process;
                    }
                }
            }

            // If no process is ready, increment time
            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            // Add context switching time if switching between processes
            if (!executionOrder.isEmpty() && executionOrder.get(executionOrder.size() - 1) != selectedProcess) {
                currentTime += contextSwitchTime;
            }

            // Execute the selected process for 1 unit of time
            executionOrder.add(selectedProcess);
            selectedProcess.DecreaseRemainingTime(1);
            currentTime++;

            // Check if process is completed
            if (selectedProcess.getRemainingTime() == 0) {
                completed++;
                int waitingTime = currentTime - selectedProcess.getArrivalTime() - selectedProcess.getBurstTime();
                int turnaroundTime = currentTime - selectedProcess.getArrivalTime();

                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;

                System.out.println("Process " + selectedProcess.getName() + " completed:");
                System.out.println("Waiting Time: " + waitingTime);
                System.out.println("Turnaround Time: " + turnaroundTime);
            }
        }
    }
}
