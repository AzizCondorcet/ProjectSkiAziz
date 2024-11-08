package PartieGraphique;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import singleton.EcoleConnection;
import dao.InstructorDAO;
import BE.ouagueni.model.InstructorPOJO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PartieGraphique extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField instructorIdField;
    private JTextArea resultArea;

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Label.font", new Font("Arial", Font.BOLD, 12));
            UIManager.put("Label.foreground", new Color(0x444444));
            UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12));
            UIManager.put("Button.background", new Color(0x5A9));
            UIManager.put("Button.foreground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                PartieGraphique frame = new PartieGraphique();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PartieGraphique() {
        setTitle("Instructor Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0xF4F4F9));
        setContentPane(contentPane);

        JLabel lblEnterId = new JLabel("Enter Instructor ID:");
        lblEnterId.setBounds(10, 10, 150, 25);
        contentPane.add(lblEnterId);

        instructorIdField = new JTextField();
        instructorIdField.setBounds(160, 10, 50, 25);
        contentPane.add(instructorIdField);
        instructorIdField.setColumns(10);

        JButton btnShowInstructor = new JButton("Show Instructor");
        btnShowInstructor.setBounds(220, 10, 150, 25);
        contentPane.add(btnShowInstructor);

        resultArea = new JTextArea();
        resultArea.setBounds(10, 50, 400, 200);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(0xBBBBBB)));
        resultArea.setBackground(new Color(0xFFFFFF));
        contentPane.add(resultArea);

        btnShowInstructor.addActionListener(e -> showInstructorInfo());
    }

    private void showInstructorInfo() {
        try {
            int instructorId = Integer.parseInt(instructorIdField.getText());
            InstructorDAO instructorDAO = new InstructorDAO(EcoleConnection.getInstance().getConnect());
            InstructorPOJO instructor = InstructorPOJO.getInstructor(instructorId, instructorDAO);

            if (instructor != null) {
                resultArea.setText(instructor.toString());
            } else {
                resultArea.setText("Instructor not found.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter a valid instructor ID.");
        } catch (Exception e) {
            resultArea.setText("Error retrieving instructor information.");
            e.printStackTrace();
        }
    }
}
