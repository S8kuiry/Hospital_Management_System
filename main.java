
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/subh", "root", "1234");

            JFrame frame = new JFrame("Hospital Management System");
            frame.setSize(1000, 650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Left Panel
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(null);
            leftPanel.setPreferredSize(new Dimension(450, 650));
            leftPanel.setBackground(new Color(230, 245, 255));

            JLabel titleLabel = new JLabel("Patient & Doctor Info");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setBounds(120, 10, 250, 30);
            leftPanel.add(titleLabel);

            JLabel l1 = new JLabel("Patient Name:");
            l1.setBounds(30, 60, 120, 25);
            JTextField lt1 = new JTextField();
            lt1.setBounds(160, 60, 230, 30);

            JLabel l2 = new JLabel("Patient Gender:");
            l2.setBounds(30, 110, 120, 25);
            JTextField lt2 = new JTextField();
            lt2.setBounds(160, 110, 230, 30);

            JLabel l3 = new JLabel("Doctor Name:");
            l3.setBounds(30, 160, 120, 25);
            JTextField lt3 = new JTextField();
            lt3.setBounds(160, 160, 230, 30);

            JLabel l4 = new JLabel("Doctor Specialization:");
            l4.setBounds(30, 210, 150, 25);
            JTextField lt4 = new JTextField();
            lt4.setBounds(160, 210, 230, 30);

            JLabel l5 = new JLabel("Appointment Date:");
            l5.setBounds(30, 260, 150, 25);
            JTextField lt5 = new JTextField("YYYY-MM-DD");
            lt5.setBounds(160, 260, 230, 30);

            JLabel l6 = new JLabel("Status:");
            l6.setBounds(30, 310, 120, 25);
            JTextField lt6 = new JTextField("Scheduled");
            lt6.setBounds(160, 310, 230, 30);

            JLabel l8 = new JLabel("Patient ID:");
            l8.setBounds(30, 360, 120, 25);
            JTextField lt8 = new JTextField("Patient ID");
            lt8.setBounds(160, 360, 230, 30);

            JLabel l9 = new JLabel("Doctor ID:");
            l9.setBounds(30, 410, 120, 25);
            JTextField lt9 = new JTextField("Doctor ID");
            lt9.setBounds(160, 410, 230, 30);

            JLabel l10 = new JLabel("Appointment ID:");
            l10.setBounds(30, 460, 120, 25);
            JTextField lt10 = new JTextField("Appointment ID");
            lt10.setBounds(160, 460, 230, 30);

            JButton btn1 = new JButton("Insert Details");
            btn1.setBounds(60, 510, 150, 35);
            btn1.setBackground(new Color(0, 123, 255)); // Bootstrap Blue
            btn1.setForeground(Color.WHITE);
            btn1.setFocusPainted(false);
            btn1.setFont(new Font("Arial", Font.BOLD, 14));
            JButton btn2 = new JButton("Update Details");
            btn2.setBounds(230, 510, 150, 35);
            btn2.setBackground(new Color(40, 167, 69)); // Bootstrap Green
            btn2.setForeground(Color.WHITE);
            btn2.setFocusPainted(false);
            btn2.setFont(new Font("Arial", Font.BOLD, 14));

            leftPanel.add(l1);
            leftPanel.add(lt1);
            leftPanel.add(l2);
            leftPanel.add(lt2);
            leftPanel.add(l3);
            leftPanel.add(lt3);
            leftPanel.add(l4);
            leftPanel.add(lt4);
            leftPanel.add(l5);
            leftPanel.add(lt5);
            leftPanel.add(l6);
            leftPanel.add(lt6);
            leftPanel.add(l8);
            leftPanel.add(lt8);
            leftPanel.add(l9);
            leftPanel.add(lt9);
            leftPanel.add(l10);
            leftPanel.add(lt10);
            leftPanel.add(btn1);
            leftPanel.add(btn2);

            // Right Panel
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(null);
            rightPanel.setBackground(new Color(245, 245, 245));

            JLabel displayLabel = new JLabel("Appointment Search Area");
            displayLabel.setFont(new Font("Arial", Font.BOLD, 18));
            displayLabel.setBounds(50, 20, 300, 30);
            rightPanel.add(displayLabel);

            JLabel lblDoctor = new JLabel("Doctor Name:");
            lblDoctor.setBounds(50, 70, 100, 25);
            JTextField txtDoctor = new JTextField();
            txtDoctor.setBounds(160, 70, 150, 25);
            JButton btnDoctorSearch = new JButton("Search");
            btnDoctorSearch.setBounds(320, 70, 90, 25);
            rightPanel.add(lblDoctor);
            rightPanel.add(txtDoctor);
            rightPanel.add(btnDoctorSearch);

            JLabel lblPatient = new JLabel("Patient Name:");
            lblPatient.setBounds(50, 110, 100, 25);
            JTextField txtPatient = new JTextField();
            txtPatient.setBounds(160, 110, 150, 25);
            JButton btnPatientSearch = new JButton("Search");
            btnPatientSearch.setBounds(320, 110, 90, 25);
            rightPanel.add(lblPatient);
            rightPanel.add(txtPatient);
            rightPanel.add(btnPatientSearch);

            JLabel lblDate = new JLabel("Appointment Date:");
            lblDate.setBounds(50, 150, 120, 25);
            JTextField txtDate = new JTextField("YYYY-MM-DD");
            txtDate.setBounds(180, 150, 130, 25);
            JButton btnDateSearch = new JButton("Search");
            btnDateSearch.setBounds(320, 150, 90, 25);
            rightPanel.add(lblDate);
            rightPanel.add(txtDate);
            rightPanel.add(btnDateSearch);

            JTextArea displayArea = new JTextArea("Appointment details will appear here...");
            displayArea.setEditable(false);
            displayArea.setLineWrap(true);
            displayArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setBounds(50, 200, 420, 380);
            rightPanel.add(scrollPane);

            // Split Pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
            splitPane.setDividerLocation(470);
            splitPane.setEnabled(false);
            frame.add(splitPane, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Insert Button Logic
            btn1.addActionListener(e -> {
                try {
                    String pname = lt1.getText();
                    String pgender = lt2.getText();
                    String dname = lt3.getText();
                    String dspec = lt4.getText();
                    String appdate = lt5.getText();
                    String status = lt6.getText();

                    String q1 = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
                    PreparedStatement ps1 = conn.prepareStatement(q1, Statement.RETURN_GENERATED_KEYS);
                    ps1.setString(1, dname);
                    ps1.setString(2, dspec);
                    ps1.executeUpdate();
                    ResultSet r1 = ps1.getGeneratedKeys();
                    int did = 0;
                    if (r1.next()) {
                        did = r1.getInt(1);
                    }

                    String q2 = "INSERT INTO patients(name, gender) VALUES(?, ?)";
                    PreparedStatement ps2 = conn.prepareStatement(q2, Statement.RETURN_GENERATED_KEYS);
                    ps2.setString(1, pname);
                    ps2.setString(2, pgender);
                    ps2.executeUpdate();
                    ResultSet r2 = ps2.getGeneratedKeys();
                    int pid = 0;
                    if (r2.next()) {
                        pid = r2.getInt(1);
                    }

                    String q3 = "INSERT INTO appointments(patient_id, doctor_id, appointment_date, status) VALUES(?, ?, ?, ?)";
                    PreparedStatement ps3 = conn.prepareStatement(q3);
                    ps3.setInt(1, pid);
                    ps3.setInt(2, did);
                    ps3.setString(3, appdate);
                    ps3.setString(4, status);
                    ps3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Inserted Successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Insert Error: " + ex.getMessage());
                }
            });

            // Update Button Logic
            btn2.addActionListener(e -> {
                try {
                    int pid = Integer.parseInt(lt8.getText().trim());
                    int did = Integer.parseInt(lt9.getText().trim());
                    int aid = Integer.parseInt(lt10.getText().trim());

                    PreparedStatement ps1 = conn.prepareStatement("UPDATE patients SET name=?, gender=? WHERE patient_id=?");
                    ps1.setString(1, lt1.getText());
                    ps1.setString(2, lt2.getText());
                    ps1.setInt(3, pid);
                    ps1.executeUpdate();

                    PreparedStatement ps2 = conn.prepareStatement("UPDATE doctors SET name=?, specialization=? WHERE doctor_id=?");
                    ps2.setString(1, lt3.getText());
                    ps2.setString(2, lt4.getText());
                    ps2.setInt(3, did);
                    ps2.executeUpdate();

                    PreparedStatement ps3 = conn.prepareStatement("UPDATE appointments SET appointment_date=?, status=? WHERE appointment_id=?");
                    ps3.setString(1, lt5.getText());
                    ps3.setString(2, lt6.getText());
                    ps3.setInt(3, aid);
                    ps3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Updated Successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Update Error: " + ex.getMessage());
                }
            });

            // Search by Doctor Name
            btnDoctorSearch.addActionListener(e -> {
                try {
                    String name = txtDoctor.getText().trim();
                    String q = "SELECT d.name AS doctor_name, d.specialization, p.name AS patient_name, p.gender "
                            + "FROM doctors d "
                            + "JOIN appointments a ON d.doctor_id = a.doctor_id "
                            + "JOIN patients p ON a.patient_id = p.patient_id "
                            + "WHERE d.name LIKE ?";

                    PreparedStatement ps = conn.prepareStatement(q);
                    ps.setString(1, "%" + name + "%");
                    ResultSet rs = ps.executeQuery();
                    displayArea.setText("");
                    displayArea.setText("");
                    while (rs.next()) {
                        displayArea.append("Doctor: " + rs.getString("doctor_name")
                                + "\nSpecialization: " + rs.getString("specialization")
                                + "\nPatient: " + rs.getString("patient_name")
                                + "\nGender: " + rs.getString("gender") + "\n---------------------\n");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Search by Patient Name
            btnPatientSearch.addActionListener(e -> {
                try {
                    String name = txtPatient.getText().trim();
                    String q = "SELECT p.name AS patient_name, p.gender, d.name AS doctor_name, d.specialization "
                            + "FROM patients p "
                            + "JOIN appointments a ON p.patient_id = a.patient_id "
                            + "JOIN doctors d ON a.doctor_id = d.doctor_id "
                            + "WHERE p.name LIKE ?";

                    PreparedStatement ps = conn.prepareStatement(q);
                    ps.setString(1, "%" + name + "%");
                    ResultSet rs = ps.executeQuery();
                    displayArea.setText("");
                    displayArea.setText("");
                    while (rs.next()) {
                        displayArea.append("Patient: " + rs.getString("patient_name")
                                + "\nGender: " + rs.getString("gender")
                                + "\nDoctor: " + rs.getString("doctor_name")
                                + "\nSpecialization: " + rs.getString("specialization") + "\n---------------------\n");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            // Search by Appointment Date
            btnDateSearch.addActionListener(e -> {
                try {
                    String date = txtDate.getText().trim();
                    String q = "SELECT a.appointment_id, p.name AS patient_name, d.name AS doctor_name, a.status "
                            + "FROM appointments a "
                            + "JOIN patients p ON a.patient_id = p.patient_id "
                            + "JOIN doctors d ON a.doctor_id = d.doctor_id "
                            + "WHERE a.appointment_date = ?";
                    PreparedStatement ps = conn.prepareStatement(q);
                    ps.setString(1, date);
                    ResultSet rs = ps.executeQuery();
                    displayArea.setText("");
                    while (rs.next()) {
                        displayArea.append("Appointment ID: " + rs.getInt("appointment_id")
                                + "\nPatient: " + rs.getString("patient_name")
                                + "\nDoctor: " + rs.getString("doctor_name")
                                + "\nStatus: " + rs.getString("status") + "\n\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
