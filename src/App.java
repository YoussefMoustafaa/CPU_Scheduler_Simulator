import java.util.Scanner;

import Schedulers.*;


public class App {
    public static void main(String[] args) throws Exception {

        Scheduler scheduler = new Scheduler();

        System.out.println("Choose the number of processes: ");
        Scanner scanner = new Scanner(System.in);

        int processesNumber = scanner.nextInt();

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. Priority Scheduling");
        System.out.println("2. Shortest-Job First (SJF) Scheduling");
        System.out.println("3. Shortest-Rmainging Time First (SRTF) Scheduling");
        System.out.println("4. FCAI Scheduling");

        

        scanner.close();

    }
}
