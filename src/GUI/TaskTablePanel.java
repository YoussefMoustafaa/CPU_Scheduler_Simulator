package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.util.List;
import Processes.Process;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TaskTablePanel extends JPanel {
    private JTable taskTable;
    private DefaultTableModel tableModel;

    public TaskTablePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Process Info"));

        String[] columnNames = {"Process Name", "Arrival Time", "Burst Time", "Color", "Wait Time", "Turnaround Time"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Color.class : String.class;
            }
        };

        taskTable = new JTable(tableModel);
    
        taskTable.getColumnModel().getColumn(3).setCellRenderer(new ColorRenderer());

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setPreferredSize(new Dimension(1000, 80));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addProcess(String name, int arrivalTime, int burstTime, Color color) {
        tableModel.addRow(new Object[] {name, arrivalTime, burstTime, color});
    }

    public void updateProcessInfo(List<Process> processes) {
        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            tableModel.setValueAt(process.getWaitTime(), i, 4);
            tableModel.setValueAt(process.getTurnaroundTime(), i, 5);
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    private static class ColorRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Color) {
                setBackground((Color) value);
                setOpaque(true); // Make the background visible
            }
            return this;
        }
    }
}
