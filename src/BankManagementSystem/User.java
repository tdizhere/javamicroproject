package BankManagementSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User {
    private Account account;
 

    public User(Account account) {
        this.account = account;
    
        displayAccountDetails();
    }

    private void displayAccountDetails() {
        JFrame frame = new JFrame("Account Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
    
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 rows, 2 columns
    
        JLabel nameLabel = new JLabel("Name:");
        JLabel accountNumberLabel = new JLabel("Account Number:");
        JLabel loanAmountLabel = new JLabel("Loan Amount:");
    
        JLabel nameValueLabel = new JLabel(account.getFirstName() + " " + account.getLastName());
        JLabel accountNumberValueLabel = new JLabel(String.valueOf(account.getAccid()));
        JLabel loanAmountValueLabel = new JLabel("$" + String.format("%.2f", account.getBalance()));
    
        panel.add(nameLabel);
        panel.add(nameValueLabel);
        panel.add(accountNumberLabel);
        panel.add(accountNumberValueLabel);
        panel.add(loanAmountLabel);
        panel.add(loanAmountValueLabel);
    
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    
        frame.add(panel, BorderLayout.CENTER);
        frame.add(backButton, BorderLayout.SOUTH);
    
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    
}