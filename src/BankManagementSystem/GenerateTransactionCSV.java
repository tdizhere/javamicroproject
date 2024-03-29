package BankManagementSystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import java.text.SimpleDateFormat;

public class GenerateTransactionCSV {

    public static void main(String[] args) {
        
        generateTransactionCSV("Files/Transaction.csv");
    }

    public static void generateTransactionCSV(String filePath, Transaction... transactions) {
        try (FileWriter writer = new FileWriter(filePath)) {
             writer.write("AccountID,TransactionType,Amount,Balance,Time\n");
            
            for (Transaction transaction : transactions) {
                writer.write(transaction.toCSVString() + "\n");
            }
            System.out.println("Transaction CSV file generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to transaction CSV file: " + e.getMessage());
        }
    }
}

class Transaction {
    private String transactionType;
    private Date date;
    private int accountID;
    private double amount;
    private double balance;
    public Transaction(int accountID,String transactionType,double amount,double balance,Date date) {
    
        this.transactionType = transactionType;
        this.accountID = accountID;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return transactionType;
    }
    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    public String toCSVString() {
        return getTime() + "," + transactionType + "," + accountID + "," + amount + "," + balance;
    }
}
