package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.BookingDAO;
import singleton.EcoleConnection;

public class BookingPOJO implements Serializable {
    private int id;
    private Date dateReservation;
    private int nombreParticipants;
    
	public BookingPOJO(int id, Date dateReservation, int nombreParticipants) {
		super();
		this.id = id;
		this.dateReservation = dateReservation;
		this.nombreParticipants = nombreParticipants;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public int getNombreParticipants() {
		return nombreParticipants;
	}

	public void setNombreParticipants(int nombreParticipants) {
		this.nombreParticipants = nombreParticipants;
	}

	@Override
	public String toString() {
		return "BookingPOJO [id=" + id + ", dateReservation=" + dateReservation + ", nombreParticipants="
				+ nombreParticipants + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateReservation, id, nombreParticipants);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingPOJO other = (BookingPOJO) obj;
		return Objects.equals(dateReservation, other.dateReservation) && id == other.id
				&& nombreParticipants == other.nombreParticipants;
	}
	public static List<BookingPOJO> getAllBookings() {
        // Création d'une instance de BookingDAO
        BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());

        // Appel de la méthode getAllBookings() du BookingDAO pour récupérer les réservations
        return bookingDAO.getAllBookings();
    }
    
}

