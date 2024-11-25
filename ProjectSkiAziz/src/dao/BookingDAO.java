package dao;

import BE.ouagueni.model.BookingPOJO;
import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.LessonPOJO;
import BE.ouagueni.model.PeriodPOJO;
import BE.ouagueni.model.SkierPOJO;
import singleton.EcoleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO  extends DAO_Generique<SkierPOJO>{

    private Connection connection;

    public BookingDAO(Connection conn) {
        super(conn); // Probablement inutile si vous n'en avez pas besoin ici
        this.connection = conn; // Assurez-vous d'initialiser ce champ
    }

    // Méthode pour récupérer toutes les réservations
    public List<BookingPOJO> getAllBookings() {
        List<BookingPOJO> bookings = new ArrayList<>();
        String query = "SELECT id, dateReservation, nombreParticipants,nomBooking FROM Booking";

        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Utilisez le champ `connection` ici
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date dateReservation = rs.getDate("dateReservation");
                int nombreParticipants = rs.getInt("nombreParticipants");
                String NomBooking = rs.getString("nomBooking");

                BookingPOJO booking = new BookingPOJO(id, dateReservation, nombreParticipants,NomBooking);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }	
    public void createBooking(BookingPOJO booking) {
        java.util.logging.Logger.getLogger("oracle.jdbc").setLevel(java.util.logging.Level.FINEST);
        System.setProperty("java.util.logging.ConsoleHandler.level", "FINEST");

        // Récupérer la connexion
        Connection connection = EcoleConnection.getInstance().getConnect();
        System.out.println("444444444444444444444444444:System.out.println(\"++++++++++++++++++++++\");");
        System.out.println("Lesson ID: " + booking.getLesson().getid());
        System.out.println("Skier ID: " + booking.getSkier().getId());
        System.out.println("Period ID: " + booking.getPeriod().getid());
        System.out.println("Instructor ID: " + booking.getInstructor().getId());
        System.out.println("Nom du booking: " + booking.getNomBooking());
        
        String query = "INSERT INTO Booking (id ,nomBooking, dateReservation, nombreParticipants, lesson_id, instructor_id, skier_id, period_id) " +
                "VALUES (booking_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        	stmt.setString(1, booking.getNomBooking()); // Index 1 : nomBooking
        	stmt.setDate(2, new java.sql.Date(booking.getDateReservation().getTime())); // Index 2 : dateReservation
        	stmt.setInt(3, booking.getNombreParticipants()); // Index 3 : nombreParticipants
        	
            // Validez les IDs avant de les insérer
            validateAndSetInt(stmt, 4, booking.getLesson().getid(), "lesson_id");
            validateAndSetInt(stmt, 5, booking.getInstructor().getId(), "instructor_id");
            validateAndSetInt(stmt, 6, booking.getSkier().getId(), "skier_id");
            validateAndSetInt(stmt, 7, booking.getPeriod().getid(), "period_id");

            // Exécuter la requête
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Récupérer la dernière valeur de la séquence générée
                String currValQuery = "SELECT booking_seq.CURRVAL FROM dual";
                try (PreparedStatement currValStmt = connection.prepareStatement(currValQuery)) {
                    try (ResultSet resultSet = currValStmt.executeQuery()) {
                        if (resultSet.next()) {
                            int generatedId = resultSet.getInt(1);  // Récupérer le CURRVAL de la séquence
                            booking.setId(generatedId);  // Assigner l'ID généré à l'objet booking
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Méthode pour valider et définir un paramètre entier
    private void validateAndSetInt(PreparedStatement stmt, int index, Object value, String fieldName) throws SQLException {
        try {
            int intValue = Integer.parseInt(value.toString());
            stmt.setInt(index, intValue);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for " + fieldName + ": " + value, e);
        }
    }
    public boolean AddBookingWithId(int idSkier, int idLesson, int idInstructeur, int idPeriod, String NomBooking) throws SQLException {
        if (connection == null) {
            throw new SQLException("La connexion à la base de données n'est pas initialisée.");
        }
        
        // Désactiver l'auto-commit pour gérer manuellement les transactions
        connection.setAutoCommit(false);

        String query = "INSERT INTO Booking (dateReservation, nombreParticipants, nomBooking, lesson_id, instructor_id, skier_id, period_id) " +
                       "VALUES (SYSDATE, 1, ?, ?, ?, ?, ?)";

        System.out.println("Query : " + query);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Paramètres
            stmt.setString(1, NomBooking);
            stmt.setInt(2, idLesson);
            stmt.setInt(3, idInstructeur);
            stmt.setInt(4, idSkier);
            stmt.setInt(5, idPeriod);

            System.out.println("Statement avant exécution : " + stmt);

            // Exécuter la requête
            int affectedRows = stmt.executeUpdate();
            System.out.println("Rows affected : " + affectedRows);

            if (affectedRows > 0) {
                connection.commit();
                return true;
            } else {
                System.err.println("Aucune ligne affectée. Vérifiez l'intégrité des données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                    System.err.println("Transaction annulée.");
                } catch (SQLException rollbackEx) {
                    System.err.println("Erreur lors du rollback : " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            // Réactiver l'auto-commit
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        return false;
    }
    // Méthode pour récupérer les bookings d'un instructeur donné par son ID
    public List<BookingPOJO> getBookingsByInstructorId(int instructorId) {
        List<BookingPOJO> bookings = new ArrayList<>();
        String query = """
            SELECT b.id, b.dateReservation, b.nombreParticipants, b.lesson_id, b.skier_id, b.period_id, b.nomBooking
            FROM Booking b
            WHERE b.instructor_id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Paramétrer l'ID de l'instructeur
            statement.setInt(1, instructorId);

            // Exécuter la requête
            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et ajouter les bookings à la liste
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("id");
                Date dateReservation = resultSet.getDate("dateReservation");
                int nombreParticipants = resultSet.getInt("nombreParticipants");
                int lessonId = resultSet.getInt("lesson_id");
                int skierId = resultSet.getInt("skier_id");
                int periodId = resultSet.getInt("period_id");
                String nomBooking = resultSet.getString("nomBooking");
                
                // Récupérer les objets associés (Skier, Instructor, Lesson, Period)
                SkierPOJO skier = SkierPOJO.getSkierById(skierId); // Vous devez créer cette méthode dans SkierPOJO
                InstructorPOJO instructor = InstructorPOJO.getInstructorById(instructorId); // Même chose pour Instructor
                LessonPOJO lesson = null;
                if (lessonId > 0) {  // Vérifier si l'ID de la leçon est valide
                	System.out.println(lessonId);
                    lesson = LessonPOJO.getLessonById(lessonId); // Idem pour Lesson
                    System.out.println("Lesson ToString : ");
                    System.out.println("+++++++++++++++++++++++++++");
                    System.out.println(lesson.toString());
                    System.out.println("+++++++++++++++++++++++++++");
                }
                else
                {
                	System.out.println("LessonID est 0");
                }
                PeriodPOJO period = PeriodPOJO.getPeriodById(periodId); // Idem pour Period

                // Si la leçon est trouvée, l'afficher dans les logs pour vérifier
                if (lesson != null) {
                    System.out.println("Leçon : " + lesson.toString());
                } else {
                    System.out.println("Leçon non trouvée pour Booking ID: " + bookingId);
                }

                BookingPOJO booking = new BookingPOJO(skier, instructor, lesson, period, nomBooking);
                booking.setId(bookingId);
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
	@Override
	public boolean create(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean delete(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean update(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public SkierPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
