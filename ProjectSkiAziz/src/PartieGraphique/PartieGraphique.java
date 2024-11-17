package PartieGraphique;

import BE.ouagueni.model.AccreditationPOJO;
import BE.ouagueni.model.BookingPOJO;
import BE.ouagueni.model.LessonPOJO;
import BE.ouagueni.model.LessonTypePOJO;
import dao.AccreditationDAO;
import dao.LessonTypeDAO;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PartieGraphique extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public PartieGraphique() {
        setTitle("Gestion École de Ski");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);  // Augmenter la taille de la fenêtre
        setLocationRelativeTo(null);  // Centrer la fenêtre

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0xF4F4F9)); // Couleur de fond douce
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Espacement intérieur
        setContentPane(contentPane);

        // Titre
        JLabel lblTitle = new JLabel("Gestion de l'École de Ski");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setBounds(120, 30, 350, 40);
        contentPane.add(lblTitle);

        // Bouton "Afficher les réservations"
        JButton btnAfficherReservations = createButton("Afficher les réservations", 150, 100);
        contentPane.add(btnAfficherReservations);

        // Bouton "Afficher les leçons"
        JButton btnAfficherLecons = createButton("Afficher leçons", 150, 160);
        contentPane.add(btnAfficherLecons);
        contentPane.add(btnAfficherLecons);
        
        // Bouton "Create Accreditation"
        JButton btnCreateAccreditation = createButton("Create Accreditation", 150, 220);
        contentPane.add(btnCreateAccreditation);
        
        
        // Action des boutons
        
        btnCreateAccreditation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Récupérer tous les LessonTypes
                LessonTypeDAO lessonTypeDAO = new LessonTypeDAO();
                List<LessonTypePOJO> lessonTypes = lessonTypeDAO.getAllLessonTypes();

                // Vérification que la liste des LessonTypes n'est pas vide
                if (lessonTypes.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Aucun type de leçon disponible.");
                    return;  // Ne pas continuer si aucun type de leçon
                }

                // 2. Afficher une nouvelle fenêtre/modale pour saisir le nom et sélectionner un LessonType
                CreateAccreditationDialog dialog = new CreateAccreditationDialog(lessonTypes);
                dialog.setVisible(true);

                // 3. Récupérer les données saisies si l'utilisateur confirme
                if (dialog.isConfirmed()) {
                    String accreditationName = dialog.getAccreditationName();
                    int selectedLessonTypeId = dialog.getSelectedLessonTypeId();

                    // 4. Créer l'accréditation
                    AccreditationDAO accreditationDAO = new AccreditationDAO();
                    accreditationDAO.createAccreditation(new AccreditationPOJO(accreditationName), selectedLessonTypeId);

                    // 5. Afficher un message de confirmation
                    JOptionPane.showMessageDialog(null, "Accreditation créée avec succès !");
                }
            }
        });
        
        btnAfficherReservations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<BookingPOJO> bookings = BookingPOJO.getAllBookings();

                System.out.println("Affichage de toutes les réservations :");
                if (bookings.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Aucune réservation trouvée.");
                } else {
                    StringBuilder message = new StringBuilder("Réservations disponibles:\n");
                    for (BookingPOJO booking : bookings) {
                        message.append(booking.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(contentPane, message.toString());
                }
            }
        });

        btnAfficherLecons.addActionListener(e -> {
            List<LessonPOJO> lessons = LessonPOJO.getAllLessons();

            System.out.println("Affichage de toutes les leçons :");
            if (lessons.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Aucune leçon trouvée.");
            } else {
                StringBuilder message = new StringBuilder("Leçons disponibles:\n");
                for (LessonPOJO lesson : lessons) {
                    message.append(lesson.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(contentPane, message.toString());
            }
        });
    }

    // Méthode pour créer un bouton avec un style commun
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 300, 40);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Police moderne
        button.setBackground(new Color(0x4CAF50));  // Couleur verte
        button.setForeground(Color.WHITE);  // Texte en blanc
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(createRoundedBorder(10));  // Bord arrondi avec un rayon de 10

        // Ajouter un effet de survol
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(0x388E3C));  // Couleur au survol
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(0x4CAF50));  // Retour à la couleur initiale
            }
        });

        return button;
    }

    // Méthode pour créer un Border arrondi pour les boutons
    private Border createRoundedBorder(int radius) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x388E3C), 2), // Bordure verte
                BorderFactory.createEmptyBorder(radius, radius, radius, radius)
        );
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 16));  // Police moderne
            UIManager.put("Label.foreground", new Color(0x333333));
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 16));
            UIManager.put("Button.background", new Color(0x4CAF50));
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
