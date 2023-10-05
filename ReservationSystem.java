import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Reservation {
    private String pnr;
    private String name;
    private String trainNumber;
    private String classType;
    private String dateOfJourney;
    private String departure;
    private String destination;

    public Reservation(String pnr, String name, String trainNumber, String classType, String dateOfJourney, String departure, String destination) {
        this.pnr = pnr;
        this.name = name;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.departure = departure;
        this.destination = destination;
    }

    public String toString() {
        return "PNR Number: " + pnr + "\n" +
                "Passenger Name: " + name + "\n" +
                "Train Number: " + trainNumber + "\n" +
                "Class Type: " + classType + "\n" +
                "Date of Journey: " + dateOfJourney + "\n" +
                "Departure: " + departure + "\n" +
                "Destination: " + destination;
    }
}

public class ReservationSystem {
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password";

    private JFrame loginFrame;
    private JFrame mainFrame;
    private JFrame makeReservationFrame;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame reservationFormFrame;

    private JTextField nameField;
    private JTextField trainNumberField;
    private JComboBox<String> classTypeComboBox;
    private JTextField dateOfJourneyField;
    private JTextField departureField;
    private JTextField destinationField;
    private JTextField pnrField;
    private JFrame cancellationFormFrame;

    private Map<String, Reservation> reservations = new HashMap<>(); // Store reservations with PNR as the key

    public ReservationSystem() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel()); 
        loginFrame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (isValidLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");

                    loginFrame.dispose();
                    showReservationSystem();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Login failed.");
                }
            }
        });

        loginFrame.setVisible(true);
        mainFrame.setVisible(true);
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD);
    }

    private void showReservationSystem() {
        mainFrame = new JFrame("Reservation System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to the Reservation System");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton makeReservationButton = new JButton("Make a Reservation");
        JButton cancelReservationButton = new JButton("Cancel a Reservation");

        mainFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(makeReservationButton);
        buttonPanel.add(cancelReservationButton);

        mainFrame.add(buttonPanel, BorderLayout.CENTER);

        makeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMakeReservationScreen();
            }
        });

        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCancellationForm();
            }
        });

        mainFrame.setVisible(true);
    }


    private void openMakeReservationScreen() {
        reservationFormFrame = new JFrame("Reservation Form");
        reservationFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reservationFormFrame.setSize(400, 300);
        reservationFormFrame.setLayout(new BorderLayout());

        JLabel reservationFormLabel = new JLabel("Reservation Form");
        reservationFormLabel.setHorizontalAlignment(JLabel.CENTER);
        reservationFormLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel reservationFormPanel = new JPanel();
        reservationFormPanel.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel trainNumberLabel = new JLabel("Train Number:");
        trainNumberField = new JTextField();
        JLabel classTypeLabel = new JLabel("Class Type:");
        String[] classTypes = {"First Class", "Second Class", "Third Class"};
        classTypeComboBox = new JComboBox<>(classTypes);
        JLabel dateOfJourneyLabel = new JLabel("Date of Journey:");
        dateOfJourneyField = new JTextField();
        JLabel departureLabel = new JLabel("Departure:");
        departureField = new JTextField();
        JLabel destinationLabel = new JLabel("Destination:");
        destinationField = new JTextField();

        JButton insertButton = new JButton("Insert");

        reservationFormPanel.add(nameLabel);
        reservationFormPanel.add(nameField);
        reservationFormPanel.add(trainNumberLabel);
        reservationFormPanel.add(trainNumberField);
        reservationFormPanel.add(classTypeLabel);
        reservationFormPanel.add(classTypeComboBox);
        reservationFormPanel.add(dateOfJourneyLabel);
        reservationFormPanel.add(dateOfJourneyField);
        reservationFormPanel.add(departureLabel);
        reservationFormPanel.add(departureField);
        reservationFormPanel.add(destinationLabel);
        reservationFormPanel.add(destinationField);
        reservationFormPanel.add(new JLabel()); 
        reservationFormPanel.add(insertButton);


        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String trainNumber = trainNumberField.getText();
                String classType = (String) classTypeComboBox.getSelectedItem();
                String dateOfJourney = dateOfJourneyField.getText();
                String departure = departureField.getText();
                String destination = destinationField.getText();

                String pnr = generateUniquePNR();
                Reservation reservation = new Reservation(pnr, name, trainNumber, classType, dateOfJourney, departure, destination);
                reservations.put(pnr, reservation);

                JOptionPane.showMessageDialog(reservationFormFrame, "Reservation data saved! PNR: " + pnr);
                clearReservationFormFields();
            }
        });

        reservationFormFrame.add(reservationFormLabel, BorderLayout.NORTH);
        reservationFormFrame.add(reservationFormPanel, BorderLayout.CENTER);

        reservationFormFrame.setVisible(true);
    }
    

    private String generateUniquePNR() {
        
        return String.valueOf(System.currentTimeMillis());
    }

    private void clearReservationFormFields() {
        nameField.setText("");
        trainNumberField.setText("");
        classTypeComboBox.setSelectedIndex(0);
        dateOfJourneyField.setText("");
        departureField.setText("");
        destinationField.setText("");
    }

    private void openCancellationForm() {
        cancellationFormFrame = new JFrame("Cancellation Form");
        cancellationFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cancellationFormFrame.setSize(400, 150);
        cancellationFormFrame.setLayout(new BorderLayout());

        JLabel cancellationFormLabel = new JLabel("Cancellation Form");
       

        JPanel cancellationFormPanel = new JPanel();
        cancellationFormPanel.setLayout(new GridLayout(2, 2));

        JLabel pnrLabel = new JLabel("Enter PNR Number:");
        pnrField = new JTextField();
        JButton checkButton = new JButton("Check");
        JButton okButton = new JButton("OK");

        cancellationFormPanel.add(pnrLabel);
        cancellationFormPanel.add(pnrField);
        cancellationFormPanel.add(checkButton);
        cancellationFormPanel.add(okButton);


        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String pnr = pnrField.getText();
                if (reservations.containsKey(pnr)) {
                    
                    Reservation reservation = reservations.get(pnr);
                    String reservationInfo = reservation.toString();

                    JOptionPane.showMessageDialog(cancellationFormFrame, "Reservation Information:\n" + reservationInfo);
                } else {
                    
                    JOptionPane.showMessageDialog(cancellationFormFrame, "No reservation found with this PNR.");
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                String pnr = pnrField.getText();

                
                if (reservations.containsKey(pnr)) {
                    
                    reservations.remove(pnr);
                    JOptionPane.showMessageDialog(cancellationFormFrame, "Cancellation confirmed!");

                    
                    pnrField.setText("");
                } else {
                    
                    JOptionPane.showMessageDialog(cancellationFormFrame, "No reservation found with this PNR.");
                }
            }
        });
        cancellationFormFrame.add(cancellationFormLabel, BorderLayout.NORTH);
        cancellationFormFrame.add(cancellationFormPanel, BorderLayout.CENTER);

        cancellationFormFrame.setVisible(true);
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReservationSystem();
            }
        });
    }
}
