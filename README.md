# CPU Scheduling Simulator

---

## Overview

The **CPU Scheduling Simulator** is a project designed to simulate how different CPU scheduling algorithms manage processes in an operating system. It provides a visual interface where users can interact with the simulation, see the order in which processes are executed, and observe a Gantt chart that shows the execution timeline. This project helps users understand how CPU scheduling works and how different algorithms handle processes based on their characteristics.

---

## Key Features

- **Four CPU Scheduling Algorithms**: The simulator supports four popular scheduling algorithms:
  - **Priority Scheduling**: Processes are executed based on priority, with the highest priority executed first.
  - **Shortest Job First (SJF)**: The process with the shortest burst time (execution time) is chosen first.
  - **Shortest Remaining Time First (SRTF)**: A preemptive version of SJF, where the process with the shortest remaining burst time is executed first.
  - **FCAI Scheduling**: A more advanced algorithm that uses a custom "FCAI Score" to determine the execution order of processes.

- **Interactive Graphical User Interface (GUI)**:  
  The simulator includes a user-friendly GUI where users can input processes, specify their arrival times, burst times, and priorities, and then run the simulation to see how the CPU handles these processes. The interface includes:
  - A table displaying the processes with their details (arrival time, burst time, priority, etc.).
  - A Gantt chart to visually represent the timeline of process executions.

- **Real-time Scheduling and Visualization**:  
  The processes are visualized in real-time as they are scheduled by the algorithm. A Gantt chart displays the process execution order and how long each process runs.

- **Wait Time and Turnaround Time**:  
  For each process, the simulator calculates and displays two important metrics:
  - **Wait Time**: The amount of time a process has been in the ready queue waiting to be executed.
  - **Turnaround Time**: The total time from the arrival of the process to its completion (including wait time and burst time).

## How It Works

1. **Input**: Users can enter the details of multiple processes (name, arrival time, burst time, priority) into the system.
2. **Execution**: When the user clicks "Run", the selected CPU scheduling algorithm begins processing the tasks. The processes are executed one by one based on the algorithm's rules.
3. **Visualization**: As processes are scheduled, the Gantt chart updates in real-time to show the execution order.
4. **Results**: Once the simulation is complete, the wait time and turnaround time for each process are displayed.

---

## Technologies Used

- **Java**: The project is developed in Java, utilizing object-oriented programming concepts for clean, maintainable code.
- **Swing**: Java Swing is used for building the GUI (Graphical User Interface) to make the simulator interactive.
- **Collections (Queue, List, LinkedList)**: These are used to manage processes and their scheduling, ensuring that processes are handled efficiently as they arrive and get executed.

---

## Installation

1. Ensure you have **Java 8 or higher** installed.
2. Clone the repository:
   ```bash
   git clone https://github.com/YoussefMoustafaa/CPU_Scheduler_Simulator.git
   ```
3. Navigate to the project directory:
   ```bash
   cd CPU_Scheduler_Simulator
   ```

---

## Example Input

```plaintext
p1, 0, 17, 4, 4
p2, 3, 6, 9, 3
p3, 4, 10, 3, 5
p4, 29, 4, 8, 2
```

---

## Why This Project is Useful

This simulator allows you to understand and compare how different CPU scheduling algorithms behave under varying conditions. By visualizing the process scheduling and execution, users can see how the algorithms impact system performance, including metrics like wait time and turnaround time. This is an excellent tool for learning about operating system concepts and CPU management.

---

## Conclusion

This project demonstrates the use of fundamental operating system concepts in a practical, interactive way. It showcases my ability to design and implement complex algorithms, build a user-friendly interface, and visualize real-time data effectively. Whether you're learning about CPU scheduling or looking to see how these algorithms work in practice, this simulator provides a clear and engaging way to explore these important concepts.

---

## Acknowledgments

- Inspired by scheduling algorithms and process management concepts.
- Developed with ❤️ and Java.

