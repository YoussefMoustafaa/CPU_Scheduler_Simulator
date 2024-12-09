package Schedulers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

import Processes.FCAIProcess;
import Processes.Process;

public class FCAIScheduling extends SchedulingStrategy {

    private FCAIProcess getProcessWithSmallestFCAIScore(Queue<FCAIProcess> readyQueue, int currentTime) {
        // Find the process with the smallest FCAI score among those that have arrived by currentTime
        return readyQueue.stream()
            .filter(p -> p.getArrivalTime() <= currentTime && !p.isCompleted())
            .min(Comparator.comparingInt(FCAIProcess::getFCAIScore))
            .orElse(null);  // If no process is found, return null
    }

    private void printDetails(FCAIProcess p, int executedTime , int starttime) {
        System.out.println("Name : " + p.getName() + ", Time : " +starttime + "->" + (starttime+executedTime) + ", Executed time " + executedTime + ", Remaining Burst Time " + p.getBurstTime() + ", Quantum " + p.getQuantum() + ", FCAI score : " + p.getFCAIScore());
        p.addTime(starttime);
        p.addTime(starttime + executedTime);
    }

    private void printProcessesInfo(List<Process> processes) {
        double averageWaitTime = 0.0;
        double averageTurnaroundTime = 0.0;
        for (Process p : processes) {
            System.out.println("Name : " + p.getName() + ": Wait Time = " + p.getWaitTime() + " - TurnAround Time = " + p.getTurnaroundTime());
            averageWaitTime += p.getWaitTime();
            averageTurnaroundTime += p.getTurnaroundTime();
        }
        averageWaitTime /= processes.size();
        averageTurnaroundTime /= processes.size();
        setAverageWaitTime(averageWaitTime);
        setAverageTurnaroundTime(averageTurnaroundTime);
        System.out.println("Average Wait Time = " + averageWaitTime);
        System.out.println("Average Turnaround Time = " + averageTurnaroundTime);
    }

    @Override
    public void schedule(List<Process> processes) {
        List<Process> FCAIProcessList = new ArrayList<>(processes);
        FCAIProcessList.sort(Comparator.comparingInt(Process::getArrivalTime)); // Sort by arrival time
        Queue<FCAIProcess> arrivedQueue = new LinkedList<>();
        List<FCAIProcess> completedQueue = new ArrayList<>();
        int currentTime = 0;
        int startTime = 0;
        int size = FCAIProcessList.size();
        int lastArrivalTime = FCAIProcessList.stream()
                .mapToInt(Process::getArrivalTime)
                .max()
                .orElse(0);
        int maxBurstTime = FCAIProcessList.stream()
                .mapToInt(Process::getBurstTime)
                .max()
                .orElse(0);

        double V1 = lastArrivalTime / 10.0;
        double V2 = maxBurstTime / 10.0;
        FCAIProcess currentProcess = (FCAIProcess) FCAIProcessList.get(0);
        final String targetName = currentProcess.getName();
        FCAIProcessList.removeIf(process -> process.getName().equals(targetName));
        
        int quantumTime = currentProcess.getQuantum();
        int burstTime = currentProcess.getBurstTime();
        int preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
        int executedTime = 0;

        while (completedQueue.size() < size) {
            Iterator<Process> iterator = FCAIProcessList.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p instanceof FCAIProcess && p.getArrivalTime() <= currentTime && !p.isCompleted()) {
                    arrivedQueue.add((FCAIProcess) p);
                    iterator.remove();
                }
            }
            if (burstTime == 0) {
                int completionTime = currentTime;
                currentProcess.setTurnaroundTime(completionTime - currentProcess.getArrivalTime());
                currentProcess.setWaitTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());

                currentProcess.DecreaseRemainingTime(executedTime);
                currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                currentProcess.setBurstTime(burstTime);
                currentProcess.calculateFCAIScore(V1, V2);
                printDetails(currentProcess, executedTime, startTime);
                completedQueue.add(currentProcess);
                currentProcess.setCompleted(true);



                currentProcess = arrivedQueue.poll();
                if (currentProcess == null) {
                    if (!FCAIProcessList.isEmpty()) {
                        currentTime++;
                        continue;
                    } else {
                        break;
                    }
                }

                startTime = currentTime;
                quantumTime = currentProcess.getQuantum();
                burstTime = currentProcess.getBurstTime();
                preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                executedTime = 0;
                continue;
            }

            if (executedTime == quantumTime) {
                currentProcess.DecreaseRemainingTime(executedTime);
                currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                currentProcess.setBurstTime(burstTime);
                currentProcess.calculateFCAIScore(V1, V2);
                arrivedQueue.add(currentProcess);
                printDetails(currentProcess, executedTime, startTime);

                currentProcess = arrivedQueue.poll();
                if (currentProcess == null) {
                    if (!FCAIProcessList.isEmpty()) {
                        currentTime++;
                        continue;
                    } else {
                        break;
                    }
                }

                startTime = currentTime;
                quantumTime = currentProcess.getQuantum();
                burstTime = currentProcess.getBurstTime();
                preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                executedTime = 0;
                continue;
            }


            if (!arrivedQueue.isEmpty() && currentTime >= preemptionThreshold) {
                FCAIProcess preemptingProcess = getProcessWithSmallestFCAIScore(arrivedQueue, currentTime);
                if (preemptingProcess != null && preemptingProcess.getFCAIScore() < currentProcess.getFCAIScore()) {
                    currentProcess.DecreaseRemainingTime(executedTime);
                    currentProcess.setQuantum(currentProcess.getQuantum() + (quantumTime - executedTime));
                    currentProcess.setBurstTime(burstTime);
                    currentProcess.calculateFCAIScore(V1, V2);
                    arrivedQueue.add(currentProcess);
                    printDetails(currentProcess, executedTime, startTime);
                    arrivedQueue.remove(preemptingProcess);
                    startTime = currentTime;
                    currentProcess = preemptingProcess;
                    quantumTime = currentProcess.getQuantum();
                    burstTime = currentProcess.getBurstTime();
                    preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
                    executedTime = 0;
                    continue;
                }
            }

            
            currentTime++;
            executedTime++;
            burstTime--;
        }
        printProcessesInfo(processes);
    }
}