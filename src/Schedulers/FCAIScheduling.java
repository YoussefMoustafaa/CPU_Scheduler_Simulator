package Schedulers;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    @Override
    public void schedule(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        Queue<FCAIProcess> arrivedQueue = new LinkedList<>();
        List<FCAIProcess> completedQueue = new ArrayList<>();
        int currentTime = 0;

        FCAIProcess currentProcess = (FCAIProcess) processes.get(0);

        int quantumTime = currentProcess.getQuantum();
        int preemptionThreshold = (int) Math.ceil(quantumTime * 0.4) + currentTime;
        int executedTime = 0;


        while (completedQueue.size() < processes.size()){
            if(arrivedQueue.isEmpty()){
                executedTime++;
                currentTime++;
                continue;
            }
            if(currentTime < preemptionThreshold){
                int temp = preemptionThreshold - currentTime;
                executedTime+= temp;
                currentTime +=temp;
                continue;
            }
            else{
                while(executedTime < quantumTime) {
                    for (Process p : processes) {
                        if (p instanceof FCAIProcess && p.getArrivalTime() <= currentTime && !arrivedQueue.contains(p) && !p.isCompleted()) {
                            arrivedQueue.add((FCAIProcess) p);
                        }
                    }
                    FCAIProcess preemptingProcess = getProcessWithSmallestFCAIScore(arrivedQueue, currentTime);
                    if (preemptingProcess != null && preemptingProcess.getFCAIScore() < currentProcess.getFCAIScore()) {
                        currentProcess.DecreaseRemainingTime(executedTime);
                        currentProcess.setQuantum(currentProcess.getQuantum() + (quantumTime - executedTime));
                        arrivedQueue.add(currentProcess);
                        

                        currentProcess = preemptingProcess;

                        quantumTime = currentProcess.getQuantum();
                        preemptionThreshold = (int) Math.ceil(quantumTime * 0.4)+ currentTime;
                        executedTime = 0;
                        currentTime++;
                        break;
                        
                    }

                    if(executedTime == quantumTime){
                        currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                        arrivedQueue.add(currentProcess);
                        currentProcess = arrivedQueue.poll();

                        quantumTime = currentProcess.getQuantum();
                        preemptionThreshold = (int) Math.ceil(quantumTime * 0.4)+ currentTime;
                        executedTime = 0;
                        currentTime++;
                        break;
                    }
                    currentTime++;
                    executedTime++;
                }
            }

        }
    }
    
}
