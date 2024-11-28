package PartieGraphique;

import BE.ouagueni.model.AccreditationPOJO;
import BE.ouagueni.model.BookingPOJO;
import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.LessonPOJO;
import BE.ouagueni.model.LessonTypePOJO;
import BE.ouagueni.model.PeriodPOJO;
import BE.ouagueni.model.SkierPOJO;
import dao.AccreditationDAO;
import dao.LessonTypeDAO;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
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
        
        // Bouton "Delete Accreditation"
        JButton btnDeleteAccreditation = createButton("Delete Accreditation", 150, 280);
        contentPane.add(btnDeleteAccreditation);
        
        // Bouton "Create Booking"
        JButton btnCreateBooking = createButton("Create Booking", 150, 340);
        contentPane.add(btnCreateBooking);
        
        // bouton "NewSkierChooseBooking"
        JButton btnNewSkierChooseBooking = createButton("New skier wante to choose a booking", 150, 400);
        contentPane.add(btnNewSkierChooseBooking);
        
        // bouton "SkierChooseBooking"
        JButton btnSkierChooseBooking = createButton("Skier wante to choose a booking", 500, 100);
        contentPane.add(btnSkierChooseBooking);
        
        // bouton "InstructorSeeHisBooking"
        JButton InstructorSeeHisBooking = createButton("Instructor See His Booking", 500, 160);
        contentPane.add(InstructorSeeHisBooking);
        
        // bouton "InstructorSeeHisAccreditation"
        JButton InstructorSeeHisAccreditation = createButton("Instructor See His Accreditation ", 500, 220);
        contentPane.add(InstructorSeeHisAccreditation);
        
      
        // Action des boutons
        
        InstructorSeeHisAccreditation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Liste pour stocker les accréditations
                List<AccreditationPOJO> ListAccreditation;
                // Récupérer tous les instructeurs
                List<InstructorPOJO> instructors = InstructorPOJO.getAllInstructor();
                
                // Construire la liste des instructeurs pour l'afficher
                StringBuilder instructorList = new StringBuilder("Liste des Instructeurs :\n");
                for (int i = 0; i < instructors.size(); i++) {
                    InstructorPOJO instructor = instructors.get(i);
                    instructorList.append(i + 1).append(". ").append(instructor.getNom())
                                  .append(" ").append(instructor.getPrenom()).append("\n");
                }

                // Demander à l'utilisateur de sélectionner un instructeur
                String selectedInstructorIndexStr = JOptionPane.showInputDialog(
                    instructorList.toString() + "Sélectionnez un instructeur (numéro) :"
                );

                try {
                    int selectedInstructorIndex = Integer.parseInt(selectedInstructorIndexStr) - 1;

                    // Récupérer les accréditations de l'instructeur sélectionné
                    ListAccreditation = AccreditationPOJO.getAccreditationByInstruId(
                        instructors.get(selectedInstructorIndex).getId()
                    );

                    // Construire la liste des accréditations pour l'afficher
                    StringBuilder AccreditationList = new StringBuilder(
                        "Accréditations de " + instructors.get(selectedInstructorIndex).getNom() + " :\n"
                    );

                    if (ListAccreditation.isEmpty()) {
                        AccreditationList.append("Aucune accréditation trouvée.\n");
                    } else {
                        for (AccreditationPOJO accreditation : ListAccreditation) {
                            AccreditationList.append("- ").append(accreditation.getName())
                                             .append(" (Type de leçon : ")
                                             .append(accreditation.getLT().getLevel()).append(")\n");
                        }
                    }

                    // Afficher les accréditations
                    JOptionPane.showMessageDialog(null, AccreditationList.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez saisir un numéro.");
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Numéro invalide. Veuillez réessayer.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue. Veuillez réessayer.");
                }
            }
        });

        
        InstructorSeeHisBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<BookingPOJO> ListBooking;
                // Afficher la liste des instructeurs
                List<InstructorPOJO> instructors = InstructorPOJO.getAllInstructor(); // Vous devrez créer cette méthode pour récupérer tous les instructeurs

                StringBuilder instructorList = new StringBuilder("Liste des Instructeurs :\n");
                for (int i = 0; i < instructors.size(); i++) {
                    InstructorPOJO instructor = instructors.get(i);
                    instructorList.append(i + 1).append(". ").append(instructor.getNom()).append(" ").append(instructor.getPrenom()).append("\n");
                }
                String selectedInstructorIndexStr = JOptionPane.showInputDialog(instructorList.toString() + "Sélectionnez un instructeur (numéro) :");
                int selectedInstructorIndex = Integer.parseInt(selectedInstructorIndexStr) - 1;

                // Récupérer les réservations de l'instructeur sélectionné
                ListBooking = BookingPOJO.getBookingsByInstructorId(instructors.get(selectedInstructorIndex).getId());

                // Afficher les bookings
                StringBuilder bookingList = new StringBuilder("Réservations de " + instructors.get(selectedInstructorIndex).getNom() + " :\n");
                
                for (BookingPOJO booking : ListBooking) {
                	System.out.println("Booking ID: " + booking.getId());
                	System.out.println("Lesson ID: " + booking.getLesson().getId());
                	System.out.println("Lesson Name: " + booking.getLesson().getName());

                    // Récupérer les informations de réservation avec une mise en forme claire
                    String bookingDetails = "Booking ID: " + booking.getId() + "\n";
                    bookingDetails += "Date de réservation: " + booking.getDateReservation() + "\n";
                    bookingDetails += "Nombre de participants: " + booking.getNombreParticipants() + "\n";
                    
                    // Afficher les détails de la leçon avec vérification pour null
                    bookingDetails += "Leçonn: " + (booking.getLesson() != null ? booking.getLesson().getName() : "Non spécifié") + "\n";

                    
                    // Afficher les détails du skieur
                    bookingDetails += "Skieur: " + (booking.getSkier() != null ? booking.getSkier().getNom() : "Non spécifié") + "\n";
                    
                    // Afficher les détails de l'instructeur
                    bookingDetails += "Instructeur: " + (booking.getInstructor() != null ? booking.getInstructor().getNom() : "Non spécifié") + "\n";
                    
                    // Afficher les dates de la période
                    bookingDetails += "Période - Date début: " + (booking.getPeriod() != null ? booking.getPeriod().getStartDate() : "Non spécifié") + "\n";
                    bookingDetails += "Période - Date fin: " + (booking.getPeriod() != null ? booking.getPeriod().getEndDate() : "Non spécifié") + "\n";
                    
                    // Afficher le nom de la réservation
                    bookingDetails += "Nom de la réservation: " + booking.getNomBooking() + "\n";

                    // Ajouter les détails du booking à la liste
                    bookingList.append(bookingDetails).append("\n");
                }

                // Afficher les informations dans une boîte de dialogue
                JOptionPane.showMessageDialog(null, bookingList.toString());
            }
        });
        
        btnSkierChooseBooking.addActionListener(new ActionListener() {
        	@Override
        	 public void actionPerformed(ActionEvent e) {
        	        // Récupérer la liste des skieurs existants
        	        List<SkierPOJO> skiers = SkierPOJO.getAllSkiers();
        	        if (skiers.isEmpty()) {
        	            JOptionPane.showMessageDialog(null, "Aucun skieur disponible. Veuillez d'abord en créer un.");
        	            return; // Arrêter si aucun skieur n'est disponible
        	        }
        	        // Afficher la liste des skieurs pour la sélection
        	        StringBuilder skierList = new StringBuilder("Sélectionnez un skieur existant :\n");
        	        for (int i = 0; i < skiers.size(); i++) {
        	            skierList.append(i + 1).append(". ").append(skiers.get(i).getNom())
        	                     .append(" ").append(skiers.get(i).getPrenom()).append("\n");
        	        }
        	        String selectedSkierIndexStr = JOptionPane.showInputDialog(null, skierList.toString() + "Entrez le numéro du skieur :");
        	        int selectedSkierIndex;
        	        try {
        	            selectedSkierIndex = Integer.parseInt(selectedSkierIndexStr) - 1;
        	            if (selectedSkierIndex < 0 || selectedSkierIndex >= skiers.size()) {
        	                JOptionPane.showMessageDialog(null, "Numéro invalide. Veuillez réessayer.");
        	                return;
        	            }
        	        } catch (NumberFormatException ex) {
        	            JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro.");
        	            return;
        	        }

        	        SkierPOJO selectedSkier = skiers.get(selectedSkierIndex);
        	        java.sql.Date sqlDate = (java.sql.Date) selectedSkier.getDateNaissance();
        	        LocalDate birthLocalDate = sqlDate.toLocalDate();
        	        LocalDate today = LocalDate.now();

        	        // Calculer l'âge
        	        int age = Period.between(birthLocalDate, today).getYears();
        	        // Déterminer la catégorie d'âge (enfant ou adulte)
        	        boolean isChild = age < 18;
        	        // Filtrer les leçons disponibles en fonction de l'âge
        	        List<LessonPOJO> lessons = LessonPOJO.getLessonsByAgeCategory(isChild);
        	        if (lessons.isEmpty()) {
        	            JOptionPane.showMessageDialog(null, "Aucune leçon disponible pour cette catégorie.");
        	            return;
        	        }
        	     // Afficher les leçons disponibles pour la sélection
        	        StringBuilder lessonsList = new StringBuilder("Leçons disponibles :\n");
        	        for (int i = 0; i < lessons.size(); i++) {
        	            lessonsList.append(i + 1).append(". ").append(lessons.get(i).getid()).append("\n");
        	        }
        	        String selectedLessonIndexStr = JOptionPane.showInputDialog(null, lessonsList.toString() + "Sélectionnez une leçon (numéro) :");
        	        int selectedLessonIndex = Integer.parseInt(selectedLessonIndexStr) - 1;
        	        System.out.println("indec selectedLessonIndex : " + selectedLessonIndex);
        	     // Récupérer la liste des instructeurs disponibles
        	        List<InstructorPOJO> instructors = InstructorPOJO.getAllInstructorNotInBooking();
        	        StringBuilder instructorsList = new StringBuilder("Instructeurs disponibles :\n");
        	        for (int i = 0; i < instructors.size(); i++) {
        	            instructorsList.append(i + 1).append(". ").append(instructors.get(i).getNom()).append("\n");
        	        }
        	        String selectedInstructorIndexStr = JOptionPane.showInputDialog(null, instructorsList.toString() + "Sélectionnez un instructeur (numéro) :");
        	        int selectedInstructorIndex = Integer.parseInt(selectedInstructorIndexStr) - 1;
        	        System.out.println("indec selectedInstructorIndex : " + selectedInstructorIndex);
        	        System.out.println("2");
        	     // Récupérer la liste des périodes disponibles
        	        List<PeriodPOJO> periods = PeriodPOJO.getAllPeriod();
        	        StringBuilder periodsList = new StringBuilder("Périodes disponibles :\n");
        	        for (int i = 0; i < periods.size(); i++) {
        	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	            periodsList.append(i + 1).append(". ")
        	                       .append(sdf.format(periods.get(i).getStartDate())).append(" à ")
        	                       .append(sdf.format(periods.get(i).getEndDate())).append("\n");
        	        }
        	        String selectedPeriodIndexStr = JOptionPane.showInputDialog(null, periodsList.toString() + "Sélectionnez une période (numéro) :");
        	        int selectedPeriodIndex = Integer.parseInt(selectedPeriodIndexStr) - 1;
        	        
        	     // Récupérer les choix sélectionnés
        	        LessonPOJO selectedLesson = lessons.get(selectedLessonIndex);
        	        InstructorPOJO selectedInstructor = instructors.get(selectedInstructorIndex);
        	        PeriodPOJO selectedPeriod = periods.get(selectedPeriodIndex);
        	        String NomBooking = JOptionPane.showInputDialog("Nom du booking : ");
        	        boolean bookingSuccess;
        	        
					try {
						bookingSuccess = BookingPOJO.AddBookingWithId(selectedSkierIndex, selectedLesson.getid(), selectedInstructor.getId(), selectedPeriod.getid(),NomBooking);
                        System.out.println(bookingSuccess);
                        if (bookingSuccess) {
                            JOptionPane.showMessageDialog(null, "Réservation réussie !");
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la réservation.");
                        }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    }
});
   
        
        // Creation du bouton pour SkierChooseBooking
        btnNewSkierChooseBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une boîte de dialogue ou une fenêtre pour saisir les informations du skieur
                String nom = JOptionPane.showInputDialog("Entrez le nom :");
                String prenom = JOptionPane.showInputDialog("Entrez le prénom :");
                String niveau = null;
                while (true) {
                    niveau = JOptionPane.showInputDialog("Entrez le niveau (Débutant/Intermédiaire/Avancé) :");
                    if (niveau != null && (niveau.equalsIgnoreCase("Débutant") ||
                                           niveau.equalsIgnoreCase("Intermédiaire") ||
                                           niveau.equalsIgnoreCase("Avancé"))) {
                        // Convertir la première lettre en majuscule pour normaliser l'entrée
                        niveau = niveau.substring(0, 1).toUpperCase() + niveau.substring(1).toLowerCase();
                        break; // Sortir de la boucle si l'entrée est valide
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un niveau valide : Débutant, Intermédiaire ou Avancé.");
                    }
                }
                String assuranceInput = null;
                boolean assurance = false;

                while (true) {
                    assuranceInput = JOptionPane.showInputDialog("Avez-vous une assurance ? (Oui/Non) :");
                    if (assuranceInput != null && (assuranceInput.equalsIgnoreCase("oui") || assuranceInput.equalsIgnoreCase("non"))) {
                        assurance = assuranceInput.equalsIgnoreCase("oui");
                        break; // Sortir de la boucle si l'entrée est valide
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez répondre par 'Oui' ou 'Non'.");
                    }
                }
                // Vérifier les champs saisis
                if (nom != null && prenom != null && !nom.isBlank() && !prenom.isBlank()) {

                    // Vérifier si la personne existe déjà
                    if (SkierPOJO.checkIfPersonExists(nom, prenom)) {
                        JOptionPane.showMessageDialog(null, "Ce skieur existe déjà dans la base de données !");
                    } else {
                        // Ajouter le skieur dans la base de données
                    	boolean success = SkierPOJO.NewSkier(nom, prenom, new Date(), niveau, assurance);
                    	SkierPOJO skier = SkierPOJO.findByNameAndSurname(nom, prenom);                     
                    	if (success) {
                            JOptionPane.showMessageDialog(null, "Le skieur a été ajouté avec succès !");
                            List<LessonPOJO> lessons = LessonPOJO.getAllLessons();
                            StringBuilder lessonsList = new StringBuilder("Leçons disponibles :\n");
                            
                            for (int i = 0; i < lessons.size(); i++) {
                                lessonsList.append(i + 1).append(". ").append(lessons.get(i).getid()).append("\n");
                            }
                            String selectedLessonIndexStr = JOptionPane.showInputDialog(null, lessonsList.toString() + "Sélectionnez une leçon (numéro) :");
                            int selectedLessonIndex = Integer.parseInt(selectedLessonIndexStr) - 1;  // Indice de la leçon sélectionnée
                            
                            // Récupérer l'instructeur disponible pour cette leçon
                            List<InstructorPOJO> instructors = InstructorPOJO.getAllInstructor();
                            StringBuilder instructorsList = new StringBuilder("Instructeurs disponibles :\n");
                            for (int i = 0; i < instructors.size(); i++) {
                                instructorsList.append(i + 1).append(". ").append(instructors.get(i).getNom()).append("\n");
                            }
                            String selectedInstructorIndexStr = JOptionPane.showInputDialog(null, instructorsList.toString() + "Sélectionnez un instructeur (numéro) :");
                            int selectedInstructorIndex = Integer.parseInt(selectedInstructorIndexStr) - 1;
                            
                         // Récupérer la période disponible
                            List<PeriodPOJO> periods = PeriodPOJO.getAllPeriod();
                            StringBuilder periodsList = new StringBuilder("Périodes disponibles :\n");
                            for (int i = 0; i < periods.size(); i++) {
                                // Récupérer les dates de début et de fin de la période
                                Date startDate = periods.get(i).getStartDate();
                                Date endDate = periods.get(i).getEndDate();
                                
                                // Définir le format de la date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Format : année-mois-jour
                                
                                // Formater les dates
                                String formattedStartDate = sdf.format(startDate);
                                String formattedEndDate = sdf.format(endDate);
                                
                                // Ajouter les dates formatées à la liste des périodes
                                periodsList.append(i + 1).append(". ").append(formattedStartDate)
                                           .append(" à ").append(formattedEndDate).append("\n");
                            }
                            
                            String selectedPeriodIndexStr = JOptionPane.showInputDialog(null, periodsList.toString() + "Sélectionnez une période (numéro) :");
                            int selectedPeriodIndex = Integer.parseInt(selectedPeriodIndexStr) - 1;
                            
                            LessonPOJO selectedLesson = lessons.get(selectedLessonIndex);
                            InstructorPOJO selectedInstructor = instructors.get(selectedInstructorIndex);
                            PeriodPOJO selectedPeriod = periods.get(selectedPeriodIndex);
                            String NomBooking = JOptionPane.showInputDialog("Nom du booking : ");
                            System.out.println("-----------------------------------");
                            System.out.println(selectedLesson.getid());
                            System.out.println("-----------------------------------");
                            System.out.println(selectedInstructor.getId());
                            System.out.println("-----------------------------------");
                            System.out.println(selectedPeriod.getid());
                            System.out.println("-----------------------------------");
                            System.out.println(skier.getId());
                            System.out.println("-----------------------------------");
                            System.out.println(NomBooking);
                            System.out.println("-----------------------------------");
                           
                            boolean bookingSuccess;
							try {
								bookingSuccess = BookingPOJO.AddBookingWithId(skier.getId(), selectedLesson.getid(), selectedInstructor.getId(), selectedPeriod.getid(),NomBooking);
	                            System.out.println(bookingSuccess);
	                            if (bookingSuccess) {
	                                JOptionPane.showMessageDialog(null, "Réservation réussie !");
	                            } else {
	                                JOptionPane.showMessageDialog(null, "Erreur lors de la réservation.");
	                            }
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        } else {
                            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout du skieur.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Les champs nom et prénom sont obligatoires.");
                }
            }
        });
        // Création du bouton pour le Booking
        btnCreateBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("++++++++++++++++++++++");
                // 1. Récupérer les skieurs, instructeurs, leçons et périodes via les POJOs
                List<SkierPOJO> skiers = SkierPOJO.getAllSkierNotInBooking();  // Appel du POJO pour récupérer les skieurs
                List<InstructorPOJO> instructors = InstructorPOJO.getAllInstructorNotInBooking();  // Appel du POJO pour récupérer les instructeurs
                List<LessonPOJO> lessons = LessonPOJO.getAllLessonsNotInBooking();  // Appel du POJO pour récupérer les leçons
                List<PeriodPOJO> periods = PeriodPOJO.getAllPeriodNotInBooking();  // Appel du POJO pour récupérer les périodes

                // Si les listes sont vides, afficher un message d'erreur
                if (skiers.isEmpty() || instructors.isEmpty() || lessons.isEmpty() || periods.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Impossible de créer un booking. Vérifiez les données.");
                    return;
                }
                System.out.println("----------------------");
                // 2. Afficher des listes déroulantes pour que l'utilisateur sélectionne les informations
                String[] skierNames = skiers.stream().map(s -> s.getNom() + " " + s.getPrenom()).toArray(String[]::new);
                String[] instructorNames = instructors.stream().map(i -> i.getNom() + " " + i.getPrenom()).toArray(String[]::new);
                String[] lessonID = lessons.stream()
                        .map(lesson -> String.valueOf(lesson.getid()))
                        .toArray(String[]::new);
                String[] periodNames = periods.stream().map(p -> p.getStartDate() + " - " + p.getEndDate()).toArray(String[]::new);

                String selectedSkier = (String) JOptionPane.showInputDialog(null,
                        "Sélectionnez un skieur :", "Créer un booking",
                        JOptionPane.QUESTION_MESSAGE, null, skierNames, skierNames[0]);

                String selectedInstructor = (String) JOptionPane.showInputDialog(null,
                        "Sélectionnez un instructeur :", "Créer un booking",
                        JOptionPane.QUESTION_MESSAGE, null, instructorNames, instructorNames[0]);

                String selectedLesson = (String) JOptionPane.showInputDialog(null,
                        "Sélectionnez une leçon :", "Créer un booking",
                        JOptionPane.QUESTION_MESSAGE, null, lessonID, lessonID[0]);

                String selectedPeriod = (String) JOptionPane.showInputDialog(null,
                        "Sélectionnez une période :", "Créer un booking",
                        JOptionPane.QUESTION_MESSAGE, null, periodNames, periodNames[0]);
                
                String nomBooking = JOptionPane.showInputDialog(null, "Entrez le nom de la réservation :");

                // 3. Si l'utilisateur a fait des sélections, créez l'objet Booking
                if (selectedSkier != null && selectedInstructor != null &&
                    selectedLesson != null && selectedPeriod != null) {

                    SkierPOJO selectedSkierPOJO = skiers.stream().filter(s -> (s.getNom() + " " + s.getPrenom()).equals(selectedSkier)).findFirst().orElse(null);
                    InstructorPOJO selectedInstructorPOJO = instructors.stream().filter(i -> (i.getNom() + " " + i.getPrenom()).equals(selectedInstructor)).findFirst().orElse(null);
                    LessonPOJO selectedLessonPOJO = lessons.stream()
                    	    .filter(l -> l.getid() == Integer.parseInt(selectedLesson)) // On compare les IDs
                    	    .findFirst()
                    	    .orElse(null);
                    PeriodPOJO selectedPeriodPOJO = periods.stream().filter(p -> (p.getStartDate() + " - " + p.getEndDate()).equals(selectedPeriod)).findFirst().orElse(null);

                    // 4. Créer le booking avec les objets sélectionnés
                    if (selectedSkierPOJO != null && selectedInstructorPOJO != null &&
                        selectedLessonPOJO != null && selectedPeriodPOJO != null) {

                    	System.out.println("999999999999999999");
                        // Créer l'objet Booking et le sauvegarder via POJ
                        BookingPOJO booking = new BookingPOJO(selectedSkierPOJO, selectedInstructorPOJO, selectedLessonPOJO, selectedPeriodPOJO,nomBooking);
                        booking.createBooking(); // Appel de la méthode POJO qui va interagir avec le DAO pour créer le booking

                        JOptionPane.showMessageDialog(null, "Booking créé avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Sélectionnez toutes les informations nécessaires.");
                    }
                }
            }
        });
        
        btnDeleteAccreditation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Récupérer toutes les accréditations via le POJO
                List<AccreditationPOJO> accreditations = AccreditationPOJO.getAllAccreditations();

                if (accreditations.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune accréditation à supprimer.");
                    return;
                }

                // 2. Afficher une liste déroulante pour choisir une accréditation à supprimer
                String[] accreditationNames = accreditations.stream()
                        .map(AccreditationPOJO::getName)
                        .toArray(String[]::new);
                String selectedAccreditation = (String) JOptionPane.showInputDialog(
                        null,
                        "Sélectionnez une accréditation à supprimer :",
                        "Supprimer une accréditation",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        accreditationNames,
                        accreditationNames[0]
                );

                if (selectedAccreditation != null) {
                    // 3. Trouver l'accréditation correspondante dans la liste
                    AccreditationPOJO accreditationToDelete = accreditations.stream()
                            .filter(a -> a.getName().equals(selectedAccreditation))
                            .findFirst()
                            .orElse(null);

                    if (accreditationToDelete != null) {
                        // 4. Supprimer l'accréditation via le POJO
                        accreditationToDelete.deleteAccreditation();
                        JOptionPane.showMessageDialog(null, "Accréditation supprimée avec succès !");
                    }
                }
            }
        });
        
        btnCreateAccreditation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Récupérer tous les LessonTypes via POJO
                List<LessonTypePOJO> lessonTypes = LessonTypePOJO.getAllLessonTypes();

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

                    // 4. Créer l'accréditation via POJO
                    AccreditationPOJO accreditation = new AccreditationPOJO(accreditationName);
                    accreditation.createAccreditation(selectedLessonTypeId);

                    // 5. Afficher un message de confirmation
                    JOptionPane.showMessageDialog(null, "Accréditation créée avec succès !");
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
