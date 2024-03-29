package BankManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionHistory {

    public TransactionHistory(Account acc, String filePath) {
        JFrame frame = new JFrame("Transaction History");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Fetch transaction history for the current account from the CSV file
        ArrayList<Transaction> transactions = readTransactionHistoryFromCSV(filePath, acc.getAccid());

        // Create table model with column names
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add columns to the table
        tableModel.addColumn("Amount");
        tableModel.addColumn("Balance");
        tableModel.addColumn("Type");
        tableModel.addColumn("Time");

        // Populate the table with transaction data
        for (Transaction transaction : transactions) {
            Object[] row = {transaction.getAmount(), transaction.getBalance(),
                            transaction.getType(), transaction.getTime()};
            tableModel.addRow(row);
        }

        frame.add(scrollPane, BorderLayout.CENTER);

        // Add Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    } 
    private ArrayList<Transaction> readTransactionHistoryFromCSV(String filePath, int accountId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the first line
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }
                String[] data = line.split(",");
                if (data.length == 5) {
                    int accId = Integer.parseInt(data[0]);
                    if (accId == accountId) {
                        double amount = Double.parseDouble(data[2]);
                        double balance = Double.parseDouble(data[3]);
                        String type = data[1];
                        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[4]); 
                        Transaction transaction = new Transaction(accId,type,amount,balance,time); // Adjust parameters order
                        transactions.add(transaction);
                    }
                }
            }
        } catch (IOException | NumberFormatException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Error reading transaction history from CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return transactions;
    }
    
}
