import javax.swing.*;

import GUI.GanttChartImproved;
import GUI.InputPanel;
import GUI.TaskTablePanel;
import Schedulers.PriorityScheduling;
import Schedulers.Scheduler;

import Processes.Process;

import java.awt.*;

public class CPUSchedulerGUI extends JFrame {
    // private JTextField arrivalTimeField, burstTimeFeild, priorityField;
    // private JComboBox<String> algorithmSelector;
    // private JTable resultTable;
    // private DefaultTableModel tableModel;
    // private JTextArea ganttChart;
    // private Map<String, SchedulingStrategy> schedulingAlgorithms;
    // private List<Process> processList;


    // public CPUSchedulerGUI() {

    //     schedulingAlgorithms = new HashMap<>();
    //     schedulingAlgorithms.put("Priority Scheduling", new PriorityScheduling());
    //     schedulingAlgorithms.put("SJF", new SJFScheduling());
    //     schedulingAlgorithms.put("SRTF", new SRTFScheduling());
    //     schedulingAlgorithms.put("FCAI", new FCAIScheduling());

    //     setTitle("CPU Scheduling");
    //     setSize(600, 400);
    //     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     setLayout(new BorderLayout());

    //     // Input Panel

    //     JPanel inputPanel = new JPanel(new GridLayout(4, 2));
    //     inputPanel.add(new JLabel("Arrival Time: "));
    //     arrivalTimeField = new JTextField();
    //     inputPanel.add(arrivalTimeField);

    //     inputPanel.add(new JLabel("Burst Time: "));
    //     burstTimeFeild = new JTextField();
    //     inputPanel.add(burstTimeFeild);

    //     inputPanel.add(new JLabel("Priority: "));
    //     priorityField = new JTextField();
    //     inputPanel.add(priorityField);

    //     inputPanel.add(new JLabel("Algorithm: "));
    //     algorithmSelector = new JComboBox<>(new String[] {"Priority Scheduling", "SJF", "SRTF", "FCAI"});
    //     inputPanel.add(algorithmSelector);

    //     add(inputPanel, BorderLayout.NORTH);

    //     // Table Panel

    //     tableModel = new DefaultTableModel(new String[] {"Process", "Arrival", "Burst", "Priority", "Completion"}, 0);
    //     resultTable = new JTable(tableModel);
    //     add(new JScrollPane(resultTable), BorderLayout.CENTER);

    //     // Gantt Chart

    //     ganttChart = new JTextArea(5, 30);
    //     ganttChart.setEditable(false);
    //     add(new JScrollPane(ganttChart), BorderLayout.SOUTH);

    //     // Button Panel

    //     JPanel buttonJPanel = new JPanel();
    //     JButton addButton = new JButton("Add Process");
    //     JButton runButton = new JButton("Run Scheduler");
    //     buttonJPanel.add(addButton);
    //     buttonJPanel.add(runButton);
    //     add(buttonJPanel, BorderLayout.SOUTH);


    //     addButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             addProcess();
    //         }
    //     });

    //     runButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             runScheduler();
    //         }
    //     });

    //     setVisible(true);
    // }

    public CPUSchedulerGUI() {
        setTitle("CPU Scheduling Simulator");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
    
        // List<Process> processes = new ArrayList<>();
        // processList.add(new Process("P1", 0, 3));
        // processList.add(new Process("P2", 1, 2));
        // processList.add(new Process("P3", 2, 1));
        // processList.add(new Process("P4", 3, 4));
        
        // Process p1 = new Process("P1", 0, 3);
        // p1.addTime(0);
        // p1.addTime(3);
        // Process p2 = new Process("P2", 4, 8);
        // p2.addTime(4);
        // p2.addTime(8);
        // Process p3 = new Process("P3", 9, 11);
        // p3.addTime(9);
        // p3.addTime(11);
        
        // processes.add(p1);
        // processes.add(p2);
        // processes.add(p3);
        
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
            java.util.List<Process> processes = inputPanel.getProcesses();
            Scheduler scheduler = new Scheduler();
            // reset start times for gantt chart
            for (Process process : processes) {
                process.clearStartTimes();
            }
            switch (inputPanel.getSelectedAlgorithm()) {
                case "Priority Scheduling":
                    scheduler.setStrategy(new PriorityScheduling()); 
                    scheduler.executeSchedule(processes);
                    break;
                case "SJF":
                    break;
                case "SRTF":
                    break;
                case "FCAI":
                    break;
                default:
                    break;
            }
            ganttChart.updateGanttChart(processes);
        });

        JPanel buttonPanel = new JPanel();
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
