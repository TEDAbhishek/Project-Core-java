import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class SkyReserve {
    private ArrayList<Flight> flights;
    private ArrayList<Reservation> reservations;
    private int reservationCounter;
    private JFrame frame;
    private JTextArea displayArea;
    private static final String ADMIN_USERNAME = "Arsh2005";
    private static final String ADMIN_PASSWORD = "Skyreserve@25";

    public SkyReserve() {
        flights = new ArrayList<>();
        reservations = new ArrayList<>();
        reservationCounter = 1000;
        initializeFlights();
        createGUI();
    }

    private void initializeFlights() {
        flights.add(new Flight("SR101", "New York", "London", "2025-03-01 08:00", 150, 299.99));
        flights.add(new Flight("SR102", "London", "Paris", "2025-03-01 14:00", 100, 149.99));
        flights.add(new Flight("SR103", "Paris", "New York", "2025-03-02 10:00", 150, 319.99));
        flights.add(new Flight("SR104", "Los Angeles", "Tokyo", "2025-03-02 15:00", 200, 599.99));
    }

    public void displayAvailableFlights() {
        StringBuilder sb = new StringBuilder("Available Flights:\n\n");
        for (Flight flight : flights) {
            if (flight.getAvailableSeats() > 0) {
                sb.append(flight.toString()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }

    public Flight findFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    public Reservation makeReservation(Passenger passenger, String flightNumber) {
        Flight flight = findFlight(flightNumber);
        if (flight != null && flight.bookSeat()) {
            String reservationId = "RES" + reservationCounter++;
            Reservation reservation = new Reservation(reservationId, flight, passenger,
                                                      LocalDate.now().toString());
            reservations.add(reservation);
            return reservation;
        }
        return null;
    }

    public void displayReservation(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                displayArea.setText(reservation.toString());
                return;
            }
        }
        displayArea.setText("Reservation not found!");
    }

    private void createGUI() {
        frame = new JFrame("SkyReserve Airline System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        // Create background panel with sky image
        BackgroundPanel backgroundPanel = new BackgroundPanel("sky.jpg");
        backgroundPanel.setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false); // Transparent to show background
        JLabel titleLabel = new JLabel("✈ SkyReserve");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        // Display area
        displayArea = new JTextArea(15, 50);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        displayArea.setBorder(BorderFactory.createLineBorder(new Color(135, 206, 235), 2));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent to show background
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton viewFlightsBtn = createStyledButton("View Flights", new Color(30, 144, 255));
        JButton bookFlightBtn = createStyledButton("Book Flight", new Color(34, 139, 34));
        JButton viewReservationBtn = createStyledButton("View Reservation", new Color(255, 165, 0));
        JButton adminBtn = createStyledButton("Admin Dashboard", new Color(128, 0, 128));
        JButton exitBtn = createStyledButton("Exit", new Color(220, 20, 60));

        buttonPanel.add(viewFlightsBtn);
        buttonPanel.add(bookFlightBtn);
        buttonPanel.add(viewReservationBtn);
        buttonPanel.add(adminBtn);
        buttonPanel.add(exitBtn);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(backgroundPanel);

        // Action listeners
        viewFlightsBtn.addActionListener(e -> displayAvailableFlights());
        bookFlightBtn.addActionListener(e -> {
            JPanel bookingPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            bookingPanel.setBackground(new Color(245, 245, 220));
            bookingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField flightField = new JTextField(10);
            JTextField nameField = new JTextField(20);
            JTextField passportField = new JTextField(15);
            JTextField contactField = new JTextField(15);

            bookingPanel.add(createStyledLabel("Flight Number:"));
            bookingPanel.add(flightField);
            bookingPanel.add(createStyledLabel("Passenger Name:"));
            bookingPanel.add(nameField);
            bookingPanel.add(createStyledLabel("Passport Number:"));
            bookingPanel.add(passportField);
            bookingPanel.add(createStyledLabel("Contact Number:"));
            bookingPanel.add(contactField);

            int result = JOptionPane.showConfirmDialog(frame, bookingPanel,
                        "✈ Book a Flight", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Passenger passenger = new Passenger(
                    nameField.getText(),
                    passportField.getText(),
                    contactField.getText()
                );
                Reservation reservation = makeReservation(passenger, flightField.getText());
                if (reservation != null) {
                    displayArea.setText("✅ Booking successful!\n\n" + reservation.toString());
                } else {
                    displayArea.setText("❌ Booking failed! Check flight number and availability.");
                }
            }
        });
        viewReservationBtn.addActionListener(e -> {
            JTextField resIdField = new JTextField(10);
            JPanel resPanel = new JPanel();
            resPanel.add(new JLabel("Reservation ID:"));
            resPanel.add(resIdField);

            int result = JOptionPane.showConfirmDialog(frame, resPanel,
                        "🔍 View Reservation", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                displayReservation(resIdField.getText());
            }
        });
        adminBtn.addActionListener(e -> showAdminDashboard());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showAdminDashboard() {
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        loginPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(10);
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(10);
        loginPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(frame, loginPanel, "Admin Login", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                openAdminDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", 
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openAdminDashboard() {
        JDialog adminDialog = new JDialog(frame, "Admin Dashboard", true);
        adminDialog.setSize(400, 300);
        adminDialog.setLayout(new BorderLayout());

        JPanel addFlightPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        addFlightPanel.setBorder(BorderFactory.createTitledBorder("Add New Flight"));

        addFlightPanel.add(new JLabel("Flight Number:"));
        JTextField flightNumberField = new JTextField();
        addFlightPanel.add(flightNumberField);

        addFlightPanel.add(new JLabel("Origin:"));
        JTextField originField = new JTextField();
        addFlightPanel.add(originField);

        addFlightPanel.add(new JLabel("Destination:"));
        JTextField destinationField = new JTextField();
        addFlightPanel.add(destinationField);

        addFlightPanel.add(new JLabel("Departure Time:"));
        JTextField departureTimeField = new JTextField();
        addFlightPanel.add(departureTimeField);

        addFlightPanel.add(new JLabel("Total Seats:"));
        JTextField totalSeatsField = new JTextField();
        addFlightPanel.add(totalSeatsField);

        addFlightPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        addFlightPanel.add(priceField);

        JButton addButton = new JButton("Add Flight");
        addFlightPanel.add(addButton);

        JPanel deleteFlightPanel = new JPanel(new FlowLayout());
        deleteFlightPanel.setBorder(BorderFactory.createTitledBorder("Delete Flight"));

        JComboBox<String> flightComboBox = new JComboBox<>();
        for (Flight flight : flights) {
            flightComboBox.addItem(flight.getFlightNumber());
        }
        deleteFlightPanel.add(new JLabel("Select Flight:"));
        deleteFlightPanel.add(flightComboBox);

        JButton deleteButton = new JButton("Delete Flight");
        deleteFlightPanel.add(deleteButton);

        adminDialog.add(addFlightPanel, BorderLayout.CENTER);
        adminDialog.add(deleteFlightPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String flightNumber = flightNumberField.getText();
            if (findFlight(flightNumber) != null) {
                JOptionPane.showMessageDialog(adminDialog, "Flight number already exists.");
                return;
            }
            String origin = originField.getText();
            String destination = destinationField.getText();
            String departureTime = departureTimeField.getText();
            int totalSeats;
            double price;
            try {
                totalSeats = Integer.parseInt(totalSeatsField.getText());
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(adminDialog, "Invalid number for seats or price.");
                return;
            }
            Flight newFlight = new Flight(flightNumber, origin, destination, departureTime, totalSeats, price);
            flights.add(newFlight);
            flightComboBox.addItem(flightNumber);
            JOptionPane.showMessageDialog(adminDialog, "Flight added successfully.");
            flightNumberField.setText("");
            originField.setText("");
            destinationField.setText("");
            departureTimeField.setText("");
            totalSeatsField.setText("");
            priceField.setText("");
        });

        deleteButton.addActionListener(e -> {
            String selectedFlight = (String) flightComboBox.getSelectedItem();
            if (selectedFlight != null) {
                flights.removeIf(flight -> flight.getFlightNumber().equals(selectedFlight));
                flightComboBox.removeItem(selectedFlight);
                JOptionPane.showMessageDialog(adminDialog, "Flight deleted successfully.");
            }
        });

        adminDialog.setLocationRelativeTo(frame);
        adminDialog.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(120, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(25, 25, 112));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SkyReserve());
    }
}