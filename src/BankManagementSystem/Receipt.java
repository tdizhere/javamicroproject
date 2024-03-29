package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {

    public Receipt(String transactionType, double amount, Account acc) {
        JFrame frame = new JFrame("Receipt");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Transaction Receipt", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel nameLabel = new JLabel("Name:");
        JLabel nameValueLabel = new JLabel(acc.getFirstName() + " " + acc.getLastName());

        JLabel accountLabel = new JLabel("Account Number:");
        JLabel accountValueLabel = new JLabel(Integer.toString(acc.getAccid()));

        JLabel transactionLabel = new JLabel("Transaction Type:");
        JLabel transactionValueLabel = new JLabel(transactionType);

        JLabel amountLabel = new JLabel("Amount:");
        JLabel amountValueLabel = new JLabel("$" + String.format("%.2f", amount));
        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(nameLabel);
        panel.add(nameValueLabel);
        panel.add(accountLabel);
        panel.add(accountValueLabel);
        panel.add(transactionLabel);
        panel.add(transactionValueLabel);
        panel.add(amountLabel);
        panel.add(amountValueLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton printButton = new JButton("Download Receipt");

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = "receipt_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
                try (FileWriter writer = new FileWriter(fileName)) {
                    writer.write("Transaction Receipt\n");
                    writer.write("Name: " + acc.getFirstName() + " " + acc.getLastName() + "\n");
                    writer.write("Account Number: " + acc.getAccid() + "\n");
                    writer.write("Transaction Type: " + transactionType + "\n");
                    writer.write("Amount: $" + String.format("%.2f", amount) + "\n");
                    writer.write("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
                    JOptionPane.showMessageDialog(frame, "Receipt saved as " + fileName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving receipt.");
                }
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
