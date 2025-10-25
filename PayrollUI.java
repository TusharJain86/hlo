import javax.swing.*;
import java.awt.*;

public class PayrollUI extends JFrame {

    private PayrollSystem payrollSystem;

    private JTextField nameField, idField, salaryField, hoursField, rateField, removeIdField;
    private JTextArea displayArea;
    private JComboBox<String> employeeTypeBox;

    public PayrollUI() {
        payrollSystem = new PayrollSystem();

        // ======== FRAME SETTINGS ========
        setTitle("Payroll System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray

        // ======== TOP PANEL (Form) ========
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Employee"));
        inputPanel.setBackground(new Color(220, 235, 255)); // Light blue

        employeeTypeBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
        nameField = new JTextField();
        idField = new JTextField();
        salaryField = new JTextField();
        hoursField = new JTextField();
        rateField = new JTextField();

        JTextField[] textFields = {nameField, idField, salaryField, hoursField, rateField};
        for (JTextField field : textFields) {
            field.setBackground(Color.WHITE);
            field.setForeground(Color.DARK_GRAY);
        }

        JLabel[] labels = {
            new JLabel("Employee Type:"), new JLabel("Name:"), new JLabel("ID:"),
            new JLabel("Monthly Salary (for Full-Time):"),
            new JLabel("Hours Worked (for Part-Time):"),
            new JLabel("Hourly Rate (for Part-Time):")
        };
        for (JLabel label : labels) {
            label.setForeground(new Color(50, 50, 50)); // Dark gray
        }

        inputPanel.add(labels[0]); inputPanel.add(employeeTypeBox);
        inputPanel.add(labels[1]); inputPanel.add(nameField);
        inputPanel.add(labels[2]); inputPanel.add(idField);
        inputPanel.add(labels[3]); inputPanel.add(salaryField);
        inputPanel.add(labels[4]); inputPanel.add(hoursField);
        inputPanel.add(labels[5]); inputPanel.add(rateField);

        JButton addButton = new JButton("Add Employee");
        addButton.setBackground(new Color(76, 175, 80)); // Green
        addButton.setForeground(Color.WHITE);

        JButton showButton = new JButton("Show All Employees");
        showButton.setBackground(new Color(33, 150, 243)); // Blue
        showButton.setForeground(Color.WHITE);

        inputPanel.add(addButton);
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        // ======== CENTER PANEL (Display) ========
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);
        displayArea.setForeground(new Color(33, 33, 33)); // Dark text
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // ======== BOTTOM PANEL (Remove) ========
        JPanel removePanel = new JPanel(new FlowLayout());
        removePanel.setBorder(BorderFactory.createTitledBorder("Remove Employee"));
        removePanel.setBackground(new Color(255, 230, 230)); // Light pink

        JLabel removeLabel = new JLabel("Enter ID to Remove:");
        removeLabel.setForeground(new Color(50, 50, 50));
        removePanel.add(removeLabel);

        removeIdField = new JTextField(10);
        removeIdField.setBackground(Color.WHITE);
        removeIdField.setForeground(Color.DARK_GRAY);
        removePanel.add(removeIdField);

        JButton removeButton = new JButton("Remove");
        removeButton.setBackground(new Color(244, 67, 54)); // Red
        removeButton.setForeground(Color.WHITE);
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
