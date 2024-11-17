package dao;

import BE.ouagueni.model.BookingPOJO;
import singleton.EcoleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour récupérer toutes les réservations
    public List<BookingPOJO> getAllBookings() {
        List<BookingPOJO> bookings = new ArrayList<>();

        String query = "SELECT id, dateReservation, nombreParticipants FROM Booking";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
}
