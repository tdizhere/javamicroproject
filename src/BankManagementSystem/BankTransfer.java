package BankManagementSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BankTransfer {

    public BankTransfer(Account senderAccount, Database database, ArrayList<Account> allAccounts) {
        JFrame frame = new JFrame("Bank Transfer");
        frame.setSize(350, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel senderLabel = new JLabel("Sender Account:");
        JLabel senderValueLabel = new JLabel(senderAccount.getFirstName() + " " + senderAccount.getLastName() + " (" + senderAccount.getAccid() + ")");

        JLabel recipientLabel = new JLabel("Recipient Account:");
        JTextField recipientTextField = new JTextField();

        JLabel amountLabel = new JLabel("Amount to Transfer:");
        JTextField amountTextField = new JTextField();

        panel.add(senderLabel);
        panel.add(senderValueLabel);
        panel.add(recipientLabel);
        panel.add(recipientTextField);
        panel.add(amountLabel);
        panel.add(amountTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton transferButton = new JButton("Transfer");

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int recipientAccId = Integer.parseInt(recipientTextField.getText());
                    double amount = Double.parseDouble(amountTextField.getText());

                    // Find recipient account
                    Account recipientAccount = null;
                    for (Account acc : allAccounts) {
                        if (acc.getAccid() == recipientAccId) {
                            recipientAccount = acc;
                            break;
                        }
                    }

                    if (recipientAccount != null) {
                        if (senderAccount.getBalance() >= amount) {
                            // Perform transfer
                            senderAccount.setBalance(senderAccount.getBalance() - amount);
                            recipientAccount.setBalance(recipientAccount.getBalance() + amount);

                            // Record transaction for sender
                            recordTransaction(senderAccount.getAccid(), "Transfer to " + recipientAccId, amount, senderAccount.getBalance());

                            // Record transaction for recipient
                            recordTransaction(recipientAccId, "Transfer from " + senderAccount.getAccid(), amount, recipientAccount.getBalance());

                            // Update accounts in database
                            Database database = new Database(); // Instantiate your database handler
                            database.saveAccounts(allAccounts);

                            JOptionPane.showMessageDialog(frame, "Transfer successful!");
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insufficient balance in sender account.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Recipient account not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numeric values.");
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(transferButton);
        buttonPanel.add(cancelButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
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
