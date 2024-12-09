# CPU Scheduling Simulator

## Overview

A simulator to visualize how CPU scheduling algorithms manage processes. Users can interact with the simulation, input processes, and see execution timelines through a Gantt chart.

---

## Features

- **Supported Algorithms**:  
  - Priority Scheduling  
  - Shortest Job First (SJF)  
  - Shortest Remaining Time First (SRTF)  
  - FCAI Scheduling  

- **Interactive GUI**:  
  Input process details (arrival time, burst time, priority) and run simulations with a real-time Gantt chart display.

- **Metrics Display**:  
  - **Wait Time**: Time spent waiting in the ready queue.  
  - **Turnaround Time**: Total time from arrival to completion.  

---

## Technologies Used

- **Java**: Core logic and algorithms.  
- **Swing**: GUI design for user interaction.  
- **Data Structures**: Efficient process management using Queues and Lists.

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

This project simplifies understanding CPU scheduling by visualizing how different algorithms manage processes. Great for learning operating system concepts and process management.

---

## Acknowledgments

- Inspired by scheduling algorithms and process management concepts.
- Developed with ❤️ and Java.

