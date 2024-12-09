import javax.swing.*;

import GUI.GanttChartImproved;
import GUI.InputPanel;
import GUI.TaskTablePanel;
import Schedulers.FCAIScheduling;
import Schedulers.PriorityScheduling;
import Schedulers.SJFScheduling;
import Schedulers.SRTFScheduling;
import Schedulers.Scheduler;

import Processes.Process;

import java.util.List;
import java.awt.*;

public class CPUSchedulerGUI extends JFrame {

    public CPUSchedulerGUI() {
        setTitle("CPU Scheduling Simulator");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        TaskTablePanel taskTablePanel = new TaskTablePanel();
        GanttChartImproved ganttChart = new GanttChartImproved();
        InputPanel inputPanel = new InputPanel(taskTablePanel, ganttChart);

        JScrollPane ganttScrollPane = new JScrollPane(ganttChart);
        ganttScrollPane.setPreferredSize(new Dimension(600, 600)); // Optional: Control initial visible area

        // Place Gantt Chart and Table in a horizontal split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, taskTablePanel, ganttScrollPane);
        splitPane.setResizeWeight(0.2); // Allocate 40% space to the table initially
        splitPane.setDividerLocation(400); // Divider position at 400px
        splitPane.setDividerSize(5);

        add(inputPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // add(ganttChart, BorderLayout.CENTER);
        // add(taskTablePanel, BorderLayout.SOUTH);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            List<Process> processes = inputPanel.getProcesses();
            Scheduler scheduler = new Scheduler();
            // reset start times for gantt chart
            for (Process process : processes) {
                process.clearStartTimes();
            }
            String algorithm = inputPanel.getSelectedAlgorithm();
            System.out.println(algorithm);
            if (algorithm.equalsIgnoreCase("Priority Scheduling")) {
                scheduler.setStrategy(new PriorityScheduling()); 
            }
            else if (algorithm.equalsIgnoreCase("SJF")) {
                scheduler.setStrategy(new SJFScheduling()); 
            }
            else if (algorithm.equalsIgnoreCase("SRTF")) {
                scheduler.setStrategy(new SRTFScheduling()); 
            }
            else if (algorithm.equalsIgnoreCase("FCAI")) {
                scheduler.setStrategy(new FCAIScheduling()); 
            }
            scheduler.executeSchedule(processes);
            // switch (inputPanel.getSelectedAlgorithm()) {
            //     case "Priority Scheduling":
            //         scheduler.setStrategy(new PriorityScheduling()); 
            //         scheduler.executeSchedule(processes);
            //         break;
            //     case "SJF":
            //         scheduler.setStrategy(new SJFScheduling());
            //         scheduler.executeSchedule(processes);
            //         break;
            //     case "SRTF":
            //         scheduler.setStrategy(new SRTFScheduling());
            //         scheduler.executeSchedule(processes);
            //         break;
            //     case "FCAI":
            //         scheduler.setStrategy(new FCAIScheduling());
            //         scheduler.executeSchedule(processes);
            //         break;
            //     default:
            //         break;
            // }
            ganttChart.updateGanttChart(processes);
        });
        

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            taskTablePanel.clearTable();
            inputPanel.clearProcesses();
            inputPanel.clearUsedColors();
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(runButton);


        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CPUSchedulerGUI gui = new CPUSchedulerGUI();
            gui.setVisible(true);
        });
    }
}
