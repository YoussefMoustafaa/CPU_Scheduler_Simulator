package GUI;

import Processes.Process;
import Processes.*;
import contextSwitch.ContextSwitch;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InputPanel extends JPanel {
    private TaskTablePanel taskTablePanel;
    // private GanttChartImproved ganttChart;    

    private JComboBox<String> schedulerComboBox;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Set<Color> usedColors = new HashSet<>();
    private Color selectedColor = null;
    private String selectedAlgorithm = "Priority Scheduling";

    private JPanel PriorityPanel;
    private JPanel SJFPanel;
    private JPanel FCAIPanel;

    private List<Process> processes = new ArrayList<>();

    public InputPanel(TaskTablePanel taskTablePanel, GanttChartImproved ganttChart) {
        this.taskTablePanel = taskTablePanel;
        // this.ganttChart = ganttChart;

        setLayout(new BorderLayout(2, 2));

        schedulerComboBox = new JComboBox<>(new String[]{"Priority Scheduling", "SJF", "SRTF", "FCAI"});
        add(schedulerComboBox, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        PriorityPanelInput();
        ShortestTimePanelInput("SJF");
        ShortestTimePanelInput("SRTF");
        FCAIPanelInput();

    }

    private void PriorityPanelInput() {
        PriorityPanel = new JPanel(new GridLayout(0, 6, 2, 2));

        PriorityPanel.add(new JLabel("Process Name"));
        JTextField nameField = new JTextField();
        PriorityPanel.add(nameField);

        PriorityPanel.add(new JLabel("Arrival Time"));
        JTextField arrivalField = new JTextField();
        PriorityPanel.add(arrivalField);

        PriorityPanel.add(new JLabel("Burst Time"));
        JTextField burstField = new JTextField();
        PriorityPanel.add(burstField);

        PriorityPanel.add(new JLabel("Priority"));
        JTextField priorityField = new JTextField();
        PriorityPanel.add(priorityField);

        JButton colorButton = new JButton("Pick Color");
        JLabel colorDisplay = new JLabel("");
        colorDisplay.setOpaque(true);
        colorDisplay.setBackground(Color.LIGHT_GRAY);

        colorButton.addActionListener(e -> {
            Color pickedColor = JColorChooser.showDialog(this, "Pick a Process Color", Color.WHITE);
            if (pickedColor != null) {
                if (usedColors.contains(pickedColor)) {
                    JOptionPane.showMessageDialog(this, "Color already in use! Please pick another");
                } else {
                    selectedColor = pickedColor;
                    usedColors.add(pickedColor);
                    colorDisplay.setBackground(pickedColor);
                    colorDisplay.setText("Color Selected");
                }
            }
        });

        JButton addProcessButton = new JButton("Add Process");

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.BLUE); 


        addProcessButton.addActionListener(e -> {
            try {

                if (arrivalField.getText().isEmpty() || nameField.getText().isEmpty() || burstField.getText().isEmpty() || priorityField.getText().isEmpty() || selectedColor == null) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameField.getText();
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());
                int priority = Integer.parseInt(priorityField.getText());

                PriorityProcess process = new PriorityProcess(name, arrival, burst, priority);
                process.setColor(selectedColor);
                processes.add(process);

                taskTablePanel.addProcess(name, arrival, burst, selectedColor);
                // ganttChart.addProcess(process);

                nameField.setText("");
                arrivalField.setText("");
                burstField.setText("");
                priorityField.setText("");
                selectedColor = null;
                colorDisplay.setBackground(Color.LIGHT_GRAY);
                colorDisplay.setText("");
                statusLabel.setText("Process added!");
                statusLabel.setForeground(Color.BLUE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for Priority Scheduler.");
            }
        });

        
        PriorityPanel.add(colorButton);
        PriorityPanel.add(colorDisplay);
        PriorityPanel.add(addProcessButton);
        // PriorityPanel.add(statusLabel);

        JLabel contextSwitchLabel = new JLabel("Context Switch");
        PriorityPanel.add(contextSwitchLabel);
        JTextField contextSwitchField = new JTextField();
        PriorityPanel.add(contextSwitchField);
        JButton addContextSwitch = new JButton("Add Context Switch");
        PriorityPanel.add(addContextSwitch);

        addContextSwitch.addActionListener(e -> {
            if (contextSwitchField.getText().isEmpty())
                return;
            int contextSwitch = Integer.parseInt(contextSwitchField.getText());
            ContextSwitch.contextSwitchTime = contextSwitch;
            contextSwitchField.setText("");
            contextSwitchLabel.setText("Context Switch: " + contextSwitch);
        });

        // JButton runButton = new JButton("Run");
        // runButton.addActionListener(e -> {
        //     Scheduler scheduler = new Scheduler();
        //     // reset start times for gantt chart
        //     for (Process process : processes) {
        //         process.clearStartTimes();
        //     }
        //     switch (selectedAlgorithm) {
        //         case "Priority Scheduling":
        //             scheduler.setStrategy(new PriorityScheduling()); 
        //             scheduler.executeSchedule(processes);
        //             break;
        //         case "SJF":
        //             break;
        //         case "SRTF":
        //             break;
        //         case "FCAI":
        //             break;
        //         default:
        //             break;
        //     }
        //     ganttChart.updateGanttChart(processes);
        // });
        // PriorityPanel.add(runButton);
        

        cardPanel.add(PriorityPanel, "Priority Scheduling");

        add(cardPanel, BorderLayout.CENTER);


        schedulerComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) schedulerComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedAlgorithm);

            
        });
    }

    private void ShortestTimePanelInput(String algorithmType) {
        SJFPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        SJFPanel.add(new JLabel("Process Name"));
        JTextField nameField = new JTextField();
        SJFPanel.add(nameField);

        SJFPanel.add(new JLabel("Arrival Time"));
        JTextField arrivalField = new JTextField();
        SJFPanel.add(arrivalField);

        SJFPanel.add(new JLabel("Burst Time"));
        JTextField burstField = new JTextField();
        SJFPanel.add(burstField);

        JButton colorButton = new JButton("Pick Color");
        JLabel colorDisplay = new JLabel("");
        colorDisplay.setOpaque(true);
        colorDisplay.setBackground(Color.LIGHT_GRAY);

        colorButton.addActionListener(e -> {
            Color pickedColor = JColorChooser.showDialog(this, "Pick a Process Color", Color.WHITE);
            if (pickedColor != null) {
                if (usedColors.contains(pickedColor)) {
                    JOptionPane.showMessageDialog(this, "Color already in use! Please pick another");
                } else {
                    selectedColor = pickedColor;
                    usedColors.add(pickedColor);
                    colorDisplay.setBackground(pickedColor);
                    colorDisplay.setText("Color Selected");
                }
            }
        });

        JButton addProcessButton = new JButton("Add Process");
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.BLUE); 

        addProcessButton.addActionListener(e -> {
            try {

                if (arrivalField.getText().isEmpty() || nameField.getText().isEmpty() || burstField.getText().isEmpty() || selectedColor == null) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameField.getText();
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());

                Process process = new Process(name, arrival, burst);
                process.setColor(selectedColor);

                processes.add(process);

                // reset inputs
                nameField.setText("");
                arrivalField.setText("");
                burstField.setText("");
                selectedColor = null;
                colorDisplay.setBackground(Color.LIGHT_GRAY);
                colorDisplay.setText("");
                usedColors.clear();
                statusLabel.setText("Process added!");
                statusLabel.setForeground(Color.BLUE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for Shortest Job Scheduler.");
            }
        });

        SJFPanel.add(colorButton);
        SJFPanel.add(colorDisplay);
        SJFPanel.add(addProcessButton);
        SJFPanel.add(statusLabel);

        cardPanel.add(SJFPanel, algorithmType);

        add(cardPanel, BorderLayout.CENTER);


        schedulerComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) schedulerComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedAlgorithm);


        });
    }

    private void FCAIPanelInput() {
        FCAIPanel = new JPanel(new GridLayout(0, 6, 5, 5));

        FCAIPanel.add(new JLabel("Process Name"));
        JTextField nameField = new JTextField();
        FCAIPanel.add(nameField);

        FCAIPanel.add(new JLabel("Arrival Time"));
        JTextField arrivalField = new JTextField();
        FCAIPanel.add(arrivalField);

        FCAIPanel.add(new JLabel("Burst Time"));
        JTextField burstField = new JTextField();
        FCAIPanel.add(burstField);

        FCAIPanel.add(new JLabel("Priority"));
        JTextField priorityField = new JTextField();
        FCAIPanel.add(priorityField);

        FCAIPanel.add(new JLabel("Quantum"));
        JTextField quantumField = new JTextField();
        FCAIPanel.add(quantumField);

        JButton colorButton = new JButton("Pick Color");
        JLabel colorDisplay = new JLabel("");
        colorDisplay.setOpaque(true);
        colorDisplay.setBackground(Color.LIGHT_GRAY);

        colorButton.addActionListener(e -> {
            Color pickedColor = JColorChooser.showDialog(this, "Pick a Process Color", Color.WHITE);
            if (pickedColor != null) {
                if (usedColors.contains(pickedColor)) {
                    JOptionPane.showMessageDialog(this, "Color already in use! Please pick another");
                } else {
                    selectedColor = pickedColor;
                    usedColors.add(pickedColor);
                    colorDisplay.setBackground(pickedColor);
                    colorDisplay.setText("Color Selected");
                }
            }
        });


        JButton addProcessButton = new JButton("Add Process");
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.BLUE); 

        addProcessButton.addActionListener(e -> {
            try {

                if (arrivalField.getText().isEmpty() || nameField.getText().isEmpty() || burstField.getText().isEmpty() || priorityField.getText().isEmpty() || selectedColor == null || quantumField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameField.getText();
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());
                int priority = Integer.parseInt(priorityField.getText());
                int quantum = Integer.parseInt(quantumField.getText());

                FCAIProcess process = new FCAIProcess(name, arrival, burst, priority, quantum);
                process.setColor(selectedColor);

                processes.add(process);

                nameField.setText("");
                arrivalField.setText("");
                burstField.setText("");
                priorityField.setText("");
                quantumField.setText("");
                selectedColor = null;
                colorDisplay.setBackground(Color.LIGHT_GRAY);
                colorDisplay.setText("");
                usedColors.clear();
                statusLabel.setText("Process added!");
                statusLabel.setForeground(Color.BLUE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for Priority Scheduler.");
            }
        });

        FCAIPanel.add(colorButton);
        FCAIPanel.add(colorDisplay);
        FCAIPanel.add(addProcessButton);
        FCAIPanel.add(statusLabel);
        

        cardPanel.add(FCAIPanel, "FCAI");

        add(cardPanel, BorderLayout.CENTER);


        schedulerComboBox.addActionListener(e -> {
            String selectedAlgorithm = (String) schedulerComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedAlgorithm);

            
        });
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public String getSelectedAlgorithm() {
        return selectedAlgorithm;
    }
}
