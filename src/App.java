import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import Processes.Process;
import Schedulers.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        // Process p1 = new Process("P1", 0, 6); // name Arrival-Time Burst-Time
        // Process p2 = new Process("P2", 1, 8);
        // Process p3 = new Process("P3", 1, 7);
        // Process p4 = new Process("P4", 3, 3);
        // Process p5 = new Process("P5", 4, 4);

        List<Process> processList = new ArrayList<>();
        // processList.add(p1);
        // processList.add(p2);
        // processList.add(p3);
        // processList.add(p4);
        // processList.add(p5);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Scheduler scheduler = new Scheduler();

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. Priority Scheduling");
        System.out.println("2. Shortest-Job First (SJF) Scheduling");
        System.out.println("3. Shortest-Remaining Time First (SRTF) Scheduling");
        System.out.println("4. FCAI Scheduling");
        int type = reader.read();
        
        System.out.println("Choose number of processes: ");
        int numProcesses = reader.read();

   
        switch (type) {
            case 1:
            {
                break;
            }
            case 2: {
                System.out.println("Write process  'Name, Arrival time, Burst time' ");   
                for (int i = 0; i < numProcesses; i++) {
                    String input = reader.readLine();
                    String[] parts = input.split(", ");
                    if (parts.length < 3) {
                        System.out.println("Error: Please provide all three values (Name, Arrival time, Burst time).");
                        continue; // or handle the error appropriately
                    }
                    Process p = new Process(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    processList.add(p);
                }
                scheduler.setStrategy(new SJFScheduling()); 
                scheduler.executeSchedule(processList);  
                break;
            }
            case 3: {
                System.out.println("Write process  'Name, Arrival time, Burst time' ");   
                for (int i = 0; i < numProcesses; i++) {
                    String input = reader.readLine();
                    String[] parts = input.split(", ");
                    if (parts.length < 3) {
                        System.out.println("Error: Please provide all three values (Name, Arrival time, Burst time).");
                        continue; // or handle the error appropriately
                    }
                    Process p = new Process(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    processList.add(p);
                }
                scheduler.setStrategy(new SRTFScheduling()); 
                scheduler.executeSchedule(processList);  
                break;

            }
            case 4: {

                break;
            }
            default: {
                System.out.println("Unknown Process [Not Valid]");
                break;
            }
        }

        reader.close();
    }
}
