import javax.swing.*;

import GUI.GanttChart;
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
        GanttChart ganttChart = new GanttChart();
        InputPanel inputPanel = new InputPanel(taskTablePanel, ganttChart);

        ganttChart.setPreferredSize(new Dimension(3000, 400));

        JScrollPane ganttScrollPane = new JScrollPane(ganttChart);
        ganttScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ganttScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Place Gantt Chart and Table in a horizontal split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, taskTablePanel, ganttScrollPane);
        splitPane.setResizeWeight(0.3); // Allocate 40% space to the table initially
        splitPane.setDividerLocation(400); // Divider position at 400px
        splitPane.setDividerSize(5);

        add(inputPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        JPanel schedulerInfoPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        JLabel avgWaitTimeLabel = new JLabel("Average Wait Time: ");
        schedulerInfoPanel.add(avgWaitTimeLabel);
        JLabel avgTurnaroundTimeLabel = new JLabel("Average Turnaround Time: ");
        schedulerInfoPanel.add(avgTurnaroundTimeLabel);



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

            // Update table with wait time and turnaround time
            taskTablePanel.updateProcessInfo(processes);

            ganttChart.updateGanttChart(processes);

            avgWaitTimeLabel.setText("Average Wait Time: " + scheduler.getAvgWaitTime());
            avgTurnaroundTimeLabel.setText("Average Turnaround Time: " + scheduler.getAvgTurnaroundTime());
        });
        

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            taskTablePanel.clearTable();
            inputPanel.clearProcesses();
            inputPanel.clearUsedColors();
            avgWaitTimeLabel.setText("Average Wait Time: ");
            avgTurnaroundTimeLabel.setText("Average Turnaround Time: ");
        });


        // JPanel buttonPanel = new JPanel();
        schedulerInfoPanel.add(clearButton);
        schedulerInfoPanel.add(runButton);


        add(schedulerInfoPanel, BorderLayout.SOUTH);
        // add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CPUSchedulerGUI gui = new CPUSchedulerGUI();
            gui.setVisible(true);
        });
    }
}
