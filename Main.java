

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        FullTimeEmployee emp1 = new FullTimeEmployee("Rohan", 01, 70000);
        PartTimeEmployee emp2 = new PartTimeEmployee("Martin", 2, 30, 400);

        payrollSystem.addEmployee(emp1);
        payrollSystem.addEmployee(emp2);
        System.out.println("Initial Employee details:");
        payrollSystem.displayAllEmployees();
        System.out.println("Removing employee");
        payrollSystem.removeEmployee(2);
        System.out.println("Updated Employee details:");
        payrollSystem.displayAllEmployees();
    }
}