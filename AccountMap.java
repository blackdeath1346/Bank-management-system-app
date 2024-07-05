import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AccountMap implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private Map<Integer, Account> accounts;

    // Constructor
    public AccountMap() {
        accounts = new HashMap<>();
    }

    // Method to add an account
    public void addAccount(int accNumber, double balance, String password) {
        if (!accounts.containsKey(accNumber)) {
            Account account = Account.createAccount(accNumber, balance, password);
            accounts.put(accNumber, account);
        } else {
            throw new IllegalArgumentException("Account with this number already exists");
        }
    }

    // Method to delete an account
    public void deleteAccount(int accNumber) {
        if (accounts.containsKey(accNumber)) {
            accounts.remove(accNumber);
        } else {
            throw new IllegalArgumentException("No account found with this number");
        }
    }

    // Method to display an account
    public void displayAccount(int accNumber) {
        if (accounts.containsKey(accNumber)) {
            Account account = accounts.get(accNumber);
            System.out.println("Account Number: " + account.getAccNumber());
            System.out.println("Balance: " + account.getBalance());
        } else {
            throw new IllegalArgumentException("No account found with this number");
        }
    }

    // Method to get an account (optional, for further manipulation if needed)
    public Account getAccount(int accNumber) {
        if (accounts.containsKey(accNumber)) {
            return accounts.get(accNumber);
        } else {
            throw new IllegalArgumentException("No account found with this number");
        }
    }

    // Method to withdraw money from an account with password
    public void withdrawFromAccount(int accNumber, double amount, String password) {
        if (accounts.containsKey(accNumber)) {
            Account account = accounts.get(accNumber);
            account.withdraw(amount, password);
        } else {
            throw new IllegalArgumentException("No account found with this number");
        }
    }

    // Method to transfer money from one account to another with password check
    public void transferMoney(int fromAccNumber, int toAccNumber, double amount, String password) {
        if (accounts.containsKey(fromAccNumber) && accounts.containsKey(toAccNumber)) {
            Account fromAccount = accounts.get(fromAccNumber);
            Account toAccount = accounts.get(toAccNumber);

            // Check if the withdrawal can be made without violating the minimum balance requirement
            if ((fromAccount.getBalance() - amount) >= Account.getMinimumBalance()) {
                // Withdraw money from the source account
                fromAccount.withdraw(amount, password);
                // Deposit money into the destination account
                toAccount.deposit(amount);
            } else {
                throw new IllegalArgumentException("Insufficient funds to maintain minimum balance after transfer");
            }
        } else {
            throw new IllegalArgumentException("One or both account numbers are invalid");
        }
    }
    }