package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import BE.ouagueni.model.LessonPOJO;
import BE.ouagueni.model.SkierPOJO;

public class SkierDAO extends DAO_Generique<SkierPOJO> {
    
    public SkierDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(SkierPOJO skier) {
        return false;
    }

    @Override
    public boolean delete(SkierPOJO skier) {
        return false;
    }

    @Override
    public boolean update(SkierPOJO skier) {

        return false;
    }
    public SkierPOJO findByNameAndSurname(String name, String surname) {
        SkierPOJO skier = null;
        String query = "SELECT * FROM Skier WHERE nom = ? AND prenom = ?";
        
        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setString(1, name);   // Remplace le premier '?' par 'name'
            stmt.setString(2, surname); // Remplace le second '?' par 'surname'
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                skier = new SkierPOJO();
                skier.setId(result.getInt("id"));
                skier.setNom(result.getString("nom"));
                skier.setPrenom(result.getString("prenom"));
                skier.setDateNaissance(result.getDate("dateNaissance"));
                skier.setNiveau(result.getString("niveau"));
                skier.setAssurance(result.getBoolean("assurance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return skier;
    }
    public SkierPOJO getSkierById(int idSkier) {
        SkierPOJO skier = null;
        String query = "SELECT * FROM Skier WHERE id = ?";
        
        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setInt(1, idSkier);   // Remplace le premier '?' par 'idSkier'
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                skier = new SkierPOJO();
                skier.setId(result.getInt("id"));
                skier.setNom(result.getString("nom"));
                skier.setPrenom(result.getString("prenom"));
                skier.setDateNaissance(result.getDate("dateNaissance"));
                skier.setNiveau(result.getString("niveau"));
                skier.setAssurance(result.getBoolean("assurance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return skier;
    }
    public boolean NewSkier(String Nom, String Prenom, java.util.Date date, String Niveau, boolean Assurance) {
        boolean success = false; // Initialiser le succès à faux
        try {
            // Vérifier si le skieur existe déjà dans la base de données
            String checkQuery = "SELECT COUNT(*) FROM Skier WHERE nom = ? AND prenom = ?";
            PreparedStatement checkStmt = this.connect.prepareStatement(checkQuery);
            checkStmt.setString(1, Nom);  // Utilisation des paramètres passés à la méthode
            checkStmt.setString(2, Prenom);

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    // Si le skieur existe déjà, renvoyer false
                    System.out.println("Ce skieur existe déjà dans la base de données.");
                    return false;
                }
            }

            // Requête d'insertion
            String insertQuery = "INSERT INTO Skier (nom, prenom, dateNaissance, niveau, assurance) " +
                                 "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = this.connect.prepareStatement(insertQuery);
            insertStmt.setString(1, Nom);  // Utilisation des paramètres passés à la méthode
            insertStmt.setString(2, Prenom);
            insertStmt.setDate(3, new java.sql.Date(date.getTime()));  // Convertir la Date Java en SQL Date
            insertStmt.setString(4, Niveau);  // Le niveau du skieur
            insertStmt.setBoolean(5, Assurance);  // Assurance en tant que booléen (true/false)

            // Exécution de la requête d'insertion et vérification des lignes affectées
            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                success = true; // Si au moins une ligne est affectée, succès
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
        return success; // Retourner le succès ou l'échec
    }
    
    public List<SkierPOJO> getAllSkiersInBooking() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = """
                SELECT id, nom, prenom, dateNaissance, niveau, assurance
				FROM Skier s
				WHERE EXISTS (
				    SELECT 1
				    FROM Booking b
				    WHERE b.skier_id = s.id
				)
            """;

        try (PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Récupérer les données de chaque skieur depuis le ResultSet
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Date dateNaissance = resultSet.getDate("dateNaissance");
                String niveau = resultSet.getString("niveau");
                boolean assurance = resultSet.getBoolean("assurance");

                // Créer un objet SkierPOJO et l'ajouter à la liste
                SkierPOJO skier = new SkierPOJO(id, nom, prenom, dateNaissance, niveau, assurance);
                skiers.add(skier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skiers;
    }
    
    public List<SkierPOJO> getAllSkiersNotInBooking() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = """
                SELECT id, nom, prenom, dateNaissance, niveau, assurance 
                FROM Skier s
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM Booking b
                    WHERE b.skier_id = s.id
                )
            """;

        try (PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Récupérer les données de chaque skieur depuis le ResultSet
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Date dateNaissance = resultSet.getDate("dateNaissance");
                String niveau = resultSet.getString("niveau");
                boolean assurance = resultSet.getBoolean("assurance");

                // Créer un objet SkierPOJO et l'ajouter à la liste
                SkierPOJO skier = new SkierPOJO(id, nom, prenom, dateNaissance, niveau, assurance);
                skiers.add(skier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skiers;
    }
    public int HaveToPay(int selectedSkierIndex) {
        int totalAmount = 0;
        boolean hasInsurance = false;
        List<LessonPOJO> lessons = new ArrayList<>();
        System.out.println("1");

        // Étape 1 : Vérifier si le skieur a une assurance
        String skierQuery = """
            SELECT assurance FROM Skier WHERE id = ?
        """;
        System.out.println("2");

        try (PreparedStatement skierStatement = connect.prepareStatement(skierQuery)) {
            skierStatement.setInt(1, selectedSkierIndex);
            try (ResultSet resultSet = skierStatement.executeQuery()) {
                if (resultSet.next()) {
                    hasInsurance = resultSet.getBoolean("assurance");
                    System.out.println("3");
                    System.out.println("hasInsurance: " + hasInsurance);
                } else {
                    System.out.println("No skier found with ID: " + selectedSkierIndex);
                    return totalAmount; // Aucun skieur trouvé, le total reste à 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException while fetching skier insurance");
            return totalAmount; // Retourner 0 en cas d'erreur
        }

        System.out.println("4");
        System.out.println("Selected skier ID: " + selectedSkierIndex);

        // Étape 2 : Récupérer les bookings associés au skieur
        String bookingsQuery = """
            SELECT b.lesson_id, l.lesson_date 
            FROM Booking b
            JOIN Lesson l ON b.lesson_id = l.id
            WHERE b.skier_id = ?
        """;

        System.out.println("5");

        try (PreparedStatement bookingsStatement = connect.prepareStatement(bookingsQuery)) {
            bookingsStatement.setInt(1, selectedSkierIndex);

            try (ResultSet resultSet = bookingsStatement.executeQuery()) {
                boolean hasBookings = false; // Indique si des bookings existent

                while (resultSet.next()) {
                    hasBookings = true;
                    int lessonId = resultSet.getInt("lesson_id");
                    Date lessonDate = resultSet.getDate("lesson_date");
                    System.out.println("6 - Booking found: lessonId=" + lessonId + ", lessonDate=" + lessonDate);

                    // Étape 3 : Récupérer les détails de la leçon
                    LessonPOJO lesson = getLessonDetails(lessonId);
                    System.out.println("7 - Lesson details fetched");

                    // Calculer le prix de la leçon en fonction du type de leçon
                    int lessonPrice = getLessonPrice(lesson.getLessonType_id());
                    System.out.println("lessonPrice: " + lessonPrice);

                    // Appliquer la réduction si le skieur a une assurance
                    if (hasInsurance) {
                        lessonPrice -= 20; // Réduction de 20 par leçon
                        System.out.println("8 - Discount applied for insurance");
                    }

                    // Ajouter la leçon à la liste
                    lessons.add(lesson);
                    totalAmount += lessonPrice;
                    System.out.println("9 - Lesson added to totalAmount");
                }

                if (!hasBookings) {
                    System.out.println("No bookings found for skier with ID: " + selectedSkierIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException while fetching bookings");
            return totalAmount; // Retourner le total actuel en cas d'erreur
        }

        // Étape 4 : Vérifier les leçons matin et après-midi
        boolean hasMorningAndAfternoonLessons = hasMorningAndAfternoon(lessons);
        System.out.println("hasMorningAndAfternoonLessons: " + hasMorningAndAfternoonLessons);

        // Appliquer une réduction de 15% si des leçons matin et après-midi existent
        if (hasMorningAndAfternoonLessons) {
            totalAmount = (int) (totalAmount * 0.85); // Réduction de 15%
            System.out.println("10 - Morning and afternoon discount applied");
        }

        System.out.println("Total Amount: " + totalAmount);
        return totalAmount;
    }

    private int getLessonPrice(int lessonTypeId) {
        // Méthode pour récupérer le prix d'une leçon à partir de son lessonType_id
        int price = 0;
        String query = """
            SELECT price FROM LessonType WHERE id = ?
        """;
        
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, lessonTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    price = resultSet.getInt("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    private LessonPOJO getLessonDetails(int lessonId) {
        // Méthode pour récupérer les détails d'une leçon spécifique
        LessonPOJO lesson = null;
        String query = """
            SELECT lessonType_id, lesson_date 
            FROM Lesson 
            WHERE id = ?
        """;
        
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, lessonId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Utilisation du constructeur pour initialiser l'objet LessonPOJO
                    lesson = new LessonPOJO(
                        resultSet.getDate("lesson_date"),    // Récupère lesson_date
                        resultSet.getInt("lessonType_id")  // Récupère lessonType_id
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }


    private boolean hasMorningAndAfternoon(List<LessonPOJO> lessons) {
        boolean hasMorning = false;
        boolean hasAfternoon = false;

        // Vérification des heures pour chaque leçon
        for (LessonPOJO lesson : lessons) {
            // Récupérer la lesson_date (java.sql.Date) et convertir en LocalDateTime
            java.sql.Date lessonDate = lesson.getLesson_date();  // Récupération de la date
            if (lessonDate != null) {
                // Convertir java.sql.Date en LocalDateTime en tenant compte du fuseau horaire
                LocalDateTime lessonLocalDateTime = lessonDate.toLocalDate()  // Récupère la date (sans l'heure)
                                                       .atStartOfDay()  // Définir l'heure à 00:00
                                                       .atZone(ZoneId.systemDefault())  // Appliquer le fuseau horaire
                                                       .toLocalDateTime();  // Convertir en LocalDateTime
                
                // Vérifier l'heure de la leçon : avant midi = matin, après midi = après-midi
                if (lessonLocalDateTime.getHour() < 12) {
                    hasMorning = true;
                } else {
                    hasAfternoon = true;
                }
            }
        }

        return hasMorning && hasAfternoon;
    }


    public List<SkierPOJO> getAllSkiers() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = """
        	    SELECT id, nom, prenom, dateNaissance, niveau, assurance
        	    FROM Skier s
        	""";

        try (PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Récupérer les données de chaque skieur depuis le ResultSet
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Date dateNaissance = resultSet.getDate("dateNaissance");
                String niveau = resultSet.getString("niveau");
                boolean assurance = resultSet.getBoolean("assurance");

                // Créer un objet SkierPOJO et l'ajouter à la liste
                SkierPOJO skier = new SkierPOJO(id, nom, prenom, dateNaissance, niveau, assurance);
                skiers.add(skier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skiers;
    }

	@Override
	public SkierPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
