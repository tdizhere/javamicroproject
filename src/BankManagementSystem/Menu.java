package BankManagementSystem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;


public class Menu {
    
    public Menu(Account acc, Database database, ArrayList<Account> accounts) {
        
        JFrame frame = Main.Frame(450, 400); 
        JLabel title = Main.Label("Welcome " + acc.getFirstName() + " " + acc.getLastName(), 23);
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton deposit = Main.Button("Deposit", 22);
        JButton withdraw = Main.Button("Withdraw", 22);
        JButton balance = Main.Button("Balance", 22);
        JButton transfer = Main.Button("Transfer", 22); 
        JButton history = Main.Button("Transaction History", 22);
        JButton loan = Main.Button("Loan", 22);
        JButton user = Main.Button("Account INFO", 22);
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        panel.add(deposit);
        panel.add(withdraw);
        panel.add(balance);
        panel.add(transfer);
        panel.add(history);
        panel.add(loan);
        panel.add(user);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                new Login(accounts, database, acc.getAccid() + 1);
                frame.dispose(); 
            }
        });

        logoutPanel.add(logoutButton);
        
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Deposit(acc, database, accounts);
            }
        });
        
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Withdraw(acc, database, accounts);
            }
        });
        
        balance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Your balance is: " + acc.getBalance());
            }
        });
        
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BankTransfer(acc, database, accounts);
            }
        }); 
        
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionHistory(acc, "Files/Transaction.csv");
            }
        });
        
        loan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Loan(acc,accounts); 
            }
        });
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new User(acc); 
            }
        });
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
		frame.add(logoutPanel, BorderLayout.SOUTH);
        frame.setVisible(true);																																																
    }
}
