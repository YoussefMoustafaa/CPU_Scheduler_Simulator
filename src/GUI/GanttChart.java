package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Processes.Process;

public class GanttChart extends JPanel {

    private List<Process> processes = new ArrayList<>();

    public GanttChart() {

        // this.processes = processList;
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

    }

    public void updateGanttChart(List<Process> processes) {
        this.processes = processes;
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (processes == null) return;

        Graphics2D g2d = (Graphics2D) g;

        int chartWidth = getWidth();
        int chartHeight = getHeight();

        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Gantt Chart Example", chartWidth / 2 - 100, 30);

        // Task bar settings
        int taskHeight = 30;
        int taskSpacing = 10;
        int taskStartY = 80;

        // Time settings
        int timeScale = 50; // Width of one time unit
        int totalTime = 35; // Total time units

        // Draw time scale
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i <= totalTime; i++) {
            int x = 150 + i * timeScale;
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(x, taskStartY - 20, x, chartHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString(Integer.toString(i), x - 5, taskStartY - 25);
        }

        // Draw tasks
        int currentY = taskStartY;
        for (Process process : processes) {
            // Task label
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString(process.getName(), 10, currentY + taskHeight / 2 + 5);

            // Draw task bars for each interval
            List<Integer> startTimes = process.getStartTimes();
            for (int i = 0; i < startTimes.size(); i += 2) {
                int startX = 150 + startTimes.get(i) * timeScale;
                int endX = 150 + startTimes.get(i+1) * timeScale;
                g2d.setColor(process.getColor());
                g2d.fillRect(startX, currentY, endX - startX, taskHeight);

                // Border around the task bar
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX, currentY, endX - startX, taskHeight);
            }

            currentY += taskHeight + taskSpacing;
        }
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gantt Chart Improved");
        GanttChart chart = new GanttChart();
        // chart.updateGanttChart();
        frame.add(chart);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
