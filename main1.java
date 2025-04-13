
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/subh", "root", "1234");

            // Frame Setup
            JFrame frame = new JFrame("Hospital Management System");
            frame.setSize(950, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Left Panel - Input Form
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(null);
            leftPanel.setPreferredSize(new Dimension(450, 600));
            leftPanel.setBackground(new Color(230, 245, 255));  // light blue tone

            JLabel titleLabel = new JLabel("Enter Patient & Doctor Details");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setBounds(60, 20, 300, 30);
            leftPanel.add(titleLabel);

            JLabel l1 = new JLabel("Patient Name:");
            l1.setBounds(30, 80, 120, 25);
            JTextField lt1 = new JTextField();
            lt1.setBounds(160, 80, 200, 30);

            JLabel l2 = new JLabel("Patient Gender:");
            l2.setBounds(30, 130, 120, 25);
            JTextField lt2 = new JTextField();
            lt2.setBounds(160, 130, 200, 30);

            JLabel l3 = new JLabel("Doctor Name:");
            l3.setBounds(30, 180, 120, 25);
            JTextField lt3 = new JTextField();
            lt3.setBounds(160, 180, 200, 30);

            JLabel l4 = new JLabel("Doctor Specialization:");
            l4.setBounds(30, 230, 150, 25);
            JTextField lt4 = new JTextField();
            lt4.setBounds(160, 230, 200, 30);

            JLabel l5 = new JLabel("Appointment Date:");
            l5.setBounds(30, 280, 150, 25);
            JTextField lt5 = new JTextField("YYYY-MM-DD");
            lt5.setBounds(160, 280, 200, 30);

            JLabel l6 = new JLabel("Status:");
            l6.setBounds(30, 330, 120, 25);
            JTextField lt6 = new JTextField("Scheduled");
            lt6.setBounds(160, 330, 200, 30);

            JButton btn1 = new JButton("Insert Details");
            btn1.setBounds(140, 400, 150, 35);
            btn1.setBackground(new Color(60, 179, 113)); // modern green
            btn1.setForeground(Color.WHITE);
            btn1.setFont(new Font("Arial", Font.BOLD, 14));

            leftPanel.add(l1); leftPanel.add(lt1);
            leftPanel.add(l2); leftPanel.add(lt2);
            leftPanel.add(l3); leftPanel.add(lt3);
            leftPanel.add(l4); leftPanel.add(lt4);
            leftPanel.add(l5); leftPanel.add(lt5);
            leftPanel.add(l6); leftPanel.add(lt6);
            leftPanel.add(btn1);

            // btn1 ActionListener
            btn1.addActionListener(e1 -> {
                try {
                    String pname = lt1.getText();
                    String pgender = lt2.getText();
                    String dname = lt3.getText();
                    String dspec = lt4.getText();
                    String appdate = lt5.getText();
                    String status = lt6.getText();

                    // Insert into doctors
                    String query1 = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
                    PreparedStatement pmst1 = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
                    pmst1.setString(1, dname);
                    pmst1.setString(2, dspec);
                    pmst1.executeUpdate();
                    ResultSet r1 = pmst1.getGeneratedKeys();
                    int d_id = 0;
                    if (r1.next()) d_id = r1.getInt(1);

                    // Insert into patients
                    String query2 = "INSERT INTO patients(name, gender) VALUES(?, ?)";
                    PreparedStatement pmst2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                    pmst2.setString(1, pname);
                    pmst2.setString(2, pgender);
                    pmst2.executeUpdate();
                    ResultSet r2 = pmst2.getGeneratedKeys();
                    int p_id = 0;
                    if (r2.next()) p_id = r2.getInt(1);

                    // Insert into appointments
                    String query3 = "INSERT INTO appointments(patient_id, doctor_id, appointment_date, status) VALUES(?,?,?,?)";
                    PreparedStatement pmst3 = conn.prepareStatement(query3);
                    pmst3.setInt(1, p_id);
                    pmst3.setInt(2, d_id);
                    pmst3.setString(3, appdate);
                    pmst3.setString(4, status);
                    pmst3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Inserted Successfully!");

                    // Reset fields
                    lt1.setText("");
                    lt2.setText("");
                    lt3.setText("");
                    lt4.setText("");
                    lt5.setText("YYYY-MM-DD");
                    lt6.setText("Scheduled");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            });

            // Right Panel - Display Area
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(null);
            rightPanel.setBackground(new Color(245, 245, 245));  // subtle light gray

            JLabel displayLabel = new JLabel("Appointment Display Area");
            displayLabel.setFont(new Font("Arial", Font.BOLD, 18));
            displayLabel.setBounds(50, 30, 300, 30);
            rightPanel.add(displayLabel);

            JTextArea displayArea = new JTextArea("Appointment details will appear here...");
            displayArea.setEditable(false);
            displayArea.setLineWrap(true);
            displayArea.setWrapStyleWord(true);
            displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

            JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setBounds(50, 80, 400, 400);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            rightPanel.add(scrollPane);

            // Split Pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
            splitPane.setDividerLocation(450);
            splitPane.setEnabled(false);

            frame.add(splitPane, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
