import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private int accNumber;
    private double balance;
    private String password;
    private static double minimumBalance = 100.0; // Example minimum balance

    // Private constructor
    private Account(int accNumber, double balance, String password) {
        this.accNumber = accNumber;
        this.balance = balance;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    // Public static method to create an account
    public static Account createAccount(int accNumber, double balance, String password) {
        if (balance >= minimumBalance) {
            return new Account(accNumber, balance, password);
        } else {
            throw new IllegalArgumentException("Initial balance must be at least the minimum balance");
        }
    }

    // Getter for account number
    public int getAccNumber() {
        return accNumber;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Getter for minimum balance
    public static double getMinimumBalance() {
        return minimumBalance;
    }

    // Setter for minimum balance
    public static void setMinimumBalance(double minimumBalance) {
        Account.minimumBalance = minimumBalance;
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    // Method to withdraw money with password check
    public void withdraw(double amount, String password) {
        if (this.password.equals(password)) {
            if (amount > 0) {
                if ((balance - amount) >= minimumBalance) {
                    balance -= amount;
                } else {
                    throw new IllegalArgumentException("Withdrawal would cause balance to drop below minimum balance");
                }
            } else {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
        } else {
            throw new IllegalArgumentException("Incorrect password");
        }
    }
}






