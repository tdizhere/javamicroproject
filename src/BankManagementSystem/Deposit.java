package BankManagementSystem;import java.awt.BorderLayout;
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

public class Deposit {

    public Deposit(Account acc, Database database, ArrayList<Account> accounts) {

        JFrame frame = Main.Frame(350, 210);
        JLabel title = Main.Label("Deposit", 23);
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
        JButton deposit = Main.Button("Deposit", 19);
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(am.getText().toString());
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(frame, "Amount must be positive");
                        return;
                    }
                    acc.setBalance(acc.getBalance()+amount);
					recordTransaction(acc, "Deposit", amount, acc.getBalance() + amount);

                    JOptionPane.showMessageDialog(frame, "Operation done successfully!");
                    database.saveAccounts(accounts);
					new Receipt("Deposit", amount, acc);
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Amount must be a valid number");
                }
            }
        });
        panel2.add(deposit);
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

	private void recordTransaction(Account acc, String transactionType, double amount, double newBalance) {
		 String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	String transactionRecord =acc.getAccid() + "," +transactionType + ","+amount+ "," + newBalance+ "," + timeStamp;
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
