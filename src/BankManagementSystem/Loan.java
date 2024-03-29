package BankManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Loan {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> loanOptions;
    private JTextField loanAmountField;
    private JTextField loanDurationField;
    private JLabel interestRateLabel;
    private JLabel totalRepaymentLabel;
    private Account acc;

    private String[] loanTypes = {"Gold Loan", "Personal Loan", "Education Loan"};
    private double[] interestRates = {0.05, 0.08, 0.07};

    public Loan(Account acc,ArrayList<Account> accounts) {
        this.acc = acc;

        frame = new JFrame("Loan Management System");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        loanOptions = new JComboBox<>(loanTypes);
        loanAmountField = new JTextField();
        loanDurationField = new JTextField();
        interestRateLabel = new JLabel("Interest Rate: ");
        totalRepaymentLabel = new JLabel("Total Repayment: ");

        panel.add(new JLabel("Loan Type: "));
        panel.add(loanOptions);
        panel.add(new JLabel("Loan Amount: "));
        panel.add(loanAmountField);
        panel.add(new JLabel("Loan Duration (years): "));
        panel.add(loanDurationField);
        panel.add(interestRateLabel);
        panel.add(new JLabel());
        panel.add(totalRepaymentLabel);
        panel.add(new JLabel());
        frame.add(panel, BorderLayout.CENTER);

        JButton takeLoanButton = new JButton("Take Loan");
        takeLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeLoan();
            }
        });
        frame.add(takeLoanButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loanAmountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateLoan();
            }
        });

        loanDurationField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateLoan();
            }
        });
    }

    private void takeLoan() {
        int selectedLoanIndex = loanOptions.getSelectedIndex();
        double loanAmount = 0;
        int loanDuration = 0;
        int accountId = acc.getAccid();
        try {
            loanAmount = Double.parseDouble(loanAmountField.getText());
            loanDuration = Integer.parseInt(loanDurationField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid loan amount and duration.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double interestRate = interestRates[selectedLoanIndex];
        double totalRepayment;

        if (loanDuration >= 1 && loanDuration <= 50) {
            double monthlyInterestRate = interestRate / 12;
            totalRepayment = loanAmount * (1 + monthlyInterestRate * loanDuration * 12);
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid loan duration in years (1-50).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        interestRateLabel.setText("Interest Rate: " + (interestRate * 100) + "%");
        totalRepaymentLabel.setText("Total Repayment: $" + String.format("%.2f", totalRepayment));

        // Write loan details to CSV file
        String[] loanData = {String.valueOf(accountId), String.valueOf(loanAmount), loanTypes[selectedLoanIndex], String.valueOf(interestRate),
                String.valueOf(loanDuration), String.format("%.2f", totalRepayment)};
        
        writeLoanDataToCSV(loanData);
        double newBalance = acc.getBalance() + Double.parseDouble(loanAmountField.getText());
        acc.setBalance(newBalance);
        recordTransaction(acc,"Loan",loanAmount,newBalance);
        
    
    
        JOptionPane.showMessageDialog(frame, "Loan credited successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        frame.dispose();
      
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
    private void writeLoanDataToCSV(String[] loanData) {
        String csvFile = "Files/loan_data.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            for (int i = 0; i < loanData.length; i++) {
                writer.append(loanData[i]);
                if (i < loanData.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateLoan() {
        double loanAmount = 0;
        int loanDuration = 0;
        try {
            loanAmount = Double.parseDouble(loanAmountField.getText());
            loanDuration = Integer.parseInt(loanDurationField.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        int selectedLoanIndex = loanOptions.getSelectedIndex();
        double interestRate = interestRates[selectedLoanIndex];
        double totalRepayment;

        if (loanDuration >= 1 && loanDuration <= 50) {
            double monthlyInterestRate = interestRate / 12;
            totalRepayment = loanAmount * (1 + monthlyInterestRate * loanDuration * 12);
        } else {
            return;
        }

        interestRateLabel.setText("Interest Rate: " + (interestRate * 100) + "%");
        totalRepaymentLabel.setText("Total Repayment: $" + String.format("%.2f", totalRepayment));
    }
}
