import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class MyFrame extends JFrame implements ActionListener {
    JButton withdrawButton, depositButton, transferButton, viewHistoryButton, quitButton;
    JTextField amountField, pinField, recipientNumberField; 
    JTextArea displayArea;
    double accountBalance = 10000.0;
    int userPin = 1122;
    List<String> transactionHistory = new ArrayList<>();

    MyFrame() {
        super("ATM Interface");

        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        JPanel inputPanel = new JPanel(new GridLayout(3, 2)); 

        amountField = new JTextField(10);
        pinField = new JTextField(10);
        recipientNumberField = new JTextField(10); 
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        transferButton = new JButton("Transfer");
        buttonPanel.add(transferButton);

        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("PIN:"));
        inputPanel.add(pinField);
        inputPanel.add(new JLabel("Recipient Number:")); 
        inputPanel.add(recipientNumberField); 
        viewHistoryButton = new JButton("View History");
        quitButton = new JButton("Quit");

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(quitButton);

        contentPanel.add(buttonPanel, BorderLayout.WEST);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        add(contentPanel);

        withdrawButton.addActionListener(this);
        depositButton.addActionListener(this);
        transferButton.addActionListener(this);
        viewHistoryButton.addActionListener(this);
        quitButton.addActionListener(this);

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

       public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            double amount = Double.parseDouble(amountField.getText());
            int pin = Integer.parseInt(pinField.getText());
            if (pin == userPin && amount <= accountBalance && amount > 0) {
                accountBalance -= amount;
                transactionHistory.add("Withdraw: $" + amount);
                displayArea.setText("Withdrawn $" + amount + ". New Balance: $" + accountBalance);
            } else if (pin != userPin) {
                displayArea.setText("Invalid PIN.");
            } else {
                displayArea.setText("Invalid withdrawal amount.");
            }
        } else if (e.getSource() == depositButton) {
            double amount = Double.parseDouble(amountField.getText());
            int pin = Integer.parseInt(pinField.getText());
            if (pin == userPin && amount > 0) {
                accountBalance += amount;
                transactionHistory.add("Deposit: $" + amount);
                displayArea.setText("Deposited $" + amount + ". New Balance: $" + accountBalance);
            } else if (pin != userPin) {
                displayArea.setText("Invalid PIN.");
            } else {
                displayArea.setText("Invalid deposit amount.");
            }
        } else if (e.getSource() == transferButton) {
            double amountToTransfer = Double.parseDouble(amountField.getText());
            int senderPin = Integer.parseInt(pinField.getText());
            String recipientNumber = recipientNumberField.getText(); 
            if (senderPin == userPin && amountToTransfer <= accountBalance && amountToTransfer > 0) {
                accountBalance -= amountToTransfer;
                transactionHistory.add("Transfer to " + recipientNumber + ": $" + amountToTransfer);

                displayArea.setText("Transferred $" + amountToTransfer + " to " + recipientNumber + ". New Balance: $" + accountBalance);
            } else if (senderPin != userPin) {
                displayArea.setText("Invalid PIN.");
            } else {
                displayArea.setText("Invalid transfer amount or insufficient balance.");
            }
        } else if (e.getSource() == viewHistoryButton) {
            displayTransactionHistory();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }

    private void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            displayArea.setText("No transaction history available.");
        } else {
            StringBuilder historyText = new StringBuilder("Transaction History:\n");
            for (String transaction : transactionHistory) {
                historyText.append(transaction).append("\n");
            }
            displayArea.setText(historyText.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyFrame();
            }
        });
    }
}

public class ATMApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyFrame();
            }
        });
    }
}

