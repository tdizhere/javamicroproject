package BankManagementSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Withdraw {

    public Withdraw(Account acc, Database database, ArrayList<Account> accounts) {

        JFrame frame = Main.Frame(350, 210);
        JLabel title = Main.Label("Withdraw", 23);
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label = Main.Label("Amount", 20);
        JTextField am = Main.TextField(20);
        panel.add(label);
        panel.add(am);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setBackground(null);
        JButton withdraw = Main.Button("Withdraw", 19);
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(am.getText().toString());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(frame, "Amount must be positive");
                        return;
                    }
                    if (amount > acc.getBalance()) {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance");
                        return;
                    }
                    acc.setBalance(acc.getBalance() - amount);
                    recordTransaction(acc.getAccid(), "Withdraw", amount, acc.getBalance());

                    JOptionPane.showMessageDialog(frame, "Operation done successfully!");
                    database.saveAccounts(accounts);
					new Receipt("withdraw", amount, acc);
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount");
                }
            }
        });
        panel2.add(withdraw);
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void recordTransaction(int accountId, String transactionType, double amount, double newBalance) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String transactionRecord = accountId + "," + transactionType + "," + amount + "," + newBalance + "," + timeStamp;
        writeTransactionRecord(transactionRecord);
    }

    private void writeTransactionRecord(String transactionRecord) {
        String filePath = "Files/Transaction.csv";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(transactionRecord + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to transaction CSV file: " + e.getMessage());
        }
    }
}
