package Processes;
public class FCAIProcess extends Process {
    private int priority;
    private int quantum;
    private int FCAIScore;

    public FCAIProcess(String name, int arrivalTime, int burstTime, int priority, int quantum, int FCAIScore) {
        super(name, arrivalTime, burstTime);
        this.priority = priority;
        this.quantum = quantum;
        this.FCAIScore = FCAIScore;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getFCAIScore() {
        return FCAIScore;
    }

    public void calculateFCAIScore(double v1, double v2) {
        this.FCAIScore = (10 - priority) + (int) Math.ceil(arrivalTime / v1) + (int) Math.ceil(burstTime / v2);
    }
}
