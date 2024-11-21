package dao;

import BE.ouagueni.model.BookingPOJO;
import BE.ouagueni.model.SkierPOJO;
import singleton.EcoleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO  extends DAO_Generique<SkierPOJO>{

    private Connection connection;

    public BookingDAO(Connection conn) {
        super(conn);
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
    public boolean AddBookingWithId(int idSkier, int idLesson, int idInstructeur, int idPeriod) {
        // Récupérer la connexion
        Connection connection = EcoleConnection.getInstance().getConnect();
        System.out.println("AddBookingWithId");
        
        // Définir la requête d'insertion dans la table Booking
        String query = "INSERT INTO Booking (dateReservation, nombreParticipants, lesson_id, instructor_id, skier_id, period_id) " +
                       "VALUES (SYSDATE, 1, ?, ?, ?, ?)"; // SYSDATE pour la date actuelle, nombreParticipants à 1 par défaut
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Ajouter les paramètres à la requête
            stmt.setInt(1, idLesson);      // lesson_id
            stmt.setInt(2, idInstructeur); // instructor_id
            stmt.setInt(3, idSkier);       // skier_id
            stmt.setInt(4, idPeriod);      // period_id
            
            // Exécuter la requête
            int affectedRows = stmt.executeUpdate();
            
            // Vérifier si l'insertion a été effectuée avec succès
            if (affectedRows > 0) {
                return true; // Si au moins une ligne est affectée, l'insertion est réussie
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Afficher l'erreur
        }
        return false; // Retourne false si l'insertion échoue
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
