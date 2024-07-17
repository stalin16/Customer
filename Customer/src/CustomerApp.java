import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerApp extends JFrame {

    static class Customer {
        private int customerId;
        private String customerlastName;
        private String customerfirstName;
        private String customerphone;

        public Customer(int customerId, String lastName, String firstName, String phone) {
            this.customerId = customerId;
            this.customerlastName = lastName;
            this.customerfirstName = firstName;
            this.customerphone = phone;
        }

        public int getCustomerId() { return customerId; }
        public String getLastName() { return customerlastName; }
        public String getFirstName() { return customerfirstName; }
        public String getPhone() { return customerphone; }
    }

    static class CustomerDAO {
        private List<Customer> customers;

        public CustomerDAO() {
            customers = new ArrayList<>();
        }

        public void addCustomer(Customer customer) {
            customers.add(customer);
        }

        public List<Customer> getCustomers() { return customers; }
    }

    private JTable table;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;
    private int currentIndex = 0;

    public CustomerApp() {
        customerDAO = new CustomerDAO();

        setTitle("Customer Information");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"customer_id", "customer_last_name", "customer_first_name", "customer_phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton addButton = new JButton("Add Customer");

        previousButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex -= 7;
                showCustomers(customerDAO.getCustomers());
            }
        });

        nextButton.addActionListener(e -> {
            if (currentIndex < customerDAO.getCustomers().size() - 7) {
                currentIndex += 7;
                showCustomers(customerDAO.getCustomers());
            }
        });

        addButton.addActionListener(e -> addCustomer());

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showCustomers(customerDAO.getCustomers());
    }

    private void addCustomer() {
        JTextField lastNameField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField phoneField = new JTextField();
        Object[] message = {
                "Last Name:", lastNameField,
                "First Name:", firstNameField,
                "Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int customerId = customerDAO.getCustomers().size() + 1;
            Customer customer = new Customer(customerId, lastNameField.getText(), firstNameField.getText(), phoneField.getText());
            customerDAO.addCustomer(customer);
            showCustomers(customerDAO.getCustomers());
        }
    }

    private void showCustomers(List<Customer> customers) {
        tableModel.setRowCount(0);
        int endIndex = Math.min(currentIndex + 7, customers.size());
        for (int i = currentIndex; i < endIndex; i++) {
            Customer customer = customers.get(i);
            tableModel.addRow(new Object[]{
                    customer.getCustomerId(),
                    customer.getLastName(),
                    customer.getFirstName(),
                    customer.getPhone()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerApp app = new CustomerApp();
            app.setVisible(true);
        });
    }
}
