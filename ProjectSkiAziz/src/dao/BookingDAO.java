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
        String query = "SELECT id, dateReservation, nombreParticipants FROM Booking";

        try (PreparedStatement stmt = connection.prepareStatement(query)) { // Utilisez le champ `connection` ici
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date dateReservation = rs.getDate("dateReservation");
                int nombreParticipants = rs.getInt("nombreParticipants");

                BookingPOJO booking = new BookingPOJO(id, dateReservation, nombreParticipants);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    public void createBooking(BookingPOJO booking) {
        // Récupérer la connexion
        Connection connection = EcoleConnection.getInstance().getConnect();
        
        String query = "INSERT INTO Booking (dateReservation, nombreParticipants, lesson_id, skier_id, period_id) " +
                       "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(booking.getDateReservation().getTime()));
            stmt.setInt(2, booking.getNombreParticipants());
            stmt.setInt(3, booking.getLesson().getid()); // ID de la leçon
            stmt.setInt(4, booking.getSkier().getId());   // ID du skieur
            stmt.setInt(5, booking.getPeriod().getid());  // ID de la période

            // Exécuter la requête
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setId(generatedKeys.getInt(1)); // Récupérer l'ID généré pour la réservation
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
