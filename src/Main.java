import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField nameField, ageField, salaryField;
    private JButton submitButton;
    private JLabel loadingLabel;

    public Main() {
        setTitle("Loan Eligibility System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 20, 20));

        Font font = new Font("Arial", Font.BOLD, 20);

        add(createLabel("Applicant Name:", font));
        nameField = createTextField(font);
        add(nameField);

        add(createLabel("Age:", font));
        ageField = createTextField(font);
        add(ageField);

        add(createLabel("Annual Salary ($):", font));
        salaryField = createTextField(font);
        add(salaryField);

        submitButton = new JButton("Submit");
        submitButton.setFont(font);
        add(submitButton);

        loadingLabel = new JLabel("Processing your eligibility...", SwingConstants.CENTER);
        loadingLabel.setFont(font);
        loadingLabel.setVisible(false);
        add(loadingLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkEligibility();
            }
        });

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        return textField;
    }

    private void checkEligibility() {
        String name = nameField.getText();
        int age;
        double salary;

        try {
            age = Integer.parseInt(ageField.getText());
            salary = Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and salary!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        loadingLabel.setVisible(true);
        submitButton.setEnabled(false);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loadingLabel.setVisible(false);
                submitButton.setEnabled(true);

                String message;
                if (age >= 25 && age <= 60 && salary > 50000) {
                    message = "✅ Congratulations, " + name + "! You are eligible for a loan.";
                } else {
                    message = "❌ Sorry, " + name + ", you are not eligible for a loan.\n\nReasons:";
                    if (age < 25) message += "\n- Your age is below 25.";
                    if (age > 60) message += "\n- Your age is above 60.";
                    if (salary <= 50000) message += "\n- Your annual salary is $50,000 or less.";
                }

                JOptionPane.showMessageDialog(null, message, "Loan Eligibility Result", JOptionPane.INFORMATION_MESSAGE);
                ((Timer) evt.getSource()).stop();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
