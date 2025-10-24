import javax.swing.*;

import employee.Employee;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import employee.PayrollSystem;

import java.awt.*;

public class PayrollUI extends JFrame {

    private PayrollSystem payrollSystem;

    private JTextField nameField, idField, salaryField, hoursField, rateField, removeIdField;
    private JTextArea displayArea;
    private JComboBox<String> employeeTypeBox;

    public PayrollUI() {
        payrollSystem = new PayrollSystem();

        setTitle("Payroll System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ======== TOP PANEL (Form) ========
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Employee"));

        employeeTypeBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
        nameField = new JTextField();
        idField = new JTextField();
        salaryField = new JTextField();
        hoursField = new JTextField();
        rateField = new JTextField();

        inputPanel.add(new JLabel("Employee Type:"));
        inputPanel.add(employeeTypeBox);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Monthly Salary (for Full-Time):"));
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Hours Worked (for Part-Time):"));
        inputPanel.add(hoursField);
        inputPanel.add(new JLabel("Hourly Rate (for Part-Time):"));
        inputPanel.add(rateField);

        JButton addButton = new JButton("Add Employee");
        inputPanel.add(addButton);

        JButton showButton = new JButton("Show All Employees");
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        // ======== CENTER PANEL (Display) ========
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // ======== BOTTOM PANEL (Remove) ========
        JPanel removePanel = new JPanel(new FlowLayout());
        removePanel.setBorder(BorderFactory.createTitledBorder("Remove Employee"));
        removePanel.add(new JLabel("Enter ID to Remove:"));
        removeIdField = new JTextField(10);
        removePanel.add(removeIdField);
        JButton removeButton = new JButton("Remove");
        removePanel.add(removeButton);

        add(removePanel, BorderLayout.SOUTH);

        // ======== ACTIONS ========
        addButton.addActionListener(e -> addEmployee());
        showButton.addActionListener(e -> showAllEmployees());
        removeButton.addActionListener(e -> removeEmployee());
    }

    private void addEmployee() {
        try {
            String name = nameField.getText().trim();
            int id = Integer.parseInt(idField.getText().trim());
            String type = (String) employeeTypeBox.getSelectedItem();

            if (type.equals("Full-Time")) {
                double salary = Double.parseDouble(salaryField.getText().trim());
                FullTimeEmployee emp = new FullTimeEmployee(name, id, salary);
                payrollSystem.addEmployee(emp);
                JOptionPane.showMessageDialog(this, "Full-Time Employee added successfully!");
            } else {
                int hours = Integer.parseInt(hoursField.getText().trim());
                double rate = Double.parseDouble(rateField.getText().trim());
                PartTimeEmployee emp = new PartTimeEmployee(name, id, hours, rate);
                payrollSystem.addEmployee(emp);
                JOptionPane.showMessageDialog(this, "Part-Time Employee added successfully!");
            }

            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAllEmployees() {
        displayArea.setText("");
        for (Employee emp : payrollSystem.getEmployeeList()) {
            displayArea.append(emp.toString() + "\n");
        }
    }

    private void removeEmployee() {
        try {
            int id = Integer.parseInt(removeIdField.getText().trim());
            payrollSystem.removeEmployee(id);
            JOptionPane.showMessageDialog(this, "Employee removed successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        salaryField.setText("");
        hoursField.setText("");
        rateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayrollUI().setVisible(true));
    }
}
