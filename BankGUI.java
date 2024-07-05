//gui interface for managing accounts, still under development
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class BankGUI extends JFrame {
    private static final String FILE_NAME = "accountMap.ser";
    private AccountMap accountMap;

    public BankGUI() {
        try {
            accountMap = AccountMapSerializer.deserializeAccountMap(FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            accountMap = new AccountMap();
        }

        initUI();
    }

    private void initUI() {
        getContentPane().removeAll();

        setTitle("Welcome to JU Bank");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel label = new JLabel("Welcome to JU Bank", JLabel.CENTER);
        panel.add(label);

        JButton createAccButton = new JButton("Create Account");
        createAccButton.addActionListener(e -> createAccount());
        panel.add(createAccButton);

        JButton withdrawButton = new JButton("Withdraw Money");
        withdrawButton.addActionListener(e -> withdrawMoney());
        panel.add(withdrawButton);

        JButton transferButton = new JButton("Transfer Funds");
        transferButton.addActionListener(e -> transferFunds());
        panel.add(transferButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(e -> checkBalance());
        panel.add(checkBalanceButton);

        add(panel);

        revalidate();
        repaint();
        setVisible(true);
    }

    private void createAccount() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 2));

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField();
        JLabel balanceLabel = new JLabel("Initial Balance:");
        JTextField balanceField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton createButton = new JButton("Create");

        createButton.addActionListener(e -> {
            int accNumber = Integer.parseInt(accNumberField.getText());
            double balance = Double.parseDouble(balanceField.getText());
            String password = new String(passwordField.getPassword());

            try {
                accountMap.addAccount(accNumber, balance, password);
                AccountMapSerializer.serializeAccountMap(accountMap, FILE_NAME);
                JOptionPane.showMessageDialog(this, "Account created successfully.");
                initUI(); // Show initial window again
            } catch (IllegalArgumentException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(accNumberLabel);
        add(accNumberField);
        add(balanceLabel);
        add(balanceField);
        add(passwordLabel);
        add(passwordField);
        add(createButton);

        revalidate();
        repaint();
    }

    private void withdrawMoney() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 2));

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton withdrawButton = new JButton("Withdraw");

        withdrawButton.addActionListener(e -> {
            int accNumber = Integer.parseInt(accNumberField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String password = new String(passwordField.getPassword());

            try {
                accountMap.withdrawFromAccount(accNumber, amount, password);
                AccountMapSerializer.serializeAccountMap(accountMap, FILE_NAME);
                JOptionPane.showMessageDialog(this, "Withdrawal successful.");
                initUI(); // Show initial window again
            } catch (IllegalArgumentException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(accNumberLabel);
        add(accNumberField);
        add(amountLabel);
        add(amountField);
        add(passwordLabel);
        add(passwordField);
        add(withdrawButton);

        revalidate();
        repaint();
    }

    private void transferFunds() {
        getContentPane().removeAll();
        setLayout(new GridLayout(5, 2));

        JLabel fromAccNumberLabel = new JLabel("From Account Number:");
        JTextField fromAccNumberField = new JTextField();
        JLabel toAccNumberLabel = new JLabel("To Account Number:");
        JTextField toAccNumberField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton transferButton = new JButton("Transfer");

        transferButton.addActionListener(e -> {
            int fromAccNumber = Integer.parseInt(fromAccNumberField.getText());
            int toAccNumber = Integer.parseInt(toAccNumberField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String password = new String(passwordField.getPassword());

            try {
                accountMap.transferMoney(fromAccNumber, toAccNumber, amount, password);
                AccountMapSerializer.serializeAccountMap(accountMap, FILE_NAME);
                JOptionPane.showMessageDialog(this, "Transfer successful.");
                initUI(); // Show initial window again
            } catch (IllegalArgumentException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(fromAccNumberLabel);
        add(fromAccNumberField);
        add(toAccNumberLabel);
        add(toAccNumberField);
        add(amountLabel);
        add(amountField);
        add(passwordLabel);
        add(passwordField);
        add(transferButton);

        revalidate();
        repaint();
    }

    private void checkBalance() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 2));

        JLabel accNumberLabel = new JLabel("Account Number:");
        JTextField accNumberField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton checkButton = new JButton("Check Balance");

        checkButton.addActionListener(e -> {
            int accNumber = Integer.parseInt(accNumberField.getText());
            String password = new String(passwordField.getPassword());

            try {
                Account account = accountMap.getAccount(accNumber);
                if (account.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Balance: " + account.getBalance());
                } else {
                    throw new IllegalArgumentException("Incorrect password");
                }
                initUI(); // Show initial window again
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(accNumberLabel);
        add(accNumberField);
        add(passwordLabel);
        add(passwordField);
        add(checkButton);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankGUI::new);
    }
}