import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class main1 {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/subh", "root", "1234");
            Statement stmt = conn.createStatement();

            // Create main frame
            JFrame frame = new JFrame("Employee Management System");
            frame.setSize(750, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // ========= LEFT PANEL (Form Panel) =========
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(null);
            leftPanel.setPreferredSize(new Dimension(350, 400));
            leftPanel.setBackground(new Color(240, 248, 255));

            JLabel l1 = new JLabel("Enter ID:");
            l1.setBounds(30, 30, 100, 25);
            JTextField lt1 = new JTextField();
            lt1.setBounds(150, 30, 150, 25);

            JLabel l2 = new JLabel("Enter Name:");
            l2.setBounds(30, 80, 100, 25);
            JTextField lt2 = new JTextField();
            lt2.setBounds(150, 80, 150, 25);

            JLabel l3 = new JLabel("Enter Department:");
            l3.setBounds(30, 130, 120, 25);
            JTextField lt3 = new JTextField();
            lt3.setBounds(150, 130, 150, 25);

            JButton btnInsert = new JButton("Insert");
            btnInsert.setBounds(100, 190, 120, 30);
            btnInsert.setBackground(new Color(60, 179, 113));
            btnInsert.setForeground(Color.WHITE);

            btnInsert.addActionListener(e1 -> {
                int id = Integer.parseInt(lt1.getText());
                String name = lt2.getText();
                String dept = lt3.getText();
                try {
                    String query = "INSERT INTO EMP VALUES(?,?,?)";
                    PreparedStatement pmst = conn.prepareStatement(query);
                    pmst.setInt(1, id);
                    pmst.setString(2, name);
                    pmst.setString(3, dept);
                    pmst.executeUpdate();
                    lt1.setText("");
                    lt2.setText("");
                    lt3.setText("");
                } catch (Exception u) {
                    u.printStackTrace();
                }
            });

            leftPanel.add(l1);
            leftPanel.add(lt1);
            leftPanel.add(l2);
            leftPanel.add(lt2);
            leftPanel.add(l3);
            leftPanel.add(lt3);
            leftPanel.add(btnInsert);

            // ========= RIGHT PANEL (Search Panel) =========
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(null);
            rightPanel.setBackground(new Color(255, 250, 250));

            JLabel searchTitle = new JLabel("Search Employee by ID");
            searchTitle.setFont(new Font("Arial", Font.BOLD, 16));
            searchTitle.setBounds(40, 30, 250, 25);
            rightPanel.add(searchTitle);

            JButton btn3 = new JButton("Search All");
            btn3.setBackground(new Color(70, 130, 180));
            btn3.setForeground(Color.WHITE);
            btn3.setBounds(250, 30, 100, 25);
            rightPanel.add(btn3);

            JLabel r1 = new JLabel("Employee ID:");
            r1.setBounds(30, 80, 100, 25);
            JTextField rt1 = new JTextField();
            rt1.setBounds(130, 80, 120, 25);
            rightPanel.add(r1);
            rightPanel.add(rt1);

            JButton btnSearch = new JButton("Search");
            btnSearch.setBounds(260, 80, 80, 25);
            btnSearch.setBackground(new Color(70, 130, 180));
            btnSearch.setForeground(Color.WHITE);
            rightPanel.add(btnSearch);

            JTextArea outputArea = new JTextArea("Result will appear here");
            outputArea.setFont(new Font("Arial", Font.PLAIN, 13));
            outputArea.setEditable(false);
            outputArea.setLineWrap(true);
            outputArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(outputArea);
            scrollPane.setBounds(20, 130, 320, 200);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            rightPanel.add(scrollPane);

            // ========= Button Logic =========
            btn3.addActionListener(e3 -> {
                try {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
                    StringBuilder results = new StringBuilder();

                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        results.append("ID: ").append(rs.getInt(1))
                               .append(", Name: ").append(rs.getString(2))
                               .append(", Dept: ").append(rs.getString(3))
                               .append("\n");
                    }

                    if (!found) {
                        outputArea.setText("Empty Table");
                    } else {
                        outputArea.setText(results.toString());
                    }

                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            });

            btnSearch.addActionListener(e2 -> {
                int id = Integer.parseInt(rt1.getText());
                boolean f = false;

                try {
                    String query = "SELECT * FROM emp WHERE empid = ?";
                    PreparedStatement psmt = conn.prepareStatement(query);
                    psmt.setInt(1, id);
                    ResultSet rs = psmt.executeQuery();
                    rt1.setText("");

                    while (rs.next()) {
                        f = true;
                        String statement = "ID: " + rs.getInt(1) + ", Name: " + rs.getString(2) + ", Dept: " + rs.getString(3);
                        outputArea.setText(statement);
                    }
                    if (!f) {
                        outputArea.setText("Employee does not exist.");
                    }

                } catch (Exception r) {
                    r.printStackTrace();
                }
            });

            // ========= SPLIT PANE =========
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
            splitPane.setDividerLocation(350);
            splitPane.setEnabled(false);

            frame.add(splitPane, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
