package PartieGraphique;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class PartieGraphique extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public PartieGraphique() {
        setTitle("Gestion École de Ski");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0xF4F4F9));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Gestion de l'École de Ski");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(100, 30, 300, 40);
        contentPane.add(lblTitle);

        JButton btnManageAccreditation = new JButton("Gérer Accréditation");
        btnManageAccreditation.setBounds(150, 100, 200, 40);
        contentPane.add(btnManageAccreditation);

        JButton btnViewLessons = new JButton("Consulter les types de leçons disponibles");
        btnViewLessons.setBounds(100, 160, 300, 40);
        contentPane.add(btnViewLessons);

        JButton btnManageRegistrations = new JButton("Gérer les inscriptions");
        btnManageRegistrations.setBounds(150, 220, 200, 40);
        contentPane.add(btnManageRegistrations);

        // Actions sur les boutons (à compléter plus tard)
        btnManageAccreditation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic pour gérer les accréditations
                System.out.println("Gestion des accréditations");
            }
        });

        btnViewLessons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic pour consulter les leçons
                System.out.println("Consultation des types de leçons disponibles");
            }
        });

        btnManageRegistrations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic pour gérer les inscriptions
                System.out.println("Gestion des inscriptions");
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14));
            UIManager.put("Label.foreground", new Color(0x333333));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
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
}
