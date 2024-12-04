package Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

import Processes.FCAIProcess;
import Processes.Process;

public class FCAIScheduling implements SchedulingStrategy {

    
    private FCAIProcess getProcessWithSmallestFCAIScore(Queue<FCAIProcess> readyQueue, int currentTime) {
        // Find the process with the smallest FCAI score among those that have arrived by currentTime
        return readyQueue.stream()
            .filter(p -> p.getArrivalTime() <= currentTime && !p.isCompleted())
            .min(Comparator.comparingInt(FCAIProcess::getFCAIScore))
            .orElse(null);  // If no process is found, return null
    }

    private void printDetails(FCAIProcess p, int executedTime) {
        System.out.println(p.getName() + " " + executedTime + " " + p.getBurstTime() + " " + p.getQuantum());
    }
    @Override
    public void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)); // Sort by arrival time
        Queue<FCAIProcess> arrivedQueue = new LinkedList<>();
        List<FCAIProcess> completedQueue = new ArrayList<>();
        int currentTime = 0;
        int processeSize = processes.size();
        int lastArrivalTime = processes.stream()
                .mapToInt(Process::getArrivalTime)
                .max()
                .orElse(0);
        int maxBurstTime = processes.stream()
                .mapToInt(Process::getBurstTime)
                .max()
                .orElse(0);

        double V1 = lastArrivalTime / 10.0;
        double V2 = maxBurstTime / 10.0;
        // Start with the first process
        FCAIProcess currentProcess = (FCAIProcess) processes.get(0);
        final String targetName = currentProcess.getName();
        processes.removeIf(process -> process.getName().equals(targetName));
        
        int quantumTime = currentProcess.getQuantum();
        int burstTime = currentProcess.getBurstTime();
        int preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
        int executedTime = 0;
        
        while (completedQueue.size() < processeSize ) {
            // Add processes that have arrived
            Iterator<Process> iterator = processes.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p instanceof FCAIProcess && p.getArrivalTime() <= currentTime && !p.isCompleted()) {
                    arrivedQueue.add((FCAIProcess) p);
                    iterator.remove(); // Safely remove the process
                }
            }

            // If burst time reaches 0, mark the process as completed
            if (burstTime == 0) {
                currentProcess.DecreaseRemainingTime(executedTime);
                currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                currentProcess.setBurstTime(burstTime);
                currentProcess.calculateFCAIScore(V1, V2);
                printDetails(currentProcess, executedTime);
                completedQueue.add(currentProcess);
                currentProcess.setCompleted(true);

                currentProcess = arrivedQueue.poll(); // Get the next process
                quantumTime = currentProcess.getQuantum();
                burstTime = currentProcess.getBurstTime();
                preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                executedTime = 0;
                currentTime++;
                executedTime++;
                burstTime--;
                continue;
            }
            if(executedTime == quantumTime){
                currentProcess.DecreaseRemainingTime(executedTime);
                currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                currentProcess.setBurstTime(burstTime);
                currentProcess.calculateFCAIScore(V1, V2);
                arrivedQueue.add(currentProcess);
                printDetails(currentProcess, executedTime);
                currentProcess = arrivedQueue.poll();
                quantumTime = currentProcess.getQuantum();
                burstTime = currentProcess.getBurstTime();
                preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                executedTime = 0;
                currentTime++;
                executedTime++;
                burstTime--;
                continue;
            }

            // Handle preemption or continue execution
            if (!arrivedQueue.isEmpty() && currentTime >= preemptionThreshold) {
                FCAIProcess preemptingProcess = getProcessWithSmallestFCAIScore(arrivedQueue, currentTime);
                if (preemptingProcess != null && preemptingProcess.getFCAIScore() < currentProcess.getFCAIScore()) {
                    currentProcess.DecreaseRemainingTime(executedTime);
                    currentProcess.setQuantum(currentProcess.getQuantum() + (quantumTime - executedTime));
                    currentProcess.setBurstTime(burstTime);
                    currentProcess.calculateFCAIScore(V1, V2);
                    arrivedQueue.add(currentProcess);
                    printDetails(currentProcess, executedTime);
                    arrivedQueue.remove(preemptingProcess);
                    currentProcess = preemptingProcess;
                    quantumTime = currentProcess.getQuantum();
                    burstTime = currentProcess.getBurstTime();
                    preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                    executedTime = 0;
                    currentTime++;
                    executedTime++;
                    burstTime--;
                    continue;
                }
            }


            // Continue current process execution
            currentTime++;
            executedTime++;
            burstTime--;
        }
    }
}
