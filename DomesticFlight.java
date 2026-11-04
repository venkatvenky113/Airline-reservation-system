import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class DomesticFlight extends JFrame {
    // UI components
    private JComboBox<String> CBFrom, CBTo, CBClass, CBAdult, CBChildren, CBInfant;
    private JLabel LFrom, LTo, LBookingDate, LClass, LAdult, LChildren, LInfant, LBookingDetails,
            LPassengerDetails, LDate, LImg1, LImg2, LNotes;
    private JTextField TFBookingDate;
    private JButton BFindFlight;
    private JPanel PPanel1, PPanel2;

    // auth-aware greeting
    private final String loggedInUser;

    // Fare data (made public to avoid “exporting non-public type” warning)
    public static class FareData {
        // row1 = economic fares, row3 = business fares
        // { From, To, Price, Time }
        final String[][] row1 = {
                {"Trivandrum", "Bangalore", "3000", "06:20"},
                {"Trivandrum", "Chennai",   "2800", "08:10"},
                {"Trivandrum", "Delhi",     "6500", "12:45"},
                {"Trivandrum", "Goa",       "4200", "09:15"},
                {"Trivandrum", "Hyderabad", "5000", "14:30"},
                {"Trivandrum", "Kolkata",   "7200", "10:00"},
                {"Trivandrum", "Lucknow",   "6900", "16:05"},
                {"Trivandrum", "Mumbai",    "5500", "18:40"},
                {"Trivandrum", "Vishakapatnam", "6100", "20:25"}
        };
        final String[][] row3 = {
                {"Trivandrum", "Bangalore", "6000", "06:20"},
                {"Trivandrum", "Chennai",   "5600", "08:10"},
                {"Trivandrum", "Delhi",     "12000","12:45"},
                {"Trivandrum", "Goa",       "8000", "09:15"},
                {"Trivandrum", "Hyderabad", "9500", "14:30"},
                {"Trivandrum", "Kolkata",   "13000","10:00"},
                {"Trivandrum", "Lucknow",   "12500","16:05"},
                {"Trivandrum", "Mumbai",    "11000","18:40"},
                {"Trivandrum", "Vishakapatnam", "11800", "20:25"}
        };
    }

    private final FareData fareData; // provides fare tables

    public DomesticFlight(FareData fareData, String loggedInUser) {
        super("Domestic Flight Booking");
        this.fareData = (fareData == null ? new FareData() : fareData);
        this.loggedInUser = (loggedInUser == null ? "Guest" : loggedInUser);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        String[] sItem1 = {"Trivandrum,Hyderabad"};
        String[] sItem2 = {"Bangalore", "Chennai", "Delhi", "Goa", "Hyderabad", "Kolkata",
                "Lucknow", "Mumbai", "Vishakapatnam"};
        String[] sItem3 = {"Economic", "Business"};

        // Left panel - booking details
        PPanel1 = new JPanel(null);
        PPanel1.setPreferredSize(new Dimension(500, 300));

        LBookingDetails = new JLabel("<html><b><font color=\"#C71585\">Booking Details</font></b></html>");
        LFrom = new JLabel("From          :");
        LTo = new JLabel("To               :");
        LBookingDate = new JLabel("Booking Date:");
        LClass = new JLabel("Class         :");

        CBFrom = new JComboBox<>(sItem1);
        CBTo = new JComboBox<>(sItem2);
        CBClass = new JComboBox<>(sItem3);
        TFBookingDate = new JTextField(10);
        LDate = new JLabel("(DD/MM/YYYY)");
        LDate.setForeground(Color.red);

        LImg1 = new JLabel(); // no external images; keep empty
        BFindFlight = new JButton("Find Flight");

        LBookingDetails.setBounds(20, 3, 200, 20);
        LFrom.setBounds(20, 40, 100, 20);
        CBFrom.setBounds(140, 40, 160, 24);
        LTo.setBounds(20, 80, 100, 20);
        CBTo.setBounds(140, 80, 160, 24);
        LBookingDate.setBounds(20, 120, 100, 20);
        TFBookingDate.setBounds(140, 120, 160, 24);
        LDate.setBounds(310, 120, 120, 20);
        LClass.setBounds(20, 160, 100, 20);
        CBClass.setBounds(140, 160, 160, 24);
        BFindFlight.setBounds(140, 200, 160, 30);
        LImg1.setBounds(0, 240, 495, 60);

        PPanel1.add(LBookingDetails);
        PPanel1.add(LFrom);
        PPanel1.add(CBFrom);
        PPanel1.add(LTo);
        PPanel1.add(CBTo);
        PPanel1.add(LBookingDate);
        PPanel1.add(TFBookingDate);
        PPanel1.add(LDate);
        PPanel1.add(LClass);
        PPanel1.add(CBClass);
        PPanel1.add(BFindFlight);
        PPanel1.add(LImg1);
        PPanel1.setBackground(Color.white);
        c.add(PPanel1, BorderLayout.WEST);

        // Right panel - passenger details & note
        PPanel2 = new JPanel(null);
        PPanel2.setPreferredSize(new Dimension(300, 300));

        LPassengerDetails = new JLabel("<html><b><font color=\"#C71585\">Passenger Details</font></b></html>");
        LAdult = new JLabel("Adults(12+):");
        LChildren = new JLabel("Children(2-11):");
        LInfant = new JLabel("Infants(<2):");

        String[] item4 = {"1", "2", "3", "4", "5", "6"};
        CBAdult = new JComboBox<>(item4);
        String[] item5 = {"0", "1", "2", "3", "4"};
        CBChildren = new JComboBox<>(item5);
        String[] item6 = {"0", "1", "2", "3"};
        CBInfant = new JComboBox<>(item6);

        LImg2 = new JLabel();
        LNotes = new JLabel("<html><body style='width:240px'>"
                + "Logged in as: <b>" + this.loggedInUser + "</b><br/><br/>"
                + "NOTE: Demo app. No real bookings or payments are made."
                + "</body></html>");

        LPassengerDetails.setBounds(20, 3, 200, 20);
        LAdult.setBounds(20, 40, 120, 20);
        CBAdult.setBounds(160, 40, 100, 24);
        LChildren.setBounds(20, 80, 120, 20);
        CBChildren.setBounds(160, 80, 100, 24);
        LInfant.setBounds(20, 120, 120, 20);
        CBInfant.setBounds(160, 120, 100, 24);
        LImg2.setBounds(16, 160, 260, 20);
        LNotes.setBounds(20, 180, 260, 100);

        PPanel2.add(LPassengerDetails);
        PPanel2.add(LAdult);
        PPanel2.add(LChildren);
        PPanel2.add(LInfant);
        PPanel2.add(CBAdult);
        PPanel2.add(CBChildren);
        PPanel2.add(CBInfant);
        PPanel2.add(LNotes);
        PPanel2.add(LImg2);
        PPanel2.setBackground(Color.white);
        c.add(PPanel2, BorderLayout.EAST);

        // top bar with logout
        JMenuBar bar = new JMenuBar();
        JMenu account = new JMenu("Account");
        JMenuItem logout = new JMenuItem("Logout & Exit");
        logout.addActionListener(ae -> {
            int opt = JOptionPane.showConfirmDialog(this, "Logout and exit?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) System.exit(0);
        });
        account.add(logout);
        bar.add(account);
        setJMenuBar(bar);

        setSize(800, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        BFindFlight.addActionListener(new ButtonFind(this, this.fareData));
    }

    // Action listener to compute fare and show result
    static class ButtonFind implements ActionListener {
        private final DomesticFlight type;
        private final FareData fareData;
        ButtonFind(DomesticFlight type, FareData fareData) {
            this.type = type;
            this.fareData = fareData;
        }
        public void actionPerformed(ActionEvent e) {
            String sFrom = (String) type.CBFrom.getSelectedItem();
            String sTo = (String) type.CBTo.getSelectedItem();
            String sClass = (String) type.CBClass.getSelectedItem();
            String sBookingDate = type.TFBookingDate.getText().trim();

            int pricePerAdult = 0;
            String sTime = "";

            String[][] table = sClass.equals("Economic") ? fareData.row1 : fareData.row3;
            for (String[] row : table) {
                if (row[0].equals(sFrom) && row[1].equals(sTo)) {
                    pricePerAdult = Integer.parseInt(row[2]);
                    sTime = row[3];
                    break;
                }
            }

            if (pricePerAdult == 0) {
                JOptionPane.showMessageDialog(null, "No flight found for the selected route.");
                return;
            }

            int iAdult = Integer.parseInt((String) type.CBAdult.getSelectedItem());
            int iChildren = Integer.parseInt((String) type.CBChildren.getSelectedItem());
            int iInfant = Integer.parseInt((String) type.CBInfant.getSelectedItem());

            // Simple pricing: child = 50% of adult, infant = free
            int totalFare = pricePerAdult * iAdult + (pricePerAdult / 2) * iChildren;

            type.setTitle("Dep: " + sTime + " | " + sClass + " | " + sFrom + " → " + sTo + " | User: " + type.loggedInUser);

            String dateText = sBookingDate.isEmpty() ? "(no date provided)" : sBookingDate;
            String message = "Flight found!\n"
                    + "Date: " + dateText + "\n"
                    + "From: " + sFrom + "\n"
                    + "To: " + sTo + "\n"
                    + "Class: " + sClass + "\n"
                    + "Departure: " + sTime + "\n"
                    + "Passengers: " + iAdult + " adult(s), " + iChildren + " child(ren), " + iInfant + " infant(s)\n"
                    + "Total fare: ₹" + totalFare + "\n\n"
                    + "Proceed with (simulated) booking?";

            int choice = JOptionPane.showConfirmDialog(type, message, "Confirm Booking", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(type, "Ticket booked! (demo)\nPNR: " + generatePNR(sFrom, sTo));
            }
        }

        private String generatePNR(String from, String to) {
            String seed = (from + to + System.currentTimeMillis());
            int h = seed.hashCode();
            String hex = Integer.toHexString(h).toUpperCase();
            if (hex.length() > 6) hex = hex.substring(0, 6);
            return hex;
        }
    }

    // very simple auth service (in-memory demo)
    static class AuthService {
        private static final Map<String, String> USERS = new HashMap<>();
        static {
            USERS.put("admin", "admin123");
            USERS.put("user",  "user123");
            USERS.put("guest", "guest");
        }
        static boolean verify(String username, char[] password) {
            if (username == null) return false;
            String expected = USERS.get(username.trim());
            boolean ok = expected != null && expected.equals(new String(password));
            java.util.Arrays.fill(password, '\0'); // hygiene
            return ok;
        }
    }

    // modal login dialog
    static class LoginDialog extends JDialog {
        private boolean authenticated = false;
        private String username = null;

        private final JTextField tfUser = new JTextField(15);
        private final JPasswordField pfPass = new JPasswordField(15);
        private final JCheckBox cbShow = new JCheckBox("Show password");

        LoginDialog(Frame owner) {
            super(owner, "Sign in", true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(6, 6, 6, 6);
            gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.EAST;
            form.add(new JLabel("Username:"), gc);
            gc.gridx = 1; gc.anchor = GridBagConstraints.WEST;
            form.add(tfUser, gc);

            gc.gridx = 0; gc.gridy = 1; gc.anchor = GridBagConstraints.EAST;
            form.add(new JLabel("Password:"), gc);
            gc.gridx = 1; gc.anchor = GridBagConstraints.WEST;
            form.add(pfPass, gc);

            gc.gridx = 1; gc.gridy = 2; gc.anchor = GridBagConstraints.WEST;
            form.add(cbShow, gc);

            JButton bLogin = new JButton("Login");
            JButton bCancel = new JButton("Cancel");

            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttons.add(bCancel);
            buttons.add(bLogin);

            // FIX: use the current field's echo char as default
            final char defaultEcho = pfPass.getEchoChar();
            cbShow.addActionListener(e ->
                pfPass.setEchoChar(cbShow.isSelected() ? (char) 0 : defaultEcho)
            );

            bLogin.addActionListener(e -> doLogin());
            bCancel.addActionListener(e -> {
                authenticated = false;
                dispose();
            });

            getContentPane().setLayout(new BorderLayout(10, 10));
            getContentPane().add(form, BorderLayout.CENTER);
            getContentPane().add(buttons, BorderLayout.SOUTH);

            pack();
            setResizable(false);
            setLocationRelativeTo(owner);
        }

        private void doLogin() {
            String u = tfUser.getText().trim();
            char[] p = pfPass.getPassword();
            if (AuthService.verify(u, p)) {
                authenticated = true;
                username = u;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login failed", JOptionPane.ERROR_MESSAGE);
                pfPass.setText("");
                pfPass.requestFocusInWindow();
            }
        }

        boolean isAuthenticated() { return authenticated; }
        String getUsername() { return username; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Show login first
            LoginDialog dialog = new LoginDialog(null);
            dialog.setVisible(true);

            if (!dialog.isAuthenticated()) {
                // exit if user cancelled or failed
                System.exit(0);
            }

            // Launch main app with logged in user
            new DomesticFlight(null, dialog.getUsername());
        });
    }
}
